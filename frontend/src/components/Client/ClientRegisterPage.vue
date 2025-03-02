<template>
    <ClientWelcomeHeader/>

    <div class="centered_column">
        <div style="height: 100px"/>

        <div class="column" style="width: fit-content">
            <h1>Вход в систему</h1>

            <div class="column" style="margin: 0 auto">
                <StringInputField
                    ref="name"

                    input_id="name_input"
                    input_style_width="400px"
                    label_text="Организация"
                    placeholder="Введите имя своей организации"
                    v-bind:on_input="onNameInput"
                    v-bind:error_message="active_error_messages.name"
                />

                <StringInputField
                    ref="login"

                    input_id="login_input"
                    input_style_width="400px"
                    label_text="Логин"
                    placeholder="Введите свой логин"
                    v-bind:on_input="onLoginInput"
                    v-bind:error_message="active_error_messages.login"
                />

                <EmailInputField
                    ref="email"

                    input_id="reg_email_input"
                    input_style_width="400px"
                    label_text="Адрес электронной почты"
                    placeholder="example.example@example.com"
                    error_message="Неверный формат почты"
                    v-bind:on_input="checkForm"
                />

                <PhoneInputField
                    ref="phone_number"

                    input_id="reg_phone_number_input"
                    input_style_width="400px"
                    label_text="Номер телефона"
                    placeholder="+7XXXXXXXXXX"
                    error_message="Неверный формат номера"
                    v-bind:on_input="checkForm"
                />

                <PasswordInputField
                    ref="password"

                    input_id="password_input"
                    input_style_width="400px"
                    label_text="Придумайте пароль"
                    placeholder=""
                    v-bind:on_input="onPasswordInput"
                    v-bind:error_message="active_error_messages.password"
                />

                <PasswordInputField
                    ref="repeat_password"

                    input_id="repeat_password_input"
                    input_style_width="400px"
                    label_text="Повторите пароль"
                    placeholder=""
                    v-bind:on_input="onRepeatPasswordInput"
                    v-bind:error_message="active_error_messages.repeat_password"
                />

                <div class="column" style="padding: 8px;">
                    <DefaultButton
                        ref="register_button"
                        caption="Зарегистрироваться"
                        v-bind:on_click="register"
                    />

                    <span style="margin: 4px auto 4px auto">или</span>

                    <DefaultButton
                        ref="login_button"
                        caption="Войти"
                        v-bind:on_click="login"
                    />
                </div>
            </div>

            <p style="word-break: normal; padding: 8px; text-align: left">
                Не можете найти свою организацию? Сообщите нам! <br>
                Контактная информация наших служб поддержки: <span style="color: #007fff">example@example.ru</span>
            </p>
        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import PasswordInputField from "@/components/Commons/PasswordInputField.vue";
import PhoneInputField from "@/components/Commons/PhoneInputField.vue";
import ClientWelcomeHeader from "@/components/Client/ClientWelcomeHeader.vue";
import EmailInputField from "@/components/Commons/EmailInputField.vue";
import axios from "axios";
import {BACKEND_API} from "@/js/backend_apis";
import * as ClientStorage from "@/js/client_storage";
import StringInputField from "@/components/Commons/StringInputField.vue";

export default {
    name: "ClientRegisterPage",
    components: {
      StringInputField,
        EmailInputField,
        ClientWelcomeHeader,
        PhoneInputField, PasswordInputField, DefaultButton},

    data() {
        return {
            input: {
                name: "",
                login: "",
                password: ""
            },

            form_errors: {
                name: false,
                login: false,
                password: false,
                repeat_password: false
            },

            active_error_messages: {
                name: "",
                login: "",
                password: "",
                repeat_password: ""
            },

            error_messages: {
                name: {
                    EmptyField: "Введите название организации"
                },

                login: {
                    EmptyField: "Введите логин",
                    WrongSymbols: "Только буквы латинского алфавита или цифры (первый символ - буква)"
                },

                password: {
                    WrongPassword: "Пароли не совпадают",
                    WrongSymbols: "Только буквы латинского алфавита или цифры"
                }
            },

            name_re: /^[а-яА-Яa-zA-Z()'"<>&. -]+$/,
            login_re: /^[а-яА-Яa-zA-Z0-9 \-'".,&]+$/,
            password_re: /^[a-zA-Z0-9]+$/
        }
    },

    mounted() {
        this.$refs.login_button.enable();
    },

    methods: {
        isWrongString(str, re) {
            return re.exec(str) == null
        },

        checkForm() {
            if (
                !this.form_errors.login &&
                this.$refs.email.isValid() &&
                this.$refs.phone_number.isValid() &&
                !this.form_errors.password &&
                !this.form_errors.repeat_password
            ) {
                this.$refs.register_button.enable();
            }
            else {
                this.$refs.register_button.disable();
            }
        },

        onNameInput(element) {
            this.input.name = element.value;
            let is_empty = element.value.length === 0;

            if (is_empty) {
                this.form_errors.name = true;
                this.active_error_messages.name = this.error_messages.name.EmptyField;
            }
            else if (this.isWrongString(element.value, this.name_re)) {
                this.form_errors.name = true;
                this.active_error_messages.name = this.error_messages.name.WrongSymbols;
            }
            else {
                this.form_errors.name = false;
            }

            if (!this.form_errors.name) {
                this.active_error_messages.name = "";
            }

            this.checkForm();
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
            }

            if (!this.form_errors.login) {
                this.active_error_messages.login = "";
            }

            this.checkForm();
        },

        onPasswordInput(element) {
            this.input.password = element.value;
            this.$refs.repeat_password.getInputElement().value = "";

            if (element.value.length !== 0 && this.isWrongString(element.value, this.password_re)) {
                this.form_errors.password = true;
                this.active_error_messages.password = this.error_messages.password.WrongSymbols;
            }
            else {
                this.form_errors.password = false;
                this.active_error_messages.password = "";
            }

            this.checkForm();
        },

        onRepeatPasswordInput(element) {
            this.input.password = element.value;

            if (element.value.length !== 0 && this.isWrongString(element.value, this.password_re)) {
                this.form_errors.repeat_password = true;
                this.active_error_messages.repeat_password = this.error_messages.password.WrongSymbols;
            }
            else if (element.value !== this.$refs.password.getInputElement().value) {
                this.form_errors.repeat_password = true;
                this.active_error_messages.repeat_password = this.error_messages.password.WrongPassword;
            }
            else {
                this.form_errors.repeat_password = false;
                this.active_error_messages.repeat_password = "";
            }

            this.checkForm();
        },

        login() {
            if (this.form_errors.login || this.form_errors.password) {
                return false;
            }

            let page = this;

            page.$refs.register_button.disable();
            page.$router.push({ name: "ClientLogin"});
        },

        register() {
            if (this.form_errors.login || this.form_errors.password || this.form_errors.repeat_password) {
                return false;
            }

            let page = this;
            let endpoint = BACKEND_API.MAIN_SERVICE.CLIENT.PROFILE.REGISTER;

            page.$refs.register_button.disable();

            axios.request({
                url: endpoint.url,
                method: endpoint.method,
                data: {
                    name: page.input.name,
                    login: page.input.login,
                    password: page.input.password,
                    phoneNumber: page.$refs.phone_number.getPhoneNumber(),
                    email: page.$refs.email.getEmail()
                }
            })
                .then(function (response) {
                    ClientStorage.setClient(
                        response.data.jwtPairResponse.access,
                        response.data.jwtPairResponse.refresh
                    );

                    page.$router.push({ name: "ClientMain"});
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        }
    }
}
</script>

<style scoped>

</style>