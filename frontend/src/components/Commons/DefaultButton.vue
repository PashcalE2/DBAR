<template>
    <div class="column">
        <button ref="button" v-on:click="clickIfEnabled">{{ caption }}</button>
    </div>
</template>

<script>
export default {
    name: "DefaultButton",

    data() {
        return {
            is_enabled: false
        }
    },

    props: ["caption", "on_click"],

    methods: {
        disable() {
            this.$refs.button.className = "disabled";
            this.is_enabled = false;
        },

        enable() {
            this.$refs.button.className = "enabled";
            this.is_enabled = true;
        },

        fit() {
            this.$refs.button.style.width = "fit-content";
        },

        fill() {
            this.$refs.button.style.width = "auto";
        },

        clickIfEnabled() {
            if (this.is_enabled) {
                this.on_click(this.$refs.button);
            }
        }
    },

    mounted() {
        this.disable();
        this.fill();
    },
}
</script>

<style scoped>

button {
    font-size: 16px;
    font-weight: bold;
    padding: 12px;
    border: 0 solid;
    border-radius: 5px;
    min-width: 50px;
}

button.enabled {
    color: #ffffff;
    background-color: #007fff;
}

button.disabled {
    color: #404040;
    background-color: #808080;
}

button.enabled:hover {
    background-color: #0060f0;
}

</style>