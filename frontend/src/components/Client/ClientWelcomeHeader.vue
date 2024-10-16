<template>
    <div class="column" style="background-color: #007fff;">
        <div class="client_header">Добро пожаловать!</div>
    </div>
</template>

<script>
export default {
    name: "ClientWelcomeHeader"
}
</script>

<style scoped>

div.client_header {
    display: flex;
    flex-direction: column;
    padding: 16px;
    font-size: 32px;
    background-color: #007fff;
    color: #ffffff;
}

</style>