<template>
    <div ref="alignment">
        <div ref="message">
            <div ref="content">
                {{ content }}
            </div>
            <div class="datetime">
                {{ posted }}
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "ChatMessage",

    props: ["content", "posted", "from_current_user"],

    mounted() {
        if (this.from_current_user) {
            this.makeFromUser();
        }
        else {
            this.makeFromOther();
        }
    },

    methods: {
        makeFromUser() {
            this.$refs.alignment.className = "align_from_me";
            this.$refs.message.className = "from_me";
        },

        makeFromOther() {
            this.$refs.alignment.className = "align_from_other";
            this.$refs.message.className = "from_other";
        },
    }
}
</script>

<style scoped>

div.align_from_me {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    padding: 8px;
}

div.align_from_other {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 8px;
}

div.from_me, div.from_other {
    max-width: 500px;
    padding: 4px;

    border: #80c0ff 4px solid;
    border-radius: 12px;
}

div.from_other, div.from_other > div {
    text-align: left;
    font-size: 20px;
    background-color: #f0f0f0;
    color: #000000;
}

div.from_me, div.from_me > div {
    text-align: left;
    font-size: 20px;
    background-color: #80c0ff;
    color: #000000;
}

div.datetime {
    margin-top: 16px;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}

</style>