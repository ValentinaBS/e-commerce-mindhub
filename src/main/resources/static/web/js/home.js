const { createApp } = Vue;

const options = {
    data() {
        return {
            allProducts: [],
            products: [],
            cart: {},
            moneyFormatter: {},
        }
    },
    created() {
        this.loadProducts();
        this.loadCart();

        this.moneyFormatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        })
    },
    methods: {
        loadProducts() {
            axios.get('/api/products')
                .then(res => {
                    this.allProducts = res.data.filter(prod => prod.active).slice(0, 4);
                    this.products = res.data.filter(prod => prod.active);
                })
                .catch(err => {
                    console.error(err);
                });
        },
        loadCart() {
            axios.get('/api/cart/current')
                .then(res => {
                    this.cart = res.data;
                    console.log(this.cart);
                })
                .catch(err => {
                    console.error(err);
                });
        },
        getStockForProduct(productId) {
            if (this.cart && this.cart.cartItems && Array.isArray(this.cart.cartItems)) {
                const productInCart = this.cart.cartItems.find(prod => prod.productDTO.id === productId);
                if (productInCart) {
                    return productInCart.productDTO.stock;
                }
            }
            const product = this.products.find(prod => prod.id === productId);
            return product ? product.stock : 0;
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
                    console.log(this.cart);
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
                    console.log(this.cart);
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
                    console.log(this.cart);
                })
                .catch(err => {
                    console.error(err);
                });
        },
        removeAllCartItems() {
            axios.delete('/api/cart/remove/all', {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    this.loadCart();
                    console.log(this.cart);
                })
                .catch(err => {
                    console.error(err);
                });
        }
    }
}

const app = createApp(options);
app.mount("#app")