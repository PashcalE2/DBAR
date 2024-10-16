<template>
    <div class="grid_plate"
        v-on:click="on_click(order_id)"
    >
        <div class="name">
            {{ "Заказ №" + order_id }}
        </div>
        <div class="description">
            Состояние: <OrderStatus
                v-bind:status="order_status"
            />
        </div>
        <div class="description">
            {{ "Дата принятия: " + order_formed_date }}
        </div>
        <div class="description">
            {{ "Дата завершения: " + order_done_date }}
        </div>
        <div class="description">
            Сумма: <span class="price">{{ order_sum + " ₽"}}</span>
        </div>
    </div>
</template>

<script>
import OrderStatus from "@/components/Commons/OrderStatus.vue";

export default {
    name: "ProductGridPlate",
    components: {OrderStatus},

    props: ["order_id", "order_status", "order_formed_date", "order_done_date", "order_sum", "on_click"]
}
</script>

<style scoped>

div.description {
    font-weight: bold;
}

div.grid_plate, .selected_grid_plate {
    display: flex;
    flex-direction: column;
    width: 300px;
    height: 200px;
    margin: 10px auto 0 auto;
    background-color: #f0f0f0;

    border: #e0e0e0 4px solid;
    border-radius: 4px;
    box-sizing: border-box;
}

div.selected_grid_plate {
    border-color: #00ff7f;
}

div.selected_grid_plate > div {
    text-align: left;
    padding: 8px;
}

div.grid_plate > div {
    text-align: left;
    padding: 8px;
}

div.grid_plate:hover, div.selected_grid_plate:hover {
    border-color: #007fff;
}

span.price {
    font-size: 22px;
    font-weight: bold;
    color: #ff007f;
}

div.name {
    font-size: 26px;
    font-weight: bold;
}

span.status_progress {
    color: #ff7f00;
}

</style>