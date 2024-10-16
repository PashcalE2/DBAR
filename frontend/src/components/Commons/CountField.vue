<template>
    <div class="column" style="padding: 8px;">
        <div class="row" style="text-align: left; padding: 4px">
            <label
                    v-bind:for="input_id"
                    v-text="label_text"
            />
        </div>

        <div class="row">
            <div style="padding: 4px; margin: auto">
                <DefaultButton
                        ref="minus"
                        caption="-"
                        v-bind:on_click="dec"
                />
            </div>

            <div style="padding: 4px">
                <StringInputField
                    ref="count_input"
                    v-bind:input_id="input_id"
                    input_style_width="200px"
                    v-bind:on_input="onCounterInput"
                    label_text=""
                    placeholder="Введите количество"
                    v-bind:error_message="count_error"
                />
            </div>

            <div style="padding: 4px; margin: auto">
                <DefaultButton
                        ref="plus"
                        caption="+"
                        v-bind:on_click="inc"
                />
            </div>
        </div>

        <div class="row">
            <DefaultButton
                ref="accept"
                caption="Применить"
                v-bind:on_click="on_change_count"
            />
        </div>
    </div>
</template>

<script>
import DefaultButton from "@/components/Commons/DefaultButton.vue";
import StringInputField from "@/components/Commons/StringInputField.vue";

export default {
    name: "CountField",
    components: {StringInputField, DefaultButton},

    data() {
        return {
            counter: 1,
            max_count: 1000,
            str_value: "",

            number_re: /[1-9]\d*/,
            count_error: "",
            is_valid: false
        }
    },

    mounted() {
        this.$refs.minus.enable();
        this.$refs.plus.enable();
        this.$refs.accept.enable();
    },

    props: ["input_id", "input_style_width", "on_change_count", "label_text", "placeholder", "error_message"],

    methods: {
        getInputElement() {
            return this.$refs.input_field
        },

        inc() {
            if (this.counter < this.max_count) {
                this.setCount(this.counter + 1);
            }

            if (this.counter >= this.max_count) {
                this.$refs.plus.disable();
            }
            else {
                this.$refs.plus.enable();
            }
        },

        dec() {
            if (this.counter > 1) {
                this.setCount(this.counter - 1);
            }

            if (this.counter <= 1) {
                this.$refs.minus.disable();
            }
            else {
                this.$refs.minus.enable();
            }
        },

        onCounterInput(element) {
            let text = element.value;

            if (this.number_re.exec(text) == null) {
                this.count_error = "Неверное число";
                this.is_valid = false;
            }
            else {
                this.count_error = "";
                this.setCount(parseInt(text));
                this.is_valid = true;
            }
        },

        isValid() {
            return this.is_valid;
        },

        setCount(new_count) {
            this.counter = new_count;
            this.$refs.count_input.getInputElement().value = "" + new_count;
        },

        getValue() {
            return this.counter;
        }
    }
}
</script>

<style scoped>

</style>