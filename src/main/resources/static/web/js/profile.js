const { createApp } = Vue

const options = {
    data() {
        return {
            currentCustomer: [],
            customerOrders: [],
            selectedOrder: {},
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
    methods: {
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
        }
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