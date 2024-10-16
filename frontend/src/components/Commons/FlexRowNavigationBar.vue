<template>
    <div class="row" style="justify-content: space-between; background-color: #007fff">
        <ul>
            <li
                ref="list"
                v-for="(element, i) in row_elements"
                v-bind:key="i"
                v-bind:id="'client_nav_' + element.ref"
                v-on:click="element.onClick(this.$refs.list[i]);"
            >
                {{ element.value }}
            </li>
        </ul>

        <ul style="flex-direction: row-reverse">
            <li
                ref="reversed_list"
                v-for="(element, i) in reversed_row_elements"
                v-bind:key="i"
                v-bind:id="'client_nav_' + element.ref"
                v-on:click="element.onClick(this.$refs.reversed_list[i]);"
            >
                {{ element.value }}
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "FlexRowNavigationBar",

    data() {
        return {
            ref_to_element: {},
            id_is_disabled: {},
            last_enabled_element: null,
        }
    },

    props: ["row_elements", "reversed_row_elements"],

    mounted() {
        for (let i = 0; i < this.row_elements.length; i++) {
            let ref = this.row_elements[i].ref;
            let element = this.$refs.list[i];
            this.ref_to_element[ref] = element;

            this.disableElement(element);
        }

        for (let i = 0; i < this.reversed_row_elements.length; i++) {
            let ref = this.reversed_row_elements[i].ref;
            let element = this.$refs.reversed_list[i];
            this.ref_to_element[ref] = element;

            this.disableElement(element);
        }
    },

    methods: {
        isDisabled(element) {
            return this.id_is_disabled[element.id];
        },

        isEnabled(element) {
            return !(this.id_is_disabled[element.id]);
        },

        disableElement(element) {
            element.className = "disabled";
            this.id_is_disabled[element.id] = true;
        },

        enableElement(element) {
            if (this.last_enabled_element !== null) {
                this.disableElement(this.last_enabled_element);
            }

            element.className = "enabled";
            this.id_is_disabled[element.id] = false;
            this.last_enabled_element = element;
        },

        disableRef(ref) {
            this.disableElement(this.ref_to_element[ref]);
        },

        enableRef(ref) {
            this.enableElement(this.ref_to_element[ref]);
        },
    }
}
</script>

<style scoped>

ul {
    display: flex;
    flex-direction: row;
    list-style-type: none;
    margin: 0;
    padding: 0;
    background-color: #007fff;
}

li {
    padding: 16px;
    font-size: 16px;
    font-weight: bold;
}

li.enabled {
    color: #000000;
    background-color: #f0f0f0;
}

li.disabled {
    color: #ffffff;
    background-color: #007fff;
}

li.disabled:hover {
    background-color: #0060f0;
}

</style>