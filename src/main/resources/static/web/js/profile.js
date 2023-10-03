const { createApp } = Vue

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const options = {
    data() {
        return {
            currentCustomer: [],
            customerOrders: [],
            selectedOrder: {},
            filteredOrders: [],
            searchInput: "",

            moneyFormatter: {},

            cart: {
                cartItems: [],
            },
              
            checkUser: false
        }
    },

    created() {
        this.loadCart();

        axios.get('/api/customer/current')
            .then(res => {
                this.currentCustomer = res.data;
                this.customerOrders = this.currentCustomer.purchasedOrders.sort((a, b) => new Date(a.orderDate).getTime() - new Date(b.orderDate).getTime());
                this.checkUser = true;
            })

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
  
        downloadOrder(orderId) {
            this.selectedOrder = this.customerOrders.find(order => order.id == orderId)
            console.log(this.selectedOrder);
            const url = `/api/current/orders/generate-pdf?id=${this.selectedOrder.id}`;
            axios.get(url, {responseType: "blob"})
                .then(res => {
                    // Creates a Blob object with the PDFs content and creates a URL
                    let blob = new Blob([res.data], { type: "application/pdf" });
                    let url = window.URL.createObjectURL(blob);

                    // Creates a link to download the PDF
                    let a = document.createElement("a");
                    a.href = url;
                    a.download = `Order_#${this.selectedOrder.id}_Purity.pdf`;

                    // Simulates a click to start the download
                    document.body.appendChild(a);
                    a.click();

                    // Cleans the URL object
                    window.URL.revokeObjectURL(url);
                })
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
    computed: {
        filterOrders(){
            this.filteredOrders = this.customerOrders.filter(order => {
                return order.id.toString() == this.searchInput || this.searchInput === "";
            }) 
        },
        checkUserLoggedCheckout() {
            if(this.checkUser) {
                return 'checkout.html'
            }
            return 'login-signup.html'
        },
    }
}

const app = createApp(options)
app.mount('#app')