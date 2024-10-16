import { createWebHistory, createRouter } from "vue-router"
import ClientLoginPage from "@/components/Client/ClientLoginPage.vue";
import ClientMainPage from "@/components/Client/ClientMainPage.vue";
import ClientRegisterPage from "@/components/Client/ClientRegisterPage.vue";
import ClientProductsPage from "@/components/Client/ClientProductsPage.vue";
import ClientOrdersPage from "@/components/Client/ClientOrdersPage.vue";
import ClientProfilePage from "@/components/Client/ClientProfilePage.vue";
import ClientCurrentOrderPage from "@/components/Client/ClientCurrentOrderPage.vue";
import ClientChatPage from "@/components/Client/ClientCurrentChatPage.vue";
import ClientChatHistoryPage from "@/components/Client/ClientChatHistoryPage.vue";
import ClientOrderHistoryPage from "@/components/Client/ClientOrderHistoryPage.vue";
import AdminLoginPage from "@/components/Admin/AdminLoginPage.vue";
import AdminMainPage from "@/components/Admin/AdminMainPage.vue";
import AdminOrdersPage from "@/components/Admin/AdminOrdersPage.vue";
import AdminOrderHistoryPage from "@/components/Admin/AdminOrderHistoryPage.vue";
import AdminChatHistoryPage from "@/components/Admin/AdminChatHistoryPage.vue";
import FactoryLoginPage from "@/components/Factory/FactoryLoginPage.vue";
import FactoryMainPage from "@/components/Factory/FactoryMainPage.vue";
import FactoryProductsPage from "@/components/Factory/FactoryProductsPage.vue";
import FactoryProductPage from "@/components/Factory/FactoryProductPage.vue";
import FactoryMaterialsPage from "@/components/Factory/FactoryMaterialsPage.vue";
import FactoryMaterialPage from "@/components/Factory/FactoryMaterialPage.vue";

const routes = [
    {
        path: "/",
        name: "Welcome",
        component: ClientLoginPage
    },

    {
        path: "/client_login",
        name: "ClientLogin",
        component: ClientLoginPage
    },

    {
        path: "/client_register",
        name: "ClientRegister",
        component: ClientRegisterPage
    },

    {
        path: "/client_app",
        name: "ClientMain",
        component: ClientMainPage,

        children: [
            {
                path: "/products",
                name: "ClientProducts",
                component: ClientProductsPage
            },

            {
                path: "/orders",
                name: "ClientOrders",
                component: ClientOrdersPage
            },

            {
                path: "/current_order",
                name: "ClientCurrentOrder",
                component: ClientCurrentOrderPage
            },

            {
                path: "/order_history",
                name: "ClientOrderHistory",
                component: ClientOrderHistoryPage
            },

            {
                path: "/chat",
                name: "ClientChat",
                component: ClientChatPage
            },

            {
                path: "/chat_history",
                name: "ClientChatHistory",
                component: ClientChatHistoryPage
            },

            {
                path: "/profile",
                name: "ClientProfile",
                component: ClientProfilePage
            }
        ],
    },

    {
        path: "/admin_login",
        name: "AdminLogin",
        component: AdminLoginPage
    },

    {
        path: "/admin_app",
        name: "AdminMain",
        component: AdminMainPage,

        children: [
            {
                path: "/orders",
                name: "AdminOrders",
                component: AdminOrdersPage
            },

            {
                path: "/order_history",
                name: "AdminOrderHistory",
                component: AdminOrderHistoryPage
            },

            {
                path: "/chat_history",
                name: "AdminChatHistory",
                component: AdminChatHistoryPage
            }
        ],
    },

    {
        path: "/factory_login",
        name: "FactoryLogin",
        component: FactoryLoginPage
    },

    {
        path: "/factory_app",
        name: "FactoryMain",
        component: FactoryMainPage,

        children: [
            {
                path: "/products",
                name: "FactoryProducts",
                component: FactoryProductsPage
            },

            {
                path: "/product",
                name: "FactoryProduct",
                component: FactoryProductPage
            },

            {
                path: "/materials",
                name: "FactoryMaterials",
                component: FactoryMaterialsPage
            },

            {
                path: "/material",
                name: "FactoryMaterial",
                component: FactoryMaterialPage
            }
        ],
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;