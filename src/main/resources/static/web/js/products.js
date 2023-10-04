const { createApp } = Vue;

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

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

            moneyFormatter: {},
            currentCustomer: [],
            checkUser: false,

            cart: {
                cartItems: [],
            },
        }
    },
    created() {
        this.loadCart();

        let urlParams = new URLSearchParams(location.search);
        this.productCategory = urlParams.get("category");
        console.log(this.productCategory);

        axios.get('/api/products')
            .then(res => {
                this.allProducts = res.data.filter(prod => prod.active);
                console.log(this.allProducts);
                
                if(this.productCategory == "all") {
                    this.productsByCategory = this.allProducts;
                } else {
                    this.productsByCategory = this.allProducts.filter(prod => prod.category == this.productCategory);
                }

                this.filteredProducts = this.productsByCategory;
                this.uniqueBrands = Array.from(new Set(this.productsByCategory.map(prod => prod.brand)))
            })
            .catch(error => {
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
            })

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
            if(this.productCategory == "all") {
                this.filteredProducts = this.allProducts;
            } else {
                this.filteredProducts = this.allProducts.filter(prod => prod.category == this.productCategory);
            }
            this.searchInput = "";
            this.priceInput = "";
            this.brandsChecked = [];
            this.stockChecked = false;
        },

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
    watch: {
        sortBy: "sortProducts"
    },    
    computed: {
        checkUserLogged() {
            if(this.checkUser) {
                return '../pages/profile.html'
            }
            return '../pages/login-signup.html'
        },
        checkUserLoggedCheckout() {
            if(this.checkUser) {
                return 'checkout.html'
            }
            return 'login-signup.html'
        },
    }
}

const app = createApp(options);
app.mount("#app")