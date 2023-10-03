const { createApp } = Vue

const options = {
    data() {
        return {
            currentCustomer: [],
            customerOrders: [],

            moneyFormatter: {}
        }
    },
    created() {
        axios.get('/api/customer/current')
            .then(res => {
                this.currentCustomer = res.data;
                this.customerOrders = this.currentCustomer.purchasedOrders;
                console.log(this.currentCustomer);
                console.log(this.customerOrders);
            })

        this.moneyFormatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        })
    },

    methods: {

    }
}

const app = createApp(options)
app.mount('#app')