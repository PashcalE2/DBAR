<template>
    <div class="column" style="background-color: #007fff;">
        <div id="admin_header">Сервис для консультантов</div>

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
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as AdminStorage from "@/js/admin_storage";

export default {
    name: "AdminMainPage",
    components: {FlexRowNavigationBar},

    data() {
        let navigation_references = {
            orders: "orders",
            logout: "logout",
        };

        return {
            references: navigation_references,

            row_elements: [
                {
                    value: "Заказы",
                    ref: navigation_references.orders,
                    onClick: this.ordersOnClick
                }
            ],

            reversed_row_elements: [
                {
                    value: "Выйти",
                    ref: navigation_references.logout,
                    onClick: this.logoutOnClick
                }
            ]
        }
    },

    beforeUpdate() {
        this.checkIfAuthenticated();
    },

    beforeMount() {
        this.checkIfAuthenticated();
    },

    mounted() {
        this.$refs.navigation_bar.enableRef(this.references.orders);
        this.$router.replace({ name: "AdminOrders"});
    },

    methods: {
        ordersOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "AdminOrders"});
            this.$refs.navigation_bar.enableRef(this.references.orders);
        },

        logoutOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;

            this.$refs.navigation_bar.enableRef(this.references.logout);
            AdminStorage.clearAdmin();
            page.$router.push({ name: "AdminLogin"});
        },

        checkIfAuthenticated() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.PROFILE.CHECK_RIGHTS.url,
                method: MY_APIS.ADMIN.PROFILE.CHECK_RIGHTS.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword()
                }
            })
                .then(function (response) {
                    if (response.status !== 200) {
                        console.log("Нет доступа");
                        page.$router.replace({ name: "AdminLogin" });
                    }
                })
                .catch(function (exception) {
                    console.log("Ошибка: " + exception.response.data);
                    page.$router.replace({ name: "AdminLogin" });
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