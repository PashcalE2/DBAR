
const SERVER_URL = "http://localhost:22600/isbd-final"
const CLIENT_PATH = SERVER_URL + "/client"
const ADMIN_PATH = SERVER_URL + "/admin"
const FACTORY_PATH = SERVER_URL + "/factory"

export const MY_APIS = {
    CLIENT: {
        PROFILE: {
            GET_REGISTERED_ORGANIZATIONS: {
                url: CLIENT_PATH + "/profile/get_all_registered_organizations",
                method: "get"
            },

            GET_NOT_REGISTERED_ORGANIZATIONS: {
                url: CLIENT_PATH + "/profile/get_all_not_registered_organizations",
                method: "get"
            },

            CHECK_RIGHTS: {
                url: CLIENT_PATH + "/profile/check_rights",
                method: "post"
            },

            LOGIN: {
                url: CLIENT_PATH + "/profile/login",
                method: "post"
            },

            REGISTER: {
                url: CLIENT_PATH + "/profile/register",
                method: "post"
            },

            GET_PROFILE: {
                url: CLIENT_PATH + "/profile/get",
                method: "get"
            },

            SET_PROFILE: {
                url: CLIENT_PATH + "/profile/set",
                method: "post"
            }
        },

        PRODUCT: {
            GET_ALL_SHORT: {
                url: CLIENT_PATH + "/product/get_all_short",
                method: "get"
            },

            GET: {
                url: CLIENT_PATH + "/product/get",
                method: "get"
            },

            ADD_TO_ORDER: {
                url: CLIENT_PATH + "/product/add_to_order",
                method: "post"
            },

            REMOVE_FROM_ORDER: {
                url: CLIENT_PATH + "/product/remove_from_order",
                method: "post"
            }
        },

        ORDER: {
            GET_ALL_INFO: {
                url: CLIENT_PATH + "/order/get_all_info",
                method: "get"
            },

            GET: {
                url: CLIENT_PATH + "/order/get",
                method: "get"
            },

            GET_CURRENT: {
                url: CLIENT_PATH + "/order/get_current",
                method: "get"
            },

            GET_PRODUCTS: {
                url: CLIENT_PATH + "/order/get_products",
                method: "get"
            },

            SET_PRODUCT_COUNT: {
                url: CLIENT_PATH + "/order/set_product_count",
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
                url: CLIENT_PATH + "/chat/get_admin",
                method: "get"
            },

            GET_MESSAGES: {
                url: CLIENT_PATH + "/chat/get_messages",
                method: "get"
            },

            POST_MESSAGE: {
                url: CLIENT_PATH + "/chat/post_message",
                method: "post"
            }
        }
    },

    ADMIN: {
        PROFILE: {
            CHECK_RIGHTS: {
                url: ADMIN_PATH + "/profile/check_rights",
                method: "post"
            },

            LOGIN: {
                url: ADMIN_PATH + "/profile/login",
                method: "post"
            }
        },

        PRODUCT: {
            GET: {
                url: ADMIN_PATH + "/product/get",
                method: "get"
            }
        },

        ORDER: {
            GET_ALL_INFO: {
                url: ADMIN_PATH + "/order/get_all_info",
                method: "get"
            },

            GET: {
                url: ADMIN_PATH + "/order/get",
                method: "get"
            },

            GET_PRODUCTS: {
                url: ADMIN_PATH + "/order/get_products",
                method: "get"
            },

            ASK_FOR_ASSEMBLING: {
                url: ADMIN_PATH + "/order/ask_for_assembling",
                method: "post"
            }
        },

        CHAT: {
            GET_CLIENT: {
                url: ADMIN_PATH + "/chat/get_client",
                method: "get"
            },

            GET_MESSAGES: {
                url: ADMIN_PATH + "/chat/get_messages",
                method: "get"
            },

            POST_MESSAGE: {
                url: ADMIN_PATH + "/chat/post_message",
                method: "post"
            }
        }
    },

    FACTORY: {
        PROFILE: {
            CHECK_RIGHTS: {
                url: FACTORY_PATH + "/profile/check_rights",
                method: "post"
            },

            LOGIN: {
                url: FACTORY_PATH + "/profile/login",
                method: "post"
            }
        },

        PRODUCT: {
            GET_ALL_SHORT: {
                url: FACTORY_PATH + "/product/get_all_short",
                method: "get"
            },

            GET: {
                url: FACTORY_PATH + "/product/get",
                method: "get"
            },

            SET: {
                url: FACTORY_PATH + "/product/set",
                method: "post"
            }
        },

        MATERIAL: {
            GET_ALL_SHORT: {
                url: FACTORY_PATH + "/material/get_all_short",
                method: "get"
            },

            GET: {
                url: FACTORY_PATH + "/material/get",
                method: "get"
            },

            SET: {
                url: FACTORY_PATH + "/material/set",
                method: "post"
            }
        }
    }
}
