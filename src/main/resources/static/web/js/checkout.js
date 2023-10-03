const app = Vue.createApp({
    data() {
        return {
            paymentMethodId: '',
            processing: false,
            amount: 1000,
            clientSecret: '', // Store the client_secret here
        };
    },
    methods: {
        async fetchClientSecret() {
            try {
                const response = await axios.post('http://localhost:8080/api/process-payment'); // Replace with your server endpoint
                this.clientSecret = response.data;
                console.log('Response:', response.data)
            } catch (error) {
                console.error(error);
            }
        },
        async submitPayment() {
            if (!this.paymentMethodId) {
                alert('Please select a payment method.');
                return;
            }

            this.processing = true;

            try {
                const stripe = await Stripe('pk_test_51NwuHdDxOCV4zZt44VYaKELS3gbPPPUSvFZws6LWik79iadJyMbC9uOoyp2yAQxoBQ9k0RYmRLoBncONFSuhubw700kEJZZfvg');
                const { error } = await stripe.confirmCardPayment(this.clientSecret, {
                    payment_method: this.paymentMethodId,
                    amount: this.amount,
                    currency: 'usd',
                });

                if (error) {
                    alert('Payment processing failed.');
                    console.error(error);
                } else {
                    alert('Payment processed successfully.');
                }
            } catch (error) {
                alert('Payment processing failed.');
                console.error(error);
            } finally {
                this.processing = false;
            }
        },
    },
    mounted() {
        // Fetch the client_secret when the component is mounted
        this.fetchClientSecret();
    },
});

app.mount('#app');