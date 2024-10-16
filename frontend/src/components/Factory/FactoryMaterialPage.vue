<template>
    <div class="material">
        <div class="column">
            <div class="row">
                <img src="@/assets/logo.png" alt="Placeholder" style="width: 200px; height: 200px;"/>
                <div class="column">
                    <TextareaInputField
                        ref="name_input"

                        input_style_width="780px"
                        input_style_max_width="780px"
                        input_style_height="80px"
                        input_style_max_height="80px"
                        label_text="Название"
                        placeholder="Введите название"
                        v-bind:error_message="active_error_messages.name"
                        v-bind:on_input="onNameInput"
                    />

                    <StringInputField
                        ref="price_input"
                        label_text="Цена"
                        placeholder="Введите цену"
                        v-bind:error_message="active_error_messages.price"
                        v-bind:on_input="onPriceInput"
                    />
                </div>
            </div>
            <TextareaInputField
                ref="description_input"

                input_style_width="980px"
                input_style_max_width="980px"
                input_style_height="120px"
                label_text="Описание"
                placeholder="Введите описание"
                v-bind:error_message="active_error_messages.description"
                v-bind:on_input="onDescriptionInput"
            />

            <div class="row" style="justify-content: space-between; padding: 8px">
                <DefaultButton
                    ref="accept_button"
                    caption="Применить"

                    v-bind:on_click="onAcceptClick"
                />

                <DefaultButton
                    ref="cancel_button"
                    caption="Отменить"

                    v-bind:on_click="onCancelClick"
                />

                <DefaultButton
                    ref="back_button"
                    caption="Назад к материалам"

                    v-bind:on_click="onBackClick"
                />
            </div>

        </div>

    </div>

</template>

<script>
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as FactoryStorage from "@/js/factory_storage";
import StringInputField from "@/components/Commons/StringInputField.vue";
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import TextareaInputField from "@/components/Commons/TextareaInputField.vue";

export default {
    name: "FactoryMaterial",
    components: {TextareaInputField, DefaultButton, StringInputField},

    data() {
        return {
            material: {
                id: 0,
                name: "",
                price: 0,
                description: ""
            },

            input: {
                name: "",
                price: "",
                description: ""
            },

            form_errors: {
                name: false,
                price: false,
                description: false
            },

            active_error_messages: {
                name: "",
                price: "",
                description: ""
            },

            error_messages: {
                name: {
                    EmptyField: "Пустое значение запрещено"
                },

                price: {
                    EmptyField: "Пустое значение запрещено",
                    WrongSymbols: "Только цифры"
                },

                description: {
                    EmptyField: "Пустое значение запрещено"
                }
            },

            price_re: /^[1-9]\d*$/
        }
    },

    mounted() {
        if (this.$route.query === undefined) {
            if (this.$route.query.material_id === undefined) {
                this.$router.replace({ name: "FactoryMaterials" });
                return;
            }
        }

        this.material.id = this.$route.query.material_id;
        this.$refs.accept_button.enable();
        this.$refs.cancel_button.enable();
        this.$refs.back_button.enable();
        this.getMaterialInfo();
    },

    methods: {

        onBackClick() {
            this.$refs.back_button.disable();
            this.$router.replace({ name: "FactoryMaterials" });
        },

        onCancelClick() {
            this.getMaterialInfo();
        },

        onAcceptClick() {
            let page = this;

            this.$refs.accept_button.disable();

            axios.request({
                url: MY_APIS.FACTORY.MATERIAL.SET.url,
                method: MY_APIS.FACTORY.MATERIAL.SET.method,
                params: {
                    factory_id: FactoryStorage.getId(),
                    password: FactoryStorage.getPassword()
                },
                data: {
                    id: page.material.id,
                    name: page.input.name,
                    price: page.input.price,
                    description: page.input.description,
                }
            })
                .then(function (response) {
                    response;
                    page.$refs.accept_button.enable();
                    page.getMaterialInfo();
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        },

        checkForm() {
            if (
                !this.form_errors.name &&
                !this.form_errors.price &&
                !this.form_errors.description
            ) {
                this.$refs.accept_button.enable();
            }
            else {
                this.$refs.accept_button.disable();
            }
        },

        onNameInput(element) {
            this.input.name = element.value;
            let is_empty = element.value.length === 0;

            if (is_empty) {
                this.form_errors.name = true;
                this.active_error_messages.name = this.error_messages.name.EmptyField;
            }
            else {
                this.form_errors.name = false;
                this.active_error_messages.name = "";
            }
        },

        onPriceInput(element) {
            this.input.price = element.value;
            let is_empty = element.value.length === 0;

            if (is_empty) {
                this.form_errors.price = true;
                this.active_error_messages.price = this.error_messages.price.EmptyField;
            }
            else if (this.price_re.exec(this.input.price) == null) {
                this.form_errors.price = true;
                this.active_error_messages.price = this.error_messages.price.WrongSymbols;
            }
            else {
                this.form_errors.price = false;
                this.active_error_messages.price = "";
            }
        },

        onDescriptionInput(element) {
            this.input.description = element.value;
            let is_empty = element.value.length === 0;

            if (is_empty) {
                this.form_errors.description = true;
                this.active_error_messages.name = this.error_messages.description.EmptyField;
            }
            else {
                this.form_errors.description = false;
                this.active_error_messages.description = "";
            }
        },

        getMaterialInfo() {
            let page = this;

            axios.request({
                url: MY_APIS.FACTORY.MATERIAL.GET.url,
                method: MY_APIS.FACTORY.MATERIAL.GET.method,
                params: {
                    factory_id: FactoryStorage.getId(),
                    password: FactoryStorage.getPassword(),
                    material_id: page.material.id
                }
            })
                .then(function (response) {
                    //console.log(response);
                    page.material.name = response.data.name;
                    page.material.price = response.data.price;
                    page.material.description = response.data.description;

                    page.$refs.name_input.setValue(page.material.name);
                    page.input.name = response.data.name;

                    page.$refs.price_input.setValue(page.material.price);
                    page.input.price = response.data.price;

                    page.$refs.description_input.setValue(page.material.description);
                    page.input.description = response.data.description;
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        }
    }
}
</script>

<style scoped>

div.material {
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