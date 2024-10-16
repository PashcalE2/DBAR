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

        <AdminProductInOrderHistory
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
                    ref="assembly_button"
                    caption="Запросить сборку"
                    v-bind:on_click="assemblyOnClick"
                />
            </div>
        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as AdminStorage from "@/js/admin_storage";
import {reformatDate} from "@/js/utils";
import OrderStatus from "@/components/Commons/OrderStatus.vue";
import AdminProductInOrderHistory from "@/components/Admin/AdminProductInOrderHistory.vue";

export default {
    name: "AdminOrderHistoryPage",
    components: {AdminProductInOrderHistory, OrderStatus, DefaultButton},

    data() {
        return {
            order_id: 0,
            order_info: {},
            products_in_order: []
        }
    },

    mounted() {
        if (this.$route.query === undefined) {
            this.$router.replace({ name: "AdminMain"});
            return;
        }

        this.order_id = this.$route.query.order_id;

        this.$refs.chat_button.enable();
        this.$refs.back_button.enable();
        this.getOrderInfo();
    },

    methods: {
        reformatDate,

        backOnClick() {
            let page = this;
            page.$refs.back_button.disable();
            page.$router.replace({ name: "AdminOrders" });
        },

        chatOnClick() {
            let page = this;
            page.$refs.chat_button.disable();
            page.$router.replace({ name: "AdminChatHistory", query: { order_id: page.order_id } });
        },

        assemblyOnClick() {
            let page = this;
            page.$refs.assembly_button.disable();
            console.log(MY_APIS.ADMIN.ORDER.ASK_FOR_ASSEMBLING.url);

            axios.request({
                url: MY_APIS.ADMIN.ORDER.ASK_FOR_ASSEMBLING.url,
                method: MY_APIS.ADMIN.ORDER.ASK_FOR_ASSEMBLING.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    response;
                    page.order_info = {};
                    page.getOrderInfo();
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "AdminMain"});
                })
        },

        getOrderInfo() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.ORDER.GET.url,
                method: MY_APIS.ADMIN.ORDER.GET.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.order_info = response.data;

                    if (page.order_info.status !== "отклонен" && page.order_info.status !== "выполнен" && page.order_info.status !== "ожидает оплаты") {
                        page.$refs.assembly_button.enable();
                    }

                    page.products_in_order = [];
                    page.getProductsInOrder();
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "AdminMain"});
                })
        },

        getProductsInOrder() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.ORDER.GET_PRODUCTS.url,
                method: MY_APIS.ADMIN.ORDER.GET_PRODUCTS.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.products_in_order = response.data;
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "AdminMain"});
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