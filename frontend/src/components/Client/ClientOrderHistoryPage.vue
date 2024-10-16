<template>
    <div class="column" style="width: fit-content; padding: 8px; margin: 0 auto 0 auto">
        <div class="row" style="justify-content: space-between">
            <div class="column">
                <div class="order_header">
                    Заказ №{{ order_info.id }}
                </div>

                <div class="order_header" style="font-weight: normal">
                    Статус: <OrderStatus
                    v-bind:status="order_info.status"
                ></OrderStatus>
                </div>

                <div class="order_header" style="font-weight: normal">
                    Сформирован: {{ reformatDate(order_info.formedAt) }}
                </div>

                <div class="order_header" style="font-weight: normal">
                    Завершен: {{ reformatDate(order_info.doneAt) }}
                </div>
            </div>

            <div class="column" style="width: 200px; padding: 8px">
                <DefaultButton
                    ref="back_button"
                    caption="Вернуться к заказам"
                    v-bind:on_click="backOnClick"
                />
            </div>
        </div>

        <ClientProductInOrderHistory
            v-for="(product, i) in products_in_order"
            v-bind:key="i"
            v-bind:product_id="product.id"
            v-bind:product_status="product.status"
            v-bind:product_count="product.count"
        />

        <div class="order_header">
            Сумма заказа: <span class="sum">{{ order_info.sum }}</span>
        </div>

        <div class="row" style="justify-content: space-between; padding: 8px">
            <div class="column" style="width: 200px">
                <DefaultButton
                        ref="chat_button"
                        caption="Перейти в чат"
                        v-bind:on_click="chatOnClick"
                />
            </div>

            <div class="column" style="width: 200px">
                <DefaultButton
                    ref="pay_button"
                    caption="Оплатить"
                    v-bind:on_click="payOnClick"
                />
            </div>

            <div class="column" style="width: 200px">
                <DefaultButton
                        ref="cancel_button"
                        caption="Отменить"
                        v-bind:on_click="cancelOnClick"
                />
            </div>
        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import ClientProductInOrderHistory from "@/components/Client/ClientProductInOrderHistory.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";
import {reformatDate} from "@/js/utils";
import OrderStatus from "@/components/Commons/OrderStatus.vue";

export default {
    name: "ClientOrderHistoryPage",
    components: {OrderStatus, ClientProductInOrderHistory, DefaultButton},

    data() {
        return {
            order_id: 0,
            order_info: {},
            products_in_order: []
        }
    },

    mounted() {
        if (this.$route.query === undefined) {
            this.$router.replace({ name: "ClientMain"});
            return;
        }

        this.order_id = this.$route.query.order_id;

        this.$refs.chat_button.enable();
        this.$refs.back_button.enable();
        this.getOrderInfo();
        this.getProductsInOrder();
    },

    methods: {
        reformatDate,

        backOnClick() {
            let page = this;
            page.$refs.back_button.disable();
            page.$router.replace({ name: "ClientOrders" });
        },

        chatOnClick() {
            let page = this;
            page.$refs.chat_button.disable();
            page.$router.replace({ name: "ClientChatHistory", query: { order_id: page.order_id } });
        },

        payOnClick() {
            let page = this;
            this.$refs.pay_button.disable();

            axios.request({
                url: MY_APIS.CLIENT.ORDER.PAY.url,
                method: MY_APIS.CLIENT.ORDER.PAY.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_info.id
                }
            })
                .then(function (response) {
                    response;
                    alert("Заказ оплачен");
                    page.getOrderInfo();
                })
                .catch(function (exception) {
                    console.log(exception);
                    alert("Не удалось оплатить заказ");
                    page.$refs.cancel_button.enable();
                })
        },

        cancelOnClick() {
            this.$refs.cancel_button.disable();

            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.ORDER.CANCEL.url,
                method: MY_APIS.CLIENT.ORDER.CANCEL.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function () {
                    alert("Заказ успешно отменен");
                })
                .catch(function (exception) {
                    alert("Не удалось отменить заказ");
                    console.log(exception);
                    this.$refs.cancel_button.enable();
                })
        },

        getOrderInfo() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.ORDER.GET.url,
                method: MY_APIS.CLIENT.ORDER.GET.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.order_info = response.data;

                    if (page.order_info.status === "формируется") {
                        page.$router.replace({ name: "ClientMain" });
                    }
                    else if (page.order_info.status === "ожидает оплаты") {
                        page.$refs.pay_button.enable();
                    }
                    else {
                        if (page.order_info.status !== "отклонен" && page.order_info.status !== "выполнен") {
                            page.$refs.cancel_button.enable();
                        }
                    }
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "ClientMain"});
                })
        },

        getProductsInOrder() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.ORDER.GET_PRODUCTS.url,
                method: MY_APIS.CLIENT.ORDER.GET_PRODUCTS.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.products_in_order = response.data;
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "ClientMain"});
                })
        }
    }
}
</script>

<style scoped>

div.order_header {
    padding: 8px;
    text-align: left;
    font-size: 32px;
    font-weight: bold;
}

span.sum {
    padding: 8px;
    font-size: 32px;
    font-weight: bold;
    color: #ff007f;
}

</style>