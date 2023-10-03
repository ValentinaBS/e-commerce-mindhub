const { createApp } = Vue;

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const options = {
    data() {
        return {
            currentCustomer: [],
            checkUser: false,
            allProducts: [],

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

        axios.get('/api/products')
            .then(res => {
                this.allProducts = res.data.filter(prod => prod.active).slice(0, 4);
            })
            .catch(error => {
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
            })

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
            if(this.checkUser) {
                return 'pages/profile.html'
            }
            return 'pages/login-signup.html'
        }
    }
}

const app = createApp(options);
app.mount("#app")