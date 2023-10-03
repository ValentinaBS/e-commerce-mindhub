const { createApp } = Vue;

const options = {
    data() {
        return {
            currentCustomer: [],
            checkUser: false,
        }
    },
    created() {
        axios.get('/api/customer/current')
        .then(res => {
            this.currentCustomer = res.data;
            this.checkUser = true;
        })
        .catch(err => {
            console.error(err);
        });
    },   
    computed: {
        checkUserLogged() {
            if(this.checkUser) {
                return '../pages/profile.html'
            }
            return '../pages/login-signup.html'
        }
    }
}

const app = createApp(options);
app.mount("#app")