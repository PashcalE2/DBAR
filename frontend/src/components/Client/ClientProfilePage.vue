<template>
    <div class="centered_column" style="width: 800px; margin: 0 auto 0 auto">
        <div style="height: 100px"/>

        <div class="column" style="width: fit-content">
            <h1>Изменение профиля</h1>

            <div class="column" style="margin: 0 auto">

                <EmailInputField
                    ref="email"

                    input_id="email_input"
                    input_style_width="400px"
                    label_text="Изменение адреса почты"
                    placeholder="example.example@example.com"
                    error_message="Неверный формат почты"
                    v-bind:on_input="checkForm"
                />

                <PhoneInputField
                    ref="phone_number"

                    input_id="phone_number_input"
                    input_style_width="400px"
                    label_text="Изменение номера телефона"
                    placeholder="+7XXXXXXXXXX"
                    error_message="Неверный формат номера"
                    v-bind:on_input="checkForm"
                />

                <div class="row" style="padding: 8px; justify-content: space-between">
                    <DefaultButton
                        ref="change_button"
                        caption="Принять изменение"
                        v-bind:on_click="acceptChanges"
                    />

                    <DefaultButton
                        ref="cancel_button"
                        caption="Отменить изменение"
                        v-bind:on_click="cancelChanges"
                    />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import EmailInputField from "@/components/Commons/EmailInputField.vue";
import PhoneInputField from "@/components/Commons/PhoneInputField.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";

export default {
    name: "ClientProfilePage",
    components: {PhoneInputField, EmailInputField, DefaultButton},

    mounted() {
        this.getClientProfile();
        this.$refs.change_button.enable();
        this.$refs.cancel_button.enable();
    },

    methods: {
        checkForm() {
            if (this.$refs.phone_number.isValid() && this.$refs.email.isValid()) {
                this.$refs.change_button.enable();
            }
            else {
                this.$refs.change_button.disable();
            }
        },

        acceptChanges() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.PROFILE.SET_PROFILE.url,
                method: MY_APIS.CLIENT.PROFILE.SET_PROFILE.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    phone_number: page.$refs.phone_number.getPhoneNumber(),
                    email: page.$refs.email.getEmail(),
                }
            })
                .then(function (response) {
                    response;
                    alert("Профиль изменен");
                    page.getClientProfile();
                })
                .catch(function (exception) {
                    console.log(exception);
                    alert("Не удалось изменить профиль");
                    // page.$router.replace({ name: "ClientMain"});
                })
        },

        cancelChanges() {
            this.getClientProfile();
        },

        getClientProfile() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.PROFILE.GET_PROFILE.url,
                method: MY_APIS.CLIENT.PROFILE.GET_PROFILE.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword()
                }
            })
                .then(function (response) {
                    page.$refs.email.setEmail(response.data.email);
                    page.$refs.phone_number.setPhoneNumber(response.data.phoneNumber);
                })
                .catch(function (exception) {
                    console.log(exception);
                    // page.$router.replace({ name: "ClientMain"});
                })
        }
    }
}
</script>

<style scoped>

</style>