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