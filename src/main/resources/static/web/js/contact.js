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
            responseMessage: "",
            loadingText: "Send"
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
        sendEmail() {
            this.loadingText = "Loading..."
            emailjs.sendForm('service_ahuaxmq', 'template_5o5busd', this.$refs.form, '8n-mlNrupz3rdpU0s')
                .then((result) => {
                    this.responseMessage = "Your message has been sent successfully. We'll get back to you shortly!"
                    this.loadingText = "Send"
                }, (error) => {
                    this.responseMessage = "Your message couldn't be sent. Try again!"
                });
        },
        logOut() {
            Swal.fire({
                title: 'Are you sure you want to log out?',
                icon: 'warning',
                buttonsStyling: false,
                customClass: {
                    confirmButton: 'btn primary-btn btn-lg my-2',
                    cancelButton: 'btn secondary-btn btn-lg me-5 my-2'
                },
                showCancelButton: true,
                confirmButtonText: 'Log out',
                cancelButtonText: 'Cancel',
                reverseButtons: true
            }).then(result => {
                if (result.isConfirmed) {
                    axios.post('/api/logout')
                        .then(() => {
                            window.location.href = '../index.html'
                            this.checkUser = false;
                        })
                }
            })
        }
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