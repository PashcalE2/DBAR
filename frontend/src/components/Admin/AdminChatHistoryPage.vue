<template>
    <div class="column" style="width: 800px; margin: 0 auto 0 auto">
        <div class="row" style="justify-content: space-between; border-bottom: #e0e0e0 4px solid">
            <div class="column">
                <div class="client_name">
                    {{ client.name }}
                </div>
                <div class="client_contacts">
                    {{ (client.phone_number[0] !== "+" ? "+" : "") + client.phone_number }}
                </div>
                <div class="client_contacts">
                    {{ client.email }}
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
import * as AdminStorage from "@/js/admin_storage";
import {reformatDateTime} from "@/js/utils";

export default {
    name: "AdminChatHistoryPage",
    components: {ChatMessage, DefaultButton},

    data() {
        return {
            client: {
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
            this.$router.replace({ name: "AdminMain"});
            return;
        }

        this.order_id = this.$route.query.order_id;

        this.$refs.back_button.enable();
        this.$refs.send_message.enable();

        this.getClient();
        this.getMessages();
    },

    methods: {
        reformatDateTime,

        backOnClick() {
            this.$refs.back_button.disable();
            this.$router.replace({ name: "AdminOrderHistory", query: { order_id: this.order_id } });
        },

        chatSendOnClick() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.CHAT.POST_MESSAGE.url,
                method: MY_APIS.ADMIN.CHAT.POST_MESSAGE.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
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
                    // page.$router.replace({ name: "AdminMain"});
                })
        },

        getClient() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.CHAT.GET_CLIENT.url,
                method: MY_APIS.ADMIN.CHAT.GET_CLIENT.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    console.log(response);

                    page.client = {
                        name: response.data.organization,
                        phone_number: response.data.phoneNumber,
                        email: response.data.email
                    };
                })
                .catch(function (exception) {
                    console.log(exception);
                    // page.$router.replace({ name: "AdminMain"});
                })
        },

        getMessages() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.CHAT.GET_MESSAGES.url,
                method: MY_APIS.ADMIN.CHAT.GET_MESSAGES.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword(),
                    order_id: page.order_id
                }
            })
                .then(function (response) {
                    page.chat_history = [];

                    for (let message of response.data) {
                        page.chat_history.push({
                            from_user: message.sender === "консультант",
                            content: message.content,
                            posted: message.sentAt
                        });
                    }
                })
                .catch(function (exception) {
                    console.log(exception);
                    // page.$router.replace({ name: "AdminMain"});
                })
        }
    }
}
</script>

<style scoped>

div.client_name {
    padding: 8px;
    text-align: left;
    font-size: 32px;
    font-weight: bold;
}

div.client_contacts {
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