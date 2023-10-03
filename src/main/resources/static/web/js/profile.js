const { createApp } = Vue

const options = {
    data() {
        return {
            currentCustomer: [],
            customerOrders: [],
            filteredOrders: [],
            searchInput: "",
            moneyFormatter: {}
        }
    },
    created() {
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