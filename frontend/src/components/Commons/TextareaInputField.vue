<template>
    <div class="column" style="width: fit-content; height: fit-content; padding: 8px;">
        <div style="text-align: left; padding: 4px">
            <label
                v-bind:for="input_id"
                v-text="label_text"
            />
        </div>

        <div>
            <textarea
                ref="input_field"

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
    name: "TextareaInputField",

    data() {
        return {
            is_valid: false
        }
    },

    mounted() {
        this.$refs.input_field.style.width = this.input_style_width;
        this.$refs.input_field.style.maxWidth = this.input_style_max_width;
        this.$refs.input_field.style.height = this.input_style_height;
        this.$refs.input_field.style.maxHeight = this.input_style_max_height;
    },

    props: ["input_id", "input_style_width", "input_style_height", "input_style_max_width", "input_style_max_height", "on_input", "label_text", "placeholder", "error_message"],

    methods: {
        getInputElement() {
            return this.$refs.input_field
        },

        setValue(val) {
            this.$refs.input_field.value = val;
        }
    }
}
</script>

<style scoped>

textarea {
    font-family: Arial, serif;
    font-size: 16px;
    box-sizing: border-box;
    margin: auto 0 auto 0;
    border: #c0c0c0 2px solid;
    border-radius: 5px;
    outline: none;
    padding: 8px;
}

textarea.error {
    border-color: #ff0000;
}

textarea:focus {
    border-color: #0060f0;
}

</style>