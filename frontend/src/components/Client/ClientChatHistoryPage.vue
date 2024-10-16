<template>
    <div class="column" style="width: 800px; margin: 0 auto 0 auto">
        <div class="row" style="justify-content: space-between; border-bottom: #e0e0e0 4px solid">
            <div class="column">
                <div class="admin_name">
                    {{ admin.name }}
                </div>
                <div class="admin_contacts">
                    {{ (admin.phone_number[0] !== "+" ? "+" : "") + admin.phone_number }}
                </div>
                <div class="admin_contacts">
                    {{ admin.email }}
                </div>
            </div>
            <div class="column" style="width: 200px; padding: 8px">
                <DefaultButton
                        ref="back_button"
                        caption="Вернуться к заказу"
                        v-bind:on_click="backOnClick"
                />
            </div>
        </div>

        <div style="max-height: 500px; overflow-y: scroll;">
            <ChatMessage
                    v-for="(message, i) in chat_history"
                    v-bind:key="i"
                    v-bind:content="message.content"
                    v-bind:posted="reformatDateTime(message.posted)"
                    v-bind:from_current_user="message.from_user"
            />
        </div>

        <div class="row" id="text_window">
            <textarea ref="chat_input" id="chat_input" placeholder="Введите сообщение"/>

            <div id="chat_send_button">
                <DefaultButton
                    ref="send_message"
                    caption="Отправить"
                    v-bind:on_click="chatSendOnClick"
                />
            </div>

        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import ChatMessage from "@/components/Commons/ChatMessage.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";
import {reformatDateTime} from "@/js/utils";

export default {
    name: "ClientChatHistoryPage",
    components: {ChatMessage, DefaultButton},

    data() {
        return {
            admin: {
                name: "",
                phone_number: "",
                email: ""
            },

            order_id: 0,
            chat_history: []
        }
    },

    mounted() {
        if (this.$route.query === undefined) {
            this.$router.replace({ name: "ClientMain"});
            return;
        }

        this.order_id = this.$route.query.order_id;

        this.$refs.back_button.enable();
        this.$refs.send_message.enable();

        this.getAdmin();
        this.getMessages();
    },

    methods: {
        reformatDateTime,

        backOnClick() {
            this.$refs.back_button.disable();
            this.$router.replace({ name: "ClientOrderHistory", query: { order_id: this.order_id } });
        },

        chatSendOnClick() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.CHAT.POST_MESSAGE.url,
                method: MY_APIS.CLIENT.CHAT.POST_MESSAGE.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_id,
                    content: page.$refs.chat_input.value
                }
            })
                .then(function (response) {
                    response;
                    page.$refs.chat_input.value = "";
                    page.getMessages();
                })
                .catch(function (exception) {
                    console.log(exception);
                    // page.$router.replace({ name: "ClientMain"});
                })
        },

        getAdmin() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.CHAT.GET_ADMIN.url,
                method: MY_APIS.CLIENT.CHAT.GET_ADMIN.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.admin = {
                        name: response.data.fullName,
                        phone_number: response.data.phoneNumber,
                        email: response.data.email
                    };
                })
                .catch(function (exception) {
                    console.log(exception);
                    // page.$router.replace({ name: "ClientMain"});
                })
        },

        getMessages() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.CHAT.GET_MESSAGES.url,
                method: MY_APIS.CLIENT.CHAT.GET_MESSAGES.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.chat_history = [];

                    for (let message of response.data) {
                        page.chat_history.push({
                            from_user: message.sender === "клиент",
                            content: message.content,
                            posted: message.sentAt
                        });
                    }
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

div.admin_name {
    padding: 8px;
    text-align: left;
    font-size: 32px;
    font-weight: bold;
}

div.admin_contacts {
    padding: 8px;
    text-align: left;
    font-size: 26px;
    font-weight: bold;
}

textarea#chat_input {
    font-family: Arial, serif;
    font-size: 20px;

    width: 600px;
    height: 120px;
    padding: 4px;
    box-sizing: border-box;
    border: 2px solid #e0e0e0;
    border-radius: 4px;
    background-color: #f0f0f0;
    resize: none;
}

div#text_window {
    justify-content: space-between;
    padding: 8px;

    outline-color: #007fff;
    border-top: #007fff 4px solid;
}

div#chat_send_button {
    width: 160px;
    margin: auto
}

</style>