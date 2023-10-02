const { createApp } = Vue;

const options = {
    data() {
        return {
            allProducts: [],
            moneyFormatter: {},
        }
    },
    created() {
        axios.get('/api/products')
            .then(res => {
                this.allProducts = res.data.slice(0, 4);
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
    }
}

const app = createApp(options);
app.mount("#app")