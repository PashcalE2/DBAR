<template>
    <div class="column" style="width: fit-content; padding: 8px; margin: 0 auto 0 auto">
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
        </div>

        <ClientProductInOrder
            v-for="(product, i) in products_in_order"
            v-bind:key="i"
            v-bind:product_id="product.id"
            v-bind:product_status="product.status"
            v-bind:product_count="product.count"
            v-bind:order_id="order_info.id"
            v-bind:on_remove="removeProductFromOrder"
            v-bind:on_change_count="updateProductCount"
        />

        <div class="order_header">
            Итого к оплате: <span class="sum">{{ order_info.sum }}</span>
        </div>

        <div class="row" style="justify-content: space-between; padding: 8px">
            <div class="column" style="width: 200px">
                <DefaultButton
                    ref="accept_button"
                    caption="Сформировать"
                    v-bind:on_click="acceptOnClick"
                />
            </div>

            <div class="column" style="width: 200px">
                <DefaultButton
                    ref="chat_button"
                    caption="Перейти в чат"
                    v-bind:on_click="chatOnClick"
                />
            </div>
        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import ClientProductInOrder from "@/components/Client/ClientProductInOrder.vue";
import OrderStatus from "@/components/Commons/OrderStatus.vue";
import {reformatDate} from "../../js/utils";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";

export default {
    name: "ClientCurrentOrderPage",
    components: {OrderStatus, ClientProductInOrder, DefaultButton},

    data() {
        return {
            order_info: {},
            products_in_order: []
        }
    },

    mounted() {
        this.$refs.chat_button.enable();
        this.$refs.accept_button.enable();
        this.getCurrentOrderInfo();
    },

    methods: {
        reformatDate,

        acceptOnClick(element) {
            element;
            let page = this;
            this.$refs.accept_button.disable();

            axios.request({
                url: MY_APIS.CLIENT.ORDER.ACCEPT.url,
                method: MY_APIS.CLIENT.ORDER.ACCEPT.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_info.id
                }
            })
                .then(function (response) {
                    response;
                    alert("Заказ подтвержден");
                    page.$router.replace({ name: "ClientMain" });
                })
                .catch(function (exception) {
                    console.log(exception);
                    alert("Не удалось подтвердить заказ");
                    page.$refs.cancel_button.enable();
                })
        },

        chatOnClick() {
            let page = this;
            page.$refs.chat_button.disable();
            page.$router.replace({ name: "ClientChat", query: { order_id: page.order_info.id }});
        },

        cancelOnClick() {
            this.$refs.cancel_button.disable();
        },

        getCurrentOrderInfo() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.ORDER.GET_CURRENT.url,
                method: MY_APIS.CLIENT.ORDER.GET_CURRENT.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword()
                }
            })
                .then(function (response) {
                    page.order_info = response.data;
                    page.products_in_order = [];
                    page.getProductsInOrder();
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "ClientMain"});
                })
        },

        updateProductCount(product_id, count) {
            console.log("Update");
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.ORDER.SET_PRODUCT_COUNT.url,
                method: MY_APIS.CLIENT.ORDER.SET_PRODUCT_COUNT.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_info.id,
                    product_id: product_id,
                    count: count
                }
            })
                .then(function (response) {
                    response;
                    page.products_in_order = [];
                    page.getCurrentOrderInfo();
                })
                .catch(function (exception) {
                    console.log(exception);
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
                    order_id: page.order_info.id
                }
            })
                .then(function (response) {
                    //console.log(response);
                    page.products_in_order = response.data;

                    if (page.products_in_order.length === 0) {
                        page.$refs.accept_button.disable();
                    }
                    else {
                        page.$refs.accept_button.enable();
                    }
                })
                .catch(function (exception) {
                    console.log(exception);
                    page.$router.replace({ name: "ClientMain"});
                })
        },

        removeProductFromOrder(product_id) {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.PRODUCT.REMOVE_FROM_ORDER.url,
                method: MY_APIS.CLIENT.PRODUCT.REMOVE_FROM_ORDER.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_info.id,
                    product_id: product_id
                }
            })
                .then(function (response) {
                    response;
                    page.getCurrentOrderInfo();
                })
                .catch(function (exception) {
                    console.log(exception);
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

span.status_progress {
    font-size: 32px;
    font-weight: bold;
    color: #ff7f00;
}

span.sum {
    padding: 8px;
    font-size: 32px;
    font-weight: bold;
    color: #ff007f;
}

</style>