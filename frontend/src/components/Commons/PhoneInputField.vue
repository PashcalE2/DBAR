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
                type="text"

                v-bind:id="input_id"
                v-bind:placeholder="placeholder"
                v-on:input="validateValue(); on_input();"
            />
        </div>

        <div class="error">
            <span class="error">{{ active_error_message }}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "PhoneInputField",

    data() {
        return {
            phone_number: "",
            is_valid: true,
            phone_number_re: /^\+7(\d{10})$/,

            simple_value: "",
            formatted_value: "",
            active_error_message: ""
        }
    },

    mounted() {
        this.$refs.input_field.style.width = this.input_style_width;
    },

    props: ["input_id", "input_style_width", "label_text", "placeholder", "error_message", "on_input"],

    methods: {
        getInputElement() {
            return this.$refs.input_field
        },

        isValid() {
            return this.is_valid;
        },

        validateValue() {
            let val = this.$refs.input_field.value;
            let re = this.phone_number_re.exec(val);
            if (re == null) {
                this.active_error_message = this.error_message;
                this.is_valid = false;
            }
            else {
                this.phone_number = "+7" + re[1];
                this.active_error_message = "";
                this.is_valid = true;
            }
        },

        getPhoneNumber() {
            return this.phone_number;
        },

        setPhoneNumber(val) {
            this.$refs.input_field.value = val;
            this.validateValue();
        }
    }
}
</script>

<style scoped>
</style>