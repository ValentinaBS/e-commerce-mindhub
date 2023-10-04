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
            if (this.currentCustomer) {
                this.checkUser = true;
            }
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
        emptyCart,

        logOut() {
            Swal.fire({
                title: 'Are you sure you want to log out?',
                icon: 'warning',
                buttonsStyling: false,
                customClass: {
                    confirmButton: 'btn primary-btn btn-lg mb-3 mb-md-0',
                    cancelButton: 'btn secondary-btn btn-lg me-md-5 mb-3 mt-2 my-md-2'
                },
                showCancelButton: true,
                confirmButtonText: 'Log out',
                cancelButtonText: 'Cancel',
                reverseButtons: true
            }).then(result => {
                if (result.isConfirmed) {
                    axios.post('/api/logout')
                        .then(() => {
                            window.location.href = '/web/index.html'
                            this.checkUser = false;
                        })
                }
            })
        }

    },
    computed: {
        checkUserLogged() {
            if(this.checkUser) {
                return 'pages/profile.html'
            }
            return 'pages/login-signup.html'
        },
        checkUserLoggedCheckout() {
            if(this.checkUser) {
                return 'pages/checkout.html'
            }
            return 'pages/login-signup.html'
        },
    }
}

const app = createApp(options);
app.mount("#app")