<template>
    <div class="column">
        <div class="centered_column">
            <ListInputField
                ref="product_search"

                input_id="product_name_input"
                input_style_width="900px"
                datalist_id="product_name_datalist"
                label_text=""
                placeholder="Начните вводить название продукции"

                v-bind:on_input="onProductInput"
                v-bind:datalist_options="products_names"
                v-bind:error_message="active_error_messages.product_name"
            />
        </div>
        <div class="grid">
            <ProductGridPlate
                ref="products"
                v-for="(product, i) in products"
                v-bind:key="i"
                v-bind:product_id="product.id"
                v-bind:product_price="product.price"
                v-bind:product_name="product.name"
                v-on:click="onProductClick(i)"
            />
        </div>
    </div>
</template>

<script>
import ListInputField from "@/components/Commons/ListInputField.vue";
import ProductGridPlate from "@/components/Commons/ProductGridPlate.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as FactoryStorage from "@/js/factory_storage";

export default {
    name: "FactoryProductsPage",
    components: {ProductGridPlate, ListInputField},

    data() {
        return {
            active_error_messages: {
                product_name: ""
            },

            error_messages: {
                product_name: {
                    NoSuchProduct: "Такого продукта нет",
                    WrongSymbols: "Только буквы русского и латинского алфавитов, цифры и \""
                },
            },

            products: [],
            products_names: [],
            current_order_products: [],
            current_order_info: {}
        };
    },

    mounted() {
        //console.log("Получаем опции продукции");
        this.getProducts();
    },

    methods: {
        onProductInput(element) {
            let text = element.value.toLowerCase();
            let is_empty = text.length === 0;

            if (is_empty) {
                for (let option of this.$refs.products) {
                    option.show();
                }
            }
            else {
                for (let option of this.$refs.products) {
                    if (option.getProductName().toLowerCase().indexOf(text) > -1){
                        option.show();
                    }
                    else {
                        option.hide();
                    }
                }
            }
        },

        onProductClick(element_id) {
            let page = this;
            let component = this.$refs.products[element_id];

            page.$router.replace({ name: "FactoryProduct", query: { product_id: component.getProductId() }})
        },

        getProducts() {
            let page = this;

            axios.request({
                url: MY_APIS.FACTORY.PRODUCT.GET_ALL_SHORT.url,
                method: MY_APIS.FACTORY.PRODUCT.GET_ALL_SHORT.method,
                params: {
                    factory_id: FactoryStorage.getId(),
                    password: FactoryStorage.getPassword()
                }
            })
                .then(function (response) {
                    page.products = response.data;

                    page.products_names = []
                    for (let i = 0; i < page.products.length; i++) {
                        page.products_names.push(page.products[i].name);
                    }
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        }
    }
}
</script>

<style scoped>

img {
    width: 180px;
    height: 180px;
    margin: auto;
    background-color: #f0f0f0;
}

div.grid {
    display: grid;
    grid-gap: 1px;
    grid-template-columns: repeat(6, 1fr);
    padding: 8px;
}


</style>