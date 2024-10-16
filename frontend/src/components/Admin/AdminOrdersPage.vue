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
import * as AdminStorage from "@/js/admin_storage";
import {reformatDate} from "@/js/utils";

export default {
    name: "AdminOrdersPage",
    components: {OrderGridPlate},

    data() {
        return {
            orders_info: []
        }
    },

    mounted() {
        this.getAdminOrders();
    },

    methods: {
        reformatDate,
        goToOrderHistory(id) {
            this.$router.replace({ name: "AdminOrderHistory" , query: { order_id: id } });
        },

        getAdminOrders() {
            let page = this;

            axios.request({
                url: MY_APIS.ADMIN.ORDER.GET_ALL_INFO.url,
                method: MY_APIS.ADMIN.ORDER.GET_ALL_INFO.method,
                params: {
                    admin_id: AdminStorage.getId(),
                    password: AdminStorage.getPassword()
                }
            })
                .then(function (response) {
                    page.orders_info = response.data;
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