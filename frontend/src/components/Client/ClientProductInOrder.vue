<template>
    <div class="product">
        <div class="column">
            <div class="row">
                <img src="@/assets/logo.png" alt="Placeholder" style="width: 200px; height: 200px;"/>
                <div class="column">
                    <div class="row" style="width: 800px; justify-content: space-between;">
                        <div class="header">
                            {{ product.name }}
                        </div>
                        <div class="column">
                            <DefaultButton
                                    ref="remove_button"
                                    caption="X"
                                    v-bind:on_click="removeOnClick"
                            />
                        </div>
                    </div>

                    <span class="price">{{ product.price }} * {{ product.count }} = {{ product.price * product.count }}</span>

                    <div style="width: fit-content">
                        <CountField
                            ref="counter"
                            label_text="Количество"
                            v-bind:on_change_count="onChangeCount"
                        />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="header">
                    Описание
                </div>
            </div>
            <div class="description">
                {{ product.description }}
            </div>
        </div>

    </div>

</template>

<script>
import CountField from "@/components/Commons/CountField.vue";
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";

export default {
    name: "ClientProductInOrder",
    components: {DefaultButton, CountField},

    props: ["product_id", "product_count", "product_status", "order_id", "on_remove", "on_change_count"],

    data() {
        return {
            product: {
                id: this.product_id,
                name: "",
                status: this.product_status,
                description: "",
                price: 0,
                count: this.product_count
            }
        }
    },

    mounted() {
        this.getProductInfo();
        this.$refs.remove_button.enable();
        this.$refs.counter.setCount(this.product.count);
    },

    methods: {
        getProductInfo() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.PRODUCT.GET.url,
                method: MY_APIS.CLIENT.PRODUCT.GET.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    product_id: page.product_id
                }
            })
                .then(function (response) {
                    //console.log(response);

                    page.product.name = response.data.name;
                    page.product.price = response.data.price;
                    page.product.description = response.data.description;
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        },

        removeOnClick() {
            this.on_remove(this.product.id);
        },

        onChangeCount() {
            this.on_change_count(this.product.id, this.$refs.counter.getValue());
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