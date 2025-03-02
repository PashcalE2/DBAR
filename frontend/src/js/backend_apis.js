
const AUTH_SERVICE_URL = "http://localhost:22500";
const MAIN_SERVICE_URL = "http://localhost:22600";
const CLIENT_PATH = MAIN_SERVICE_URL + "/client";
const ADMIN_PATH = MAIN_SERVICE_URL + "/admin";
const FACTORY_PATH = MAIN_SERVICE_URL + "/factory";

export const BACKEND_API = {
    AUTH_SERVICE: {
        AUTH: {
            LOGIN: {
                CLIENT: {
                    url: AUTH_SERVICE_URL + "/auth/login",
                    method: "post"
                },

                ADMIN: {
                    url: AUTH_SERVICE_URL + "/auth/login/admin",
                    method: "post"
                },

                FACTORY: {
                    url: AUTH_SERVICE_URL + "/auth/login/factory",
                    method: "post"
                }
            },

            ACCESS_TOKEN: {
                NEW: {
                    url: AUTH_SERVICE_URL + "/auth/refresh/login",
                    method: "post"
                }
            }
        },

        USER: {
            PROFILE: {
                GET: {
                    url: AUTH_SERVICE_URL + "/user/profile",
                    method: "get"
                },

                UPDATE: {
                    url: AUTH_SERVICE_URL + "/user/profile",
                    method: "put"
                }
            }
        }
    },

    MAIN_SERVICE: {
        CLIENT: {
            PROFILE: {
                REGISTER: {
                    url: CLIENT_PATH + "/profile/register",
                    method: "post"
                },

                GET_PROFILE: {
                    url: AUTH_SERVICE_URL + "/user/profile",
                    method: "get"
                },

                SET_PROFILE: {
                    url: AUTH_SERVICE_URL + "/user/profile",
                    method: "put"
                }
            },

            PRODUCT: {
                GET_ALL_SHORT: {
                    url: CLIENT_PATH + "/product/all-short",
                    method: "get"
                },

                GET: {
                    url: CLIENT_PATH + "/product",
                    method: "get"
                },

                ADD_TO_ORDER: {
                    url: CLIENT_PATH + "/product/add-to-order",
                    method: "post"
                },

                REMOVE_FROM_ORDER: {
                    url: CLIENT_PATH + "/product/remove-from-order",
                    method: "post"
                }
            },

            ORDER: {
                GET_ALL_INFO: {
                    url: CLIENT_PATH + "/order/all-info",
                    method: "get"
                },

                GET: {
                    url: CLIENT_PATH + "/order",
                    method: "get"
                },

                GET_CURRENT: {
                    url: CLIENT_PATH + "/order/current",
                    method: "get"
                },

                GET_PRODUCTS: {
                    url: CLIENT_PATH + "/order/products",
                    method: "get"
                },

                SET_PRODUCT_COUNT: {
                    url: CLIENT_PATH + "/order/product-count",
                    method: "post"
                },

                ACCEPT: {
                    url: CLIENT_PATH + "/order/accept",
                    method: "post"
                },

                PAY: {
                    url: CLIENT_PATH + "/order/pay",
                    method: "post"
                },

                CANCEL: {
                    url: CLIENT_PATH + "/order/cancel",
                    method: "post"
                }
            },

            CHAT: {
                GET_ADMIN: {
                    url: CLIENT_PATH + "/chat/admin",
                    method: "get"
                },

                GET_MESSAGES: {
                    url: CLIENT_PATH + "/chat/messages",
                    method: "get"
                },

                POST_MESSAGE: {
                    url: CLIENT_PATH + "/chat/message",
                    method: "post"
                }
            }
        },

        ADMIN: {
            PRODUCT: {
                GET: {
                    url: ADMIN_PATH + "/product",
                    method: "get"
                }
            },

            ORDER: {
                GET_ALL_INFO: {
                    url: ADMIN_PATH + "/order/all-info",
                    method: "get"
                },

                GET: {
                    url: ADMIN_PATH + "/order",
                    method: "get"
                },

                GET_PRODUCTS: {
                    url: ADMIN_PATH + "/order/products",
                    method: "get"
                },

                ASK_FOR_ASSEMBLING: {
                    url: ADMIN_PATH + "/order/ask-for-assembling",
                    method: "post"
                }
            },

            CHAT: {
                GET_CLIENT: {
                    url: ADMIN_PATH + "/chat/client",
                    method: "get"
                },

                GET_MESSAGES: {
                    url: ADMIN_PATH + "/chat/messages",
                    method: "get"
                },

                POST_MESSAGE: {
                    url: ADMIN_PATH + "/chat/message",
                    method: "post"
                }
            }
        },

        FACTORY: {
            PRODUCT: {
                GET_ALL_SHORT: {
                    url: FACTORY_PATH + "/product/all-short",
                    method: "get"
                },

                GET: {
                    url: FACTORY_PATH + "/product",
                    method: "get"
                },

                SET: {
                    url: FACTORY_PATH + "/product",
                    method: "post"
                }
            },

            MATERIAL: {
                GET_ALL_SHORT: {
                    url: FACTORY_PATH + "/material/all-short",
                    method: "get"
                },

                GET: {
                    url: FACTORY_PATH + "/material",
                    method: "get"
                },

                SET: {
                    url: FACTORY_PATH + "/material",
                    method: "post"
                }
            }
        }
    }
}
