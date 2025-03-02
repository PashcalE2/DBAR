
export const ENUMS = {
    ORDER_STATUS: {
        BEING_FORMED: "формируется",
        AWAITS_PAYMENT: "ожидает оплаты",
        IN_PROGRESS: "выполняется",
        DONE: "выполнен",
        CANCELED: "отклонен"
    },

    MESSAGE_SENDER: {
        ADMIN: "консультант",
        CLIENT: "клиент"
    },

    PRODUCT_STATUS: {
        AWAITS_PRODUCTION: "ожидает производства",
        AWAITS_ASSEMBLING: "ожидает сборки",
        AWAITS_RETURNING: "ожидает возвращения",
        RETURNED: "возвращена",
        ASSEMBLED: "собрана под заказ"
    }
}
