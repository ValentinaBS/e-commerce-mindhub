const { createApp } = Vue;

const options = {
    data() {
        return {
            allProducts: [],
            productsByCategory: [],
            productCategory: "",
            filteredProducts: [],
            searchInput: "",

            product: {
                nameInput: "",
                brandInput: "",
                priceInput: "",
                stockInput: "",
                categoryInput: "",
                shortDescriptInput: "",
                longDescriptInput: "",
                imageUrlInput: "",
            },

            errorMessage: "",
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
        addProduct() {
            if (this.nameInput == "" || this.brandInput == "" || this.priceInput == "" || this.stockInput == "" || this.categoryInput == "" || this.imageInput == "") {
                this.errorMessage = "You can't leave any empty fields.";
                return
            }
            this.errorMessage = ""
            Swal.fire({
                title: 'Are you sure you want to create a new product?',
                icon: 'info',
                buttonsStyling: false,
                customClass: {
                    confirmButton: 'btn primary-btn btn-lg mb-3 mb-md-0',
                    cancelButton: 'btn secondary-btn btn-lg me-md-5 mb-3 mt-2 my-md-2'
                },
                showCancelButton: true,
                confirmButtonText: 'Yes, create a new product',
                cancelButtonText: 'Cancel',
                reverseButtons: true
            }).then(result => {
                if (result.isConfirmed) {

                    axios.post('/api/products/create', { 
                        name: this.product.nameInput, 
                        descriptLong: this.product.longDescriptInput,
                        descriptShort: this.product.shortDescriptInput,
                        price: this.product.priceInput,
                        category: this.product.categoryInput,
                        brand: this.product.brandInput,
                        stock: this.product.stockInput,
                        active: true,
                        imageUrl: this.product.imageUrlInput
                    })
                        .then(res => {
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: 'The product has been created!',
                                showConfirmButton: true,
                                buttonsStyling: false,
                                customClass: {
                                    confirmButton: 'btn primary-btn btn-lg',
                                }
                            })
                                .then(result => {
                                    if (result.isConfirmed) {
                                        document.location.reload()
                                    }
                                })
                        })
                        .catch(error => {
                            if (error.response) {
                                console.log(error.response.data);
                                console.log(error.response.status);
                                console.log(error.response.headers);
                                this.errorMessage = error.response.data;
                            } else if (error.request) {
                                console.log(error.request);
                            } else {
                                console.log('Error', error.message);
                            }
                            console.log(error.config);
                        })
                }
            })
        },
        editProduct(productId, name, brand, price, stock, category, descriptShort, descriptLong, imageUrl) {
            axios.patch(`/api/products/update/${productId}`, { 
                name: name, 
                descriptLong: descriptLong,
                descriptShort: descriptShort,
                price: price,
                category: category,
                brand: brand,
                stock: stock,
                active: true,
                imageUrl: imageUrl
            })
            .then(res => {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'The product has been updated!',
                    showConfirmButton: true,
                    buttonsStyling: false,
                    customClass: {
                        confirmButton: 'btn primary-btn btn-lg',
                    }
                })
                .then(result => {
                    if (result.isConfirmed) {
                        document.location.reload()
                    }
                })
            })
            .catch(error => {
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
            })
        },
        removeProduct(productId) {
            Swal.fire({
                title: 'Are you sure you want to delete this product?',
                icon: 'warning',
                buttonsStyling: false,
                customClass: {
                    confirmButton: 'btn primary-btn btn-lg mb-3 mb-md-0',
                    cancelButton: 'btn secondary-btn btn-lg me-md-5 mb-3 mt-2 my-md-2'
                },
                showCancelButton: true,
                confirmButtonText: 'Yes, delete this product',
                cancelButtonText: 'Cancel',
                reverseButtons: true
            }).then(result => {
                if (result.isConfirmed) {
                    axios.patch(`/api/products/${productId}`)
                    .then(res => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'The product has been deleted!',
                            showConfirmButton: true,
                            buttonsStyling: false,
                            customClass: {
                                confirmButton: 'btn primary-btn btn-lg',
                            }
                        })
                        .then(result => {
                            if (result.isConfirmed) {
                                document.location.reload()
                            }
                        })
                    })
                    .catch(error => {
                        console.log(error.response.data);
                        console.log(error.response.status);
                        console.log(error.response.headers);
                    })
                }
            })
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
                            window.location.href = '../../index.html'
                        })
                }
            })
        },
        searchProducts(){
            this.filteredProducts = this.productsByCategory.filter(prod => {
                return prod.name.toLowerCase().includes(this.searchInput.toLowerCase()) || this.searchInput === "";
            }) 
        }
    },
    watch: {
        searchInput: "searchProducts"
    }
}

const app = createApp(options);
app.mount("#app")