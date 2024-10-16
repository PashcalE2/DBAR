<template>
    <div class="product">
        <div class="column">
            <div class="row">
                <img src="@/assets/logo.png" alt="Placeholder" style="width: 200px; height: 200px;"/>
                <div class="column">
                    <div class="row" style="width: 800px; justify-content: space-between;">
                        <div class="header">
                            {{ product_name }}
                        </div>
                    </div>

                    <div class="header">
                        <ProductStatus
                            v-bind:status="product_status"
                        />
                    </div>

                    <span class="price">{{ product_price + " * " + product_count }} = {{ product_price * product_count }}</span>
                </div>
            </div>
            <div class="row">
                <div class="header">
                    Описание
                </div>
            </div>
            <div class="description">
                {{ product_description }}
            </div>
        </div>

    </div>

</template>

<script>
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as AdminStorage from "@/js/admin_storage";
import ProductStatus from "@/components/Commons/ProductStatus.vue";

export default {
    name: "AdminProductInOrderHistory",
    components: {ProductStatus},

    props: ["product_id", "product_count", "product_status"],

    data() {
        return {
            product_name: "",
            product_price: 0,
            product_description: ""
        }
    },

    mounted() {
        this.getProductInfo();
    },

    methods: {
        getProductInfo() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.PRODUCT.GET.url,
                method: MY_APIS.ADMIN.PRODUCT.GET.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
                    product_id: page.product_id
                }
            })
                .then(function (response) {
                    page.product_name = response.data.name;
                    page.product_price = response.data.price;
                    page.product_description = response.data.description;
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        }
    }
}
</script>

<style scoped>

div.product {
    margin: 8px auto 8px auto;
    padding: 8px;
    border: #e0e0e0 2px solid;
    border-radius: 8px;
    width: 1000px;
}

div.header {
    padding: 8px;
    font-size: 26px;
    font-weight: bold;
    text-align: left;
}

span.price {
    padding: 8px;
    font-size: 32px;
    font-weight: bold;
    text-align: left;
    color: #ff007f;
}

div.description {
    padding: 8px;
    font-size: 20px;
    text-align: left;
}

</style>