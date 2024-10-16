<template>
    <div class="column" style="background-color: #007fff;">
        <div id="factory_header">Сервис для управления</div>

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
import * as FactoryStorage from "@/js/factory_storage";

export default {
    name: "FactoryMainPage",
    components: {FlexRowNavigationBar},

    data() {
        let navigation_references = {
            products: "products",
            materials: "materials",
            logout: "logout",
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
                    value: "Материалы",
                    ref: navigation_references.materials,
                    onClick: this.materialsOnClick
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
        this.$refs.navigation_bar.enableRef(this.references.products);
        this.$router.replace({ name: "FactoryProducts"});
    },

    methods: {
        productsOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "FactoryProducts"});
            this.$refs.navigation_bar.enableRef(this.references.products);
        },

        materialsOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;
            page.$router.replace({ name: "FactoryMaterials"});
            this.$refs.navigation_bar.enableRef(this.references.materials);
        },

        logoutOnClick(element) {
            if (this.$refs.navigation_bar.isEnabled(element)) {
                return;
            }

            let page = this;

            this.$refs.navigation_bar.enableRef(this.references.logout);
            FactoryStorage.clearFactory();
            page.$router.push({ name: "FactoryLogin"});
        },

        checkIfAuthenticated() {
            let page = this;

            axios.request({
                url: MY_APIS.FACTORY.PROFILE.CHECK_RIGHTS.url,
                method: MY_APIS.FACTORY.PROFILE.CHECK_RIGHTS.method,
                params: {
                    factory_id: FactoryStorage.getId(),
                    password: FactoryStorage.getPassword()
                }
            })
                .then(function (response) {
                    if (response.status !== 200) {
                        console.log("Нет доступа");
                        page.$router.replace({ name: "FactoryLogin" });
                    }
                })
                .catch(function (exception) {
                    console.log("Ошибка: " + exception.response.data);
                    page.$router.replace({ name: "FactoryLogin" });
                })
        },
    }
}
</script>

<style scoped>

div#factory_header {
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