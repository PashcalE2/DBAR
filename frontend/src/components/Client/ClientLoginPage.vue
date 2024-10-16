<template>
    <ClientWelcomeHeader/>

    <div class="centered_column">
        <div style="height: 100px"/>

        <div class="column" style="width: fit-content">
            <h1>Вход в систему</h1>

            <div class="column" style="margin: 0 auto">
                <ListInputField
                    ref="organization"

                    input_id="organization_input"
                    input_style_width="400px"
                    datalist_id="organization_datalist"
                    label_text="Организация"
                    placeholder="Начните вводить название организации"

                    v-bind:on_input="onLoginInput"
                    v-bind:datalist_options="organizations_options"
                    v-bind:error_message="active_error_messages.login"
                />

                <PasswordInputField
                    ref="password"

                    input_id="password_input"
                    input_style_width="400px"
                    label_text="Пароль"
                    placeholder="Password"
                    v-bind:on_input="onPasswordInput"
                    v-bind:error_message="active_error_messages.password"
                />

                <div class="column" style="padding: 8px;">
                    <DefaultButton
                        ref="login_button"
                        caption="Войти"
                        v-bind:on_click="login"
                    />

                    <span style="margin: 4px auto 4px auto">или</span>

                    <DefaultButton
                        ref="register_button"
                        caption="Зарегистрироваться"
                        v-bind:on_click="register"
                    />
                </div>
            </div>

            <p style="word-break: normal; padding: 8px; text-align: left;">
                Забыли пароль? Сообщите нам! <br>
                Контактная информация наших служб поддержки: <span style="color: #007fff">example@example.ru</span>
            </p>
        </div>
    </div>
</template>

<script>

import ListInputField from "@/components/Commons/ListInputField.vue";
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import PasswordInputField from "@/components/Commons/PasswordInputField.vue";
import ClientWelcomeHeader from "@/components/Client/ClientWelcomeHeader.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";

export default {
    name: "ClientLoginPage",
    components: {ClientWelcomeHeader, PasswordInputField, DefaultButton, ListInputField},
    data() {
        return {
            input: {
                login: "",
                password: ""
            },

            organizations_options: [
                "Если опций не появилось - обновите страницу"
            ],

            form_errors: {
                login: false,
                password: false
            },

            active_error_messages: {
                login: "",
                password: ""
            },

            error_messages: {
                login: {
                    NoSuchOrganization: "Такой организации нет",
                    EmptyField: "Введите название организации"
                },

                password: {
                    WrongPassword: "Неверный пароль",
                    WrongSymbols: "Только буквы латинского алфавита или цифры"
                }
            },

            login_re: /^[а-яА-Яa-zA-Z0-9 \-'".,&]+$/,
            password_re: /^[a-zA-Z0-9]+$/
        }
    },

    mounted() {
        let page = this;

        axios.request({
            url: MY_APIS.CLIENT.PROFILE.GET_REGISTERED_ORGANIZATIONS.url,
            method: MY_APIS.CLIENT.PROFILE.GET_REGISTERED_ORGANIZATIONS.method
        })
            .then(function (response) {
                // console.log(response.data);
                page.organizations_options = response.data;
            })
            .catch(function (exception) {
                console.log(exception);
            })

        this.$refs.register_button.enable();
    },

    methods: {
        isWrongString(str, re) {
            let re_result = re.exec(str);
            return (re_result == null) || (re_result[0].length !== str.length)
        },

        canLogin() {
            return (this.input.login.length !== 0) && (!this.form_errors.login) && (this.input.password.length !== 0) && (!this.form_errors.password);
        },

        onLoginInput(element) {
            this.input.login = element.value;
            let is_empty = element.value.length === 0;

            if (is_empty) {
                this.form_errors.login = true;
                this.active_error_messages.login = this.error_messages.login.EmptyField;
            }
            else {
                this.form_errors.login = false;
            }

            if (!this.form_errors.login) {
                if (!is_empty && !(this.organizations_options.includes(element.value))) {
                    this.form_errors.login = true;
                    this.active_error_messages.login = this.error_messages.login.NoSuchOrganization;
                } else {
                    this.form_errors.login = false;
                    this.active_error_messages.login = "";
                }
            }

            if (this.canLogin()) {
                this.$refs.login_button.enable();
            }
            else {
                this.$refs.login_button.disable();
            }
        },

        onPasswordInput(element) {
            this.input.password = element.value;

            if (element.value.length !== 0 && this.isWrongString(element.value, this.password_re)) {
                this.form_errors.password = true;
                this.active_error_messages.password = this.error_messages.password.WrongSymbols;
            }
            else {
                this.form_errors.password = false;
                this.active_error_messages.password = "";
            }

            if (this.canLogin()) {
                this.$refs.login_button.enable();
            }
            else {
                this.$refs.login_button.disable();
            }
        },

        login() {
            if (this.form_errors.login || this.form_errors.password) {
                return false;
            }

            let page = this;

            this.$refs.login_button.disable();

            axios.request({
                url: MY_APIS.CLIENT.PROFILE.LOGIN.url,
                method: MY_APIS.CLIENT.PROFILE.LOGIN.method,
                data: {
                    name: page.input.login,
                    password: page.input.password
                }
            })
                .then(function (response) {
                    //console.log(response);

                    ClientStorage.setClient(
                        response.data.id,
                        page.input.login,
                        response.data.email,
                        response.data.phoneNumber,
                        response.data.password
                    );

                    page.$router.push({ name: "ClientMain"});
                })
                .catch(function (exception) {
                    //console.log(exception);

                    if (exception.response.status === 409) {
                        page.form_errors.password = true;
                        page.active_error_messages.password = page.error_messages.password.WrongPassword;
                    }
                })
        },

        register() {
            let page = this;

            this.$refs.register_button.disable();
            page.$router.push({ name: "ClientRegister"});
        }
    }
}
</script>

<style scoped>
</style>