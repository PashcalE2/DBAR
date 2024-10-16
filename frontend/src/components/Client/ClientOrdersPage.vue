<template>
    <div class="column">
        <div class="grid">
            <OrderGridPlate
                v-for="(order, i) in orders_info"
                v-bind:key="i"
                v-bind:order_id="order.id"
                v-bind:order_status="order.status"
                v-bind:order_formed_date="reformatDate(order.formedAt)"
                v-bind:order_done_date="reformatDate(order.doneAt)"
                v-bind:order_sum="order.sum"
                v-bind:on_click="goToOrderHistory"
            />
        </div>
    </div>
</template>

<script>
import OrderGridPlate from "@/components/Commons/OrderGridPlate.vue";
import axios from "axios";
import {MY_APIS} from "@/js/my_apis";
import * as ClientStorage from "@/js/client_storage";
import {reformatDate} from "@/js/utils";

export default {
    name: "ClientOrdersPage",
    components: {OrderGridPlate},

    data() {
        return {
            orders_info: []
        }
    },

    mounted() {
        this.getClientOrders();
    },

    methods: {
        reformatDate,
        goToOrderHistory(id) {
            this.$router.replace({ name: "ClientOrderHistory" , query: { order_id: id } });
        },

        getClientOrders() {
            let page = this;

            axios.request({
                url: MY_APIS.CLIENT.ORDER.GET_ALL_INFO.url,
                method: MY_APIS.CLIENT.ORDER.GET_ALL_INFO.method,
                params: {
                    client_id: ClientStorage.getId(),
                    password: ClientStorage.getPassword()
                }
            })
                .then(function (response) {
                    let all = response.data;
                    let without_current = []

                    for (let i = 0; i < all.length; i++) {
                        if (all[i].status !== "формируется") {
                            without_current.push(all[i]);
                        }
                    }

                    page.orders_info = without_current;
                })
                .catch(function (exception) {
                    console.log(exception);
                })
        }
    }
}
</script>

<style scoped>

div.grid {
    display: grid;
    grid-gap: 1px;
    grid-template-columns: repeat(5, 1fr);
    padding: 8px;
}

</style>