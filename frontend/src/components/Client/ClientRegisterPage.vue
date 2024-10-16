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
                    label_text="Выберите организацию"
                    placeholder="Начните вводить название организации"

                    v-bind:on_input="onLoginInput"
                    v-bind:datalist_options="organizations_options"
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
import ListInputField from "@/components/Commons/ListInputField.vue";
import PasswordInputField from "@/components/Commons/PasswordInputField.vue";
import PhoneInputField from "@/components/Commons/PhoneInputField.vue";
import ClientWelcomeHeader from "@/components/Client/ClientWelcomeHeader.vue";
import EmailInputField from "@/components/Commons/EmailInputField.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";

export default {
    name: "ClientRegisterPage",
    components: {
        EmailInputField,
        ClientWelcomeHeader,
        PhoneInputField, PasswordInputField, ListInputField, DefaultButton},

    data() {
        return {
            input: {
                login: "",
                password: ""
            },

            organizations_options: [
                "Mozilla",
                "Chrome",
                "Firefox"
            ],

            form_errors: {
                login: false,
                password: false,
                repeat_password: false
            },

            active_error_messages: {
                login: "",
                password: "",
                repeat_password: ""
            },

            error_messages: {
                login: {
                    NoSuchOrganization: "Такой организации нет",
                    EmptyField: "Введите название организации"
                },

                password: {
                    WrongPassword: "Пароли не совпадают",
                    WrongSymbols: "Только буквы латинского алфавита или цифры"
                }
            },

            login_re: /^[а-яА-Яa-zA-Z0-9 \-'".,&]+$/,
            password_re: /^[a-zA-Z0-9]+$/
        }
    },

    mounted() {
        this.getOrganizations();

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

            page.$refs.register_button.disable();

            axios.request({
                url: MY_APIS.CLIENT.PROFILE.REGISTER.url,
                method: MY_APIS.CLIENT.PROFILE.REGISTER.method,
                data: {
                    name: page.input.login,
                    password: page.input.password,
                    phoneNumber: page.$refs.phone_number.getPhoneNumber(),
                    email: page.$refs.email.getEmail()
                }
            })
                .then(function (response) {
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
                    console.log(exception);
                })
        },

        getOrganizations() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.PROFILE.GET_NOT_REGISTERED_ORGANIZATIONS.url,
                method: MY_APIS.CLIENT.PROFILE.GET_NOT_REGISTERED_ORGANIZATIONS.method
            })
                .then(function (response) {
                    console.log(response.data);
                    page.organizations_options = response.data;
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