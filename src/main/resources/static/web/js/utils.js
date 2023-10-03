export function loadCart() {
    axios.get('/api/cart/current')
        .then(res => {
            this.cart = res.data;
            this.cart.cartItems.sort((a, b) => a.id - b.id);
        })
        .catch(err => {
            console.error(err);
        });
}

export function addToCart(productId, count) {
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
}

export function updateCartItem(cartItemId, count) {
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
}

export function removeCartItem(cartItemId) {
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
}

export function emptyCart() {
    axios.delete('/api/cart/empty')
        .then(res => {
            this.loadCart();
        })
        .catch(err => {
            console.error(err);
        });
}