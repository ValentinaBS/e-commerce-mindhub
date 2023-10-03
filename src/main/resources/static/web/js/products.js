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

            moneyFormatter: {},
        }
    },
    created() {
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

        this.moneyFormatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        })
    },
    methods: {
        filter(){
            this.filteredProducts = this.productsByCategory.filter(prod => {
                console.log(this.searchInput);
                console.log(this.priceInput);
                console.log(this.brandsChecked);
                console.log(this.stockChecked);

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