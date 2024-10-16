<template>
    <div class="column" style="background-color: #007fff;">
        <div id="admin_header">Клиент-ориентированный сервис</div>

        <FlexRowNavigationBar
            ref="navigation_bar"
            v-bind:row_elements="row_elements"
            v-bind:reversed_row_elements="reversed_row_elements"
        />
    </div>
    <div class="background">
        <router-view />
    </div>

</template>

<script>

import FlexRowNavigationBar from "@/components/Commons/FlexRowNavigationBar.vue";
import * as ClientStorage from "@/js/client_storage";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";

export default {
    name: "ClientMainPage",
    components: {FlexRowNavigationBar},

    data() {
        let navigation_references = {
            products: "products",
            orders: "orders",
            current_order: "current_order",
            logout: "logout",
            profile: "profile",
        };

        return {
            references: navigation_references,

            row_elements: [
                {
                    value: "Продукция",
                    ref: navigation_references.products,
                    onClick: this.productsOnClick
                },

                {
                    value: "Заказы",
                    ref: navigation_references.orders,
                    onClick: this.ordersOnClick
                },

                {
                    value: "Корзина",
                    ref: navigation_references.current_order,
                    onClick: this.currentOrderOnClick
                }
            ],

            reversed_row_elements: [
                {
                    value: "Выйти",
                    ref: navigation_references.logout,
                    onClick: this.logoutOnClick
                },

                {
                    value: "Профиль",
                    ref: navigation_references.profile,
                    onClick: this.profileOnClick
                }
            ],

            current_order_info: {},
            current_order_products: []
        }
    },

    beforeUpdate() {
        this.checkIfAuthenticated();
    },

    beforeMount() {
        this.checkIfAuthenticated();
        //this.getCurrentOrderInfo();
    },

    mounted() {
        this.$refs.navigation_bar.enableRef(this.references.products);
        this.$router.replace({ name: "ClientProducts"});
    },

    methods: {
        productsOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "ClientProducts"});
            this.$refs.navigation_bar.enableRef(this.references.products);
        },

        ordersOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "ClientOrders"});
            this.$refs.navigation_bar.enableRef(this.references.orders);
        },

        currentOrderOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "ClientCurrentOrder"});
            this.$refs.navigation_bar.enableRef(this.references.current_order);
        },

        logoutOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;

            this.$refs.navigation_bar.enableRef(this.references.logout);
            ClientStorage.clearClient();
            page.$router.push({ name: "ClientLogin"});
        },

        profileOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "ClientProfile"});
            this.$refs.navigation_bar.enableRef(this.references.profile);
        },

        checkIfAuthenticated() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.PROFILE.CHECK_RIGHTS.url,
                method: MY_APIS.CLIENT.PROFILE.CHECK_RIGHTS.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword()
                }
            })
                .then(function (response) {
                    if (response.status !== 200) {
                        console.log("Нет доступа");
                        page.$router.replace({ name: "ClientLogin" });
                    }
                })
                .catch(function (exception) {
                    console.log("Ошибка: " + exception.response.data);
                    page.$router.replace({ name: "ClientLogin" });
                })
        },
    }
}
</script>

<style scoped>

div#admin_header {
    display: flex;
    flex-direction: column;
    padding: 16px;
    font-size: 32px;
    background-color: #007fff;
    color: #ffffff;
}

div.background {
    background-color: #e0e0e0;
    padding: 10px;
}

</style>