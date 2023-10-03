const { createApp } = Vue;

const options = {
    data() {
        return {
            currentCustomer: [],
            checkUser: false,
            responseMessage: ""
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
    methods: {
        sendEmail() {
            emailjs.sendForm('service_ahuaxmq', 'template_5o5busd', this.$refs.form, '8n-mlNrupz3rdpU0s')
                .then((result) => {
                    this.responseMessage = "Your message has been sent successfully. We'll get back to you shortly!"
                }, (error) => {
                    this.responseMessage = "Your message couldn't be sent. Try again!"
                });
        }
    },
    computed: {
        checkUserLogged() {
            if (this.checkUser) {
                return '../pages/profile.html'
            }
            return '../pages/login-signup.html'
        }
    }
}

const app = createApp(options);
app.mount("#app")