const { createApp } = Vue

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const options = {
    data() {
        return {
            currentCustomer: [],
            customerOrders: [],
            filteredOrders: [],
            searchInput: "",
            moneyFormatter: {}

            cart: {
                cartItems: [],
            },
        }
    },

    created() {
        this.loadCart();

        axios.get('/api/customer/current')
            .then(res => {
                this.currentCustomer = res.data;
                this.customerOrders = this.currentCustomer.purchasedOrders.sort((a, b) => new Date(a.orderDate).getTime() - new Date(b.orderDate).getTime());
                console.log(this.currentCustomer);
                console.log(this.customerOrders);
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
        emptyCart
    },

    computed: {
        filterOrders(){
            this.filteredOrders = this.customerOrders.filter(order => {
                return order.id.toString() == this.searchInput || this.searchInput === "";
            }) 
        }
    }
}

const app = createApp(options)
app.mount('#app')