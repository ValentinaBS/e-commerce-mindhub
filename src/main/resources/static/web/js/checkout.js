const { createApp } = Vue

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const app = createApp({
    data() {
        return {
            paymentMethodId: 'pm_card_visa',
            processing: false,
            processingMessage: "Place Order",
            clientSecret: 'pi_3NxDZUDxOCV4zZt42jEtY98j_secret_JuMhECF70tB8EejcI2Rruciq',
            amount : 0,
            cart: {
                cartItems: [],
            },
            moneyFormatter: {}
        };
    },
    created() {
        this.loadCart();

        axios.get('/api/cart/current')
        .then(res => {
            this.cart = res.data;
            this.cart.cartItems.sort((a, b) => a.id - b.id);
            console.log(this.cart.cartItems)
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
        async submitPayment() {

            this.processing = true
            this.processingMessage = "Loading...";

            try {
                const response = await axios.post('http://localhost:8080/api/process-payment', {
                    paymentMethodId: this.paymentMethodId,
                    amount : this.cartTotal,
                });

                if (response.status === 200) {
                    axios.post('/api/order/create')
                        .then(response => {              
                
                        })
                        .catch((error) => console.log(error));
                 

                    Swal.fire({
                        icon: 'success',
                        title: 'Thanks for your purchase!',
                        confirmButtonText: 'OK',
                        buttonsStyling: false,
                        customClass: {
                        confirmButton: 'btn primary-btn btn-lg mb-3 mb-md-0',
                    
                },
                    }).then((result) => {
                        if (result.isConfirmed) {
                            emptyCart();
                            window.location.href = "http://localhost:8080/web/pages/profile.html";
                        }
                    });
                    
                } else {
                    alert('Payment processing failed.');
                    console.error(response.data);
                }
            } catch (error) {
                alert('Payment processing failed.');
                console.error(error);
            } finally {
                this.processing = false;
                this.processingMessage = "Place Order";
            }
        },
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
                            window.location.href = '../index.html'
                            this.checkUser = false;
                        })
                }
            })
        }
    },
    computed:{
        cartTotal() {
            
            return this.cart.cartItems.reduce((total, item) => {
              return total + (item.productDTO.price * item.count);
            }, 0);
    }
},
    
});

app.mount("#app");