<template>
    <span ref="status">
        {{ status }}
    </span>
</template>

<script>
export default {
    name: "OrderStatus",

    props: ["status"],

    mounted() {
        this.updateStatus();
    },

    beforeUpdate() {
        this.updateStatus();
    },

    methods: {
        updateStatus() {
            if (this.status === "выполнен") {
                this.$refs.status.className = "done"
            }
            else if (this.status === "выполняется") {
                this.$refs.status.className = "progress"
            }
            else if (this.status === "отклонен") {
                this.$refs.status.className = "canceled"
            }
            else {
                this.$refs.status.className = "waiting"
            }
        }
    }
}
</script>

<style scoped>

span {
    font: inherit;
    font-weight: bold;
}

span.done {
    color: #00ff7f;
}

span.progress {
    color: #7fff00;
}

span.waiting {
    color: #ff7f00;
}

span.canceled {
    color: #ff0000;
}

</style>