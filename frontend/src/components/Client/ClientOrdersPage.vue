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
import {BACKEND_API} from "@/js/backend_apis";
import * as ClientStorage from "@/js/client_storage";
import {reformatDate} from "@/js/utils";
import {ENUMS} from "@/js/model/enums";

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
            let endpoint = BACKEND_API.MAIN_SERVICE.CLIENT.ORDER.GET_ALL_INFO;

            axios.request({
                url: endpoint.url,
                method: endpoint.method,
                headers: {
                  "Authorization": "Bearer " + ClientStorage.getAccessToken()
                }
            })
                .then(function (response) {
                    let all = response.data;
                    let without_current = []

                    for (let i = 0; i < all.length; i++) {
                        if (all[i].status !== ENUMS.ORDER_STATUS.BEING_FORMED) {
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