const { createApp } = Vue;

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const options = {
    data() {
        return {
            currentCustomer: [],
            checkUser: false,

             cart: {
                 cartItems: [],
             },

             moneyFormatter: {},
        }
    },
    created() {
        this.loadCart();

        axios.get('/api/customer/current')
            .then(res => {
                this.currentCustomer = res.data;
                this.checkUser = true;
            })
            .catch(err => {
                console.error(err);
            });

        this.moneyFormatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        })
    },

    methods: {
        loadCart,
        addToCart,
        updateCartItem,
        removeCartItem,
        emptyCart
    },

    computed: {
        checkUserLogged() {
            if (this.checkUser) {
                return '../pages/profile.html'
            }
            return '../pages/login-signup.html'
        }
    }
}

const app = createApp(options);
app.mount("#app")