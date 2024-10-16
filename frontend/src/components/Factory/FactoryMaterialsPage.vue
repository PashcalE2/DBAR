<template>
    <div class="column">
        <div class="centered_column">
            <ListInputField
                ref="material_search"

                input_id="material_name_input"
                input_style_width="900px"
                datalist_id="material_name_datalist"
                label_text=""
                placeholder="Начните вводить название материала"

                v-bind:on_input="onMaterialInput"
                v-bind:datalist_options="materials_names"
                v-bind:error_message="active_error_messages.material_name"
            />
        </div>
        <div class="grid">
            <ProductGridPlate
                ref="materials"
                v-for="(product, i) in materials"
                v-bind:key="i"
                v-bind:product_id="product.id"
                v-bind:product_price="product.price"
                v-bind:product_name="product.name"
                v-on:click="onMaterialClick(i)"
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
    name: "FactoryMaterialsPage",
    components: {ProductGridPlate, ListInputField},

    data() {
        return {
            active_error_messages: {
                material_name: ""
            },

            error_messages: {
                material_name: {
                    NoSuchMaterial: "Такого материала нет",
                    WrongSymbols: "Только буквы русского и латинского алфавитов, цифры и \""
                },
            },

            materials: [],
            materials_names: [],
            current_order_materials: [],
            current_order_info: {}
        };
    },

    mounted() {
        this.getMaterials();
    },

    methods: {
        onMaterialInput(element) {
            let text = element.value.toLowerCase();
            let is_empty = text.length === 0;

            if (is_empty) {
                for (let option of this.$refs.materials) {
                    option.show();
                }
            }
            else {
                for (let option of this.$refs.materials) {
                    if (option.getProductName().toLowerCase().indexOf(text) > -1){
                        option.show();
                    }
                    else {
                        option.hide();
                    }
                }
            }
        },

        onMaterialClick(element_id) {
            let page = this;
            let component = this.$refs.materials[element_id];

            page.$router.replace({ name: "FactoryMaterial", query: { material_id: component.getProductId() }})
        },

        getMaterials() {
            let page = this;

            axios.request({
                url: MY_APIS.FACTORY.MATERIAL.GET_ALL_SHORT.url,
                method: MY_APIS.FACTORY.MATERIAL.GET_ALL_SHORT.method,
                params: {
                    factory_id: FactoryStorage.getId(),
                    password: FactoryStorage.getPassword()
                }
            })
                .then(function (response) {
                    page.materials = response.data;

                    page.materials_names = []
                    for (let i = 0; i < page.materials.length; i++) {
                        page.materials_names.push(page.materials[i].name);
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