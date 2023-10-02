const { createApp } = Vue;

const options = {
    data() {
        return {
            allProducts: [],
            productsByCategory: [],
            productCategory: "",
            filteredProducts: [],

            searchInput: "",
            priceInput: "",
            brandsChecked: [],
            stockChecked: false,

            sortBy: "",

            cart: {},

            moneyFormatter: {},
        }
    },
    created() {

        let urlParams = new URLSearchParams(location.search);
        this.productCategory = urlParams.get("category");

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
                    this.allProducts = res.data.filter(prod => prod.active);

                    if(this.productCategory == "all") {
                        this.productsByCategory = this.allProducts;
                    } else {
                        this.productsByCategory = this.allProducts.filter(prod => prod.category == this.productCategory);
                    }
    
                    this.filteredProducts = this.productsByCategory;
                    this.uniqueBrands = Array.from(new Set(this.productsByCategory.map(prod => prod.brand)))
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
                    return productInCart.productDTO.stock - productInCart.count;
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
        filter(){
            this.filteredProducts = this.productsByCategory.filter(prod => {

                const nameMatch = prod.name.toLowerCase().includes(this.searchInput.toLowerCase()) || this.searchInput === "";
                const brandMatch = this.brandsChecked.includes(prod.brand) || this.brandsChecked.length === 0;

                let priceMatch = true;
                if (this.priceInput !== "") {
                    if(this.priceInput !== "+50") {
                        // Divides value in inferior and superior
                        const [minPrice, maxPrice] = this.priceInput.split('-').map(Number);
                        // Verifies if the price matches the range
                        priceMatch = prod.price >= minPrice && prod.price <= maxPrice;
                    } else {
                        priceMatch = prod.price > 50;
                    }
                }
                
                const stockMatch = this.stockChecked ? prod.stock > 0 : true;

                return nameMatch && brandMatch && priceMatch && stockMatch;
            }) 
        },
        sortProducts() {
            if (this.sortBy === "lowest-price") {
                this.filteredProducts.sort((a, b) => a.price - b.price);
            } else if (this.sortBy === "highest-price") {
                this.filteredProducts.sort((a, b) => b.price - a.price);
            } else if (this.sortBy === "A-Z") {
                this.filteredProducts.sort((a, b) => a.name.localeCompare(b.name));
            } else if (this.sortBy === "Z-A") {
                this.filteredProducts.sort((a, b) => b.name.localeCompare(a.name));
            } else if (this.sortBy === "highest-stock") {
                this.filteredProducts.sort((a, b) => b.stock - a.stock);
            }
        },
        clearFilters() {
            this.filteredProducts = this.allProducts.filter(prod => prod.category == this.productCategory);
            this.searchInput = "";
            this.priceInput = "";
            this.brandsChecked = [];
            this.stockChecked = false;
        },
    },
    watch: {
        sortBy: "sortProducts"
    },    
}

const app = createApp(options);
app.mount("#app")