const { createApp } = Vue;
const options = {
    data() {
        return {
            products: [],
            cart: {},
        }
    },
    created() {
        this.loadProducts();
        this.loadCart();
    },
    methods: {
        loadProducts() {
            axios.get('/api/products')
                .then(res => {
                    this.products = res.data;
                })
                .catch(err => {
                    console.error(err);
                });
        },

        loadCart() {
            axios.get('/api/cart/current')
                .then(res => {
                    this.cart = res.data;
                })
                .catch(err => {
                    console.error(err);
                });
        },

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
    }
}

const app = createApp(options);
app.mount("#app")