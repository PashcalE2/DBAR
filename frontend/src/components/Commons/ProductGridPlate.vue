<template>
    <div ref="product" class="grid_plate">
        <img src="../../assets/logo.png" alt="Placeholder"/>
        <div class="price">
            {{ product_price + " ₽"}}
        </div>
        <div class="name">
            {{ product_name }}
        </div>
    </div>
</template>

<script>
export default {
    name: "ProductGridPlate",

    data() {
        return {
            is_in_order: false,
            is_hidden: false
        }
    },

    props: ["product_id", "product_name", "product_price"],

    methods: {
        isInOrder() {
            return this.is_in_order;
        },

        select() {
            this.$refs.product.className = "selected_grid_plate";
            this.is_in_order = true;
        },

        unselect() {
            this.$refs.product.className = "grid_plate"
            this.is_in_order = false;
        },

        getProductId() {
            return this.product_id;
        },

        getProductName() {
            return this.product_name;
        },

        hide() {
            this.$refs.product.style.display = "none"
            this.is_hidden = true;
        },

        show() {
            this.$refs.product.style.display = "flex"
            this.is_hidden = false;
        }
    }
}
</script>

<style scoped>

div.grid_plate, .selected_grid_plate {
    display: flex;
    flex-direction: column;
    width: 200px;
    height: 320px;
    margin: 10px auto 0 auto;
    background-color: #f0f0f0;

    border: #e0e0e0 4px solid;
    border-radius: 4px;
    box-sizing: border-box;
}

div.selected_grid_plate {
    border-color: #ff007f;
}

div.selected_grid_plate > div, div.grid_plate > div {
    text-align: left;
    padding: 8px;
}

div.grid_plate:hover, div.selected_grid_plate:hover {
    border-color: #007fff;
}

div.price {
    font-size: 22px;
    font-weight: bold;
    color: #ff007f;
}

div.name {
    font-size: 16px;
    font-weight: bold;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    line-height: 2;
    overflow: hidden;
}

</style>