<template>
    <div class="column" style="width: fit-content; height: fit-content; padding: 8px;">
        <div style="text-align: left; padding: 4px">
            <label
                v-bind:for="input_id"
                v-text="label_text"
            />
        </div>

        <div>
            <input
                ref="input_field"
                type="password"

                v-bind:id="input_id"
                v-bind:placeholder="placeholder"
                v-on:input="on_input(this.$refs.input_field);"
            />
        </div>

        <div class="error">
            <span class="error">{{ error_message }}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "PasswordInputField",

    data() {
        return {
            is_valid: false
        }
    },

    mounted() {
        this.$refs.input_field.style.width = this.input_style_width;
    },

    props: ["input_id", "input_style_width", "on_input", "label_text", "placeholder", "error_message"],

    methods: {
        getInputElement() {
            return this.$refs.input_field
        },
    }
}
</script>

<style scoped>
</style>