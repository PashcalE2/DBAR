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
                type="search"
                autocomplete="false"
                list=""

                v-bind:id="input_id"
                v-bind:placeholder="placeholder"
                v-on:focus="showOptions()"
                v-on:blur="hideIfNotSelectingOption()"
                v-on:input="searchOptions(); on_input(this.$refs.input_field)"
            />
            <datalist ref="datalist" v-bind:id="datalist_id" v-on:mouseenter="selectingOptions()" v-on:mouseleave="leavingOptions">
                <option v-for="(option, i) in datalist_options"
                        v-bind:key="i"
                        v-bind:value="option"
                        v-bind:name="option"
                        v-text="option"
                        v-on:click="selectOption(i)"
                />
            </datalist>
        </div>

        <div class="error">
            <span class="error">{{ error_message }}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "ListInputField",

    data() {
        return {
            is_valid: false,
            is_selecting: false
        }
    },

    mounted() {
        this.$refs.input_field.style.width = this.input_style_width;
        this.$refs.datalist.style.width = this.$refs.input_field.style.width;
    },

    props: ["label_text", "input_id", "input_style_width", "on_input", "datalist_id", "placeholder", "datalist_options", "error_message"],

    methods: {
        getInputElement() {
            return this.$refs.input_field
        },

        showOptions() {
            this.$refs.datalist.style.display = "block";
            this.$refs.input_field.className = "focused";

            this.searchOptions();
        },

        hideOptions() {
            this.$refs.datalist.style.display = "none";
            this.$refs.input_field.className = "";
        },

        hideIfNotSelectingOption() {
            if (this.is_selecting) {
                this.$refs.input_field.focus();
            }
            else {
                this.hideOptions();
            }
        },

        selectingOptions() {
            this.is_selecting = true;
        },

        leavingOptions() {
            this.is_selecting = false;
        },

        selectOption(option) {
            this.$refs.input_field.value = this.datalist_options[option];
            this.on_input(this.$refs.input_field)
            this.hideOptions();
        },

        searchOptions() {
            let text = this.$refs.input_field.value.toLowerCase();
            let not_found = true;
            for (let option of this.$refs.datalist.options) {
                if (option.value.toLowerCase().indexOf(text) > -1){
                    option.style.display = "block";
                    not_found = false;
                }
                else {
                    option.style.display = "none";
                }
            }

            if (not_found) {
                this.$refs.datalist.style.display = "none";
            }
            else {
                this.$refs.datalist.style.display = "block";
            }
        },
    }
}
</script>

<style scoped>

datalist {
    position: absolute;
    font-family: sans-serif;
    padding: 4px;
    border: 2px solid #0060f0;
    border-top: none;
    box-sizing: border-box;
    overflow-y: auto;
    max-height: 10rem;
    background-color: #ffffff;
}

option {
    text-align: left;
    padding: 4px;
    background: #ffffff;
}

input.focused {
    border-radius: 5px 5px 0 0;
}

option:hover {
    background: #007fff;
}


</style>