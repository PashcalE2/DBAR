<template>
    <AdminWelcomeHeader/>

    <div class="centered_column">
        <div style="height: 100px"/>

        <div class="column" style="width: fit-content">
            <h1>Вход в систему</h1>

            <div class="column" style="margin: 0 auto">
                <StringInputField
                    ref="admin_id_input"

                    input_id="admin_id_input"
                    input_style_width="400px"
                    label_text="Идентификатор"
                    placeholder="Введите свой идентификатор"
                    v-bind:on_input="onLoginInput"
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

import DefaultButton from "@/components/Commons/DefaultButton.vue";
import PasswordInputField from "@/components/Commons/PasswordInputField.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as AdminStorage from "@/js/admin_storage";
import AdminWelcomeHeader from "@/components/Admin/AdminWelcomeHeader.vue";
import StringInputField from "@/components/Commons/StringInputField.vue";

export default {
    name: "AdminLoginPage",
    components: {StringInputField, AdminWelcomeHeader, PasswordInputField, DefaultButton},
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
                    NoSuchId: "Проверьте идентификатор",
                    EmptyField: "Введите свой идентификатор консультанта",
                    WrongSymbols: "Только цифры"
                },

                password: {
                    WrongPassword: "Неверный пароль",
                    WrongSymbols: "Только буквы латинского алфавита или цифры"
                }
            },

            login_re: /^[1-9]\d*$/,
            password_re: /^[a-zA-Z0-9]+$/
        }
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
            else if (this.isWrongString(element.value, this.login_re)) {
                this.form_errors.login = true;
                this.active_error_messages.login = this.error_messages.login.WrongSymbols;
            }
            else {
                this.form_errors.login = false;
                this.active_error_messages.login = "";
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
                url: MY_APIS.ADMIN.PROFILE.LOGIN.url,
                method: MY_APIS.ADMIN.PROFILE.LOGIN.method,
                data: {
                    id: page.input.login,
                    password: page.input.password
                }
            })
                .then(function (response) {
                    //console.log(response);

                    AdminStorage.setAdmin(
                        response.data.id,
                        response.data.password
                    );

                    page.$router.push({ name: "AdminMain"});
                })
                .catch(function (exception) {
                    console.log(exception);

                    if (exception.response.status === 409) {
                        page.form_errors.login = true;
                        page.active_error_messages.login = page.error_messages.login.NoSuchId;

                        page.form_errors.password = true;
                        page.active_error_messages.password = page.error_messages.password.WrongPassword;
                    }
                })
        }
    }
}
</script>

<style scoped>
</style>