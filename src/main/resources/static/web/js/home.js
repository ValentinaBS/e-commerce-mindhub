const { createApp } = Vue;

const options = {
    data() {
        return {
            currentCustomer: [],
            checkUser: false,
            allProducts: [],
            cart: {},
            moneyFormatter: {},
        }
    },
    created() {
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

        axios.get('/api/cart/current')
            .then(res => {
                this.cart = res.data;
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
        addToCart(productId, count) {
            axios.post('/api/cart', {
                productID: productId,
                count: count
            }, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    this.loadCart();
                })
                .catch(err => {
                    console.error(err);
                });
        },

        updateCartItem(cartItemId, count) {
            if (count <= 0) {
                this.removeCartItem(cartItemId);
                return;
            }
            axios.post('/api/cart/update', {
                cartItemID: cartItemId,
                count: count
            }, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    this.loadCart();
                })
                .catch(err => {
                    console.error(err);
                });
        },

        removeCartItem(cartItemId) {
            axios.delete('/api/cart/remove', {
                params: {
                    cartItemID: cartItemId
                }
            }, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    this.loadCart();
                })
                .catch(err => {
                    console.error(err);
                });
        },
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