const { createApp } = Vue;

const options = {
    data() {
        return {
            allProducts: [],
            cart: [],
            localStorageQty: 0,
            cartQty: 0,
            oldItems: [],

            moneyFormatter: {},
        }
    },
    created() {
        axios.get('/api/products')
            .then(res => {
                this.allProducts = res.data.slice(0, 4);

                document.body.setAttribute("data-quantity", this.cartQty);
                this.cart = JSON.parse(localStorage.getItem("cartProducts"));

                this.localStorageQty = JSON.parse(localStorage.getItem("cartProducts")).length;
                this.oldItems = JSON.parse(localStorage.getItem("cartProducts")) || [];
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
    computed: {
        addQty() {
            for (let product of this.cart) {
                product.quantity = 1;
            }
        }
    },
    methods: {
        emptyCart() {
            localStorage.removeItem("cartProducts");
            this.cart = [];
        },
        addCart(product) {
            this.cartQty++;
            this.cart.push(product);
            this.oldItems = this.cart;
            this.localStorageQty = this.oldItems.length;
            localStorage.setItem(
                "cartProducts",
                JSON.stringify(this.oldItems)
            );
        },
    }
}

const app = createApp(options);
app.mount("#app")