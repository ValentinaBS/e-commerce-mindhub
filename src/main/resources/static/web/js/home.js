const { createApp } = Vue;

const options = {
    data() {
        return {
            allProducts: []
        }
    },
    created() {
        axios.get('/api/products')
            .then(res => {
                this.allProducts = res.data;
                console.log(this.allProducts)
            })
            .catch(error => {
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
            })
    },
    methods: {
    }
}

const app = createApp(options);
app.mount("#app")