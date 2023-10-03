
const { createApp } = Vue;



const app = createApp({
    data() {
        return {
            paymentMethodId: '', // Store the payment method ID here
            processing: false,
            amount: 1000, // Set the initial amount (you can change this dynamically)
        };
    },
    methods: {
        async submitPayment() {
            if (!this.paymentMethodId) {
                alert('Please select a payment method.');
                return;
            }

            this.processing = true;

            try {
                const stripe = await Stripe('pk_test_51NwuHdDxOCV4zZt44VYaKELS3gbPPPUSvFZws6LWik79iadJyMbC9uOoyp2yAQxoBQ9k0RYmRLoBncONFSuhubw700kEJZZfvg'); // Replace with your Stripe publishable key
                const { error } = await stripe.confirmCardPayment('your_client_secret', {
                    payment_method: this.paymentMethodId,
                    amount: this.amount,
                    currency: 'usd',
                });

                if (error) {
                    alert('Payment processing failed.');
                    console.error(error);
                } else {
                    alert('Payment processed successfully.');
                    // Redirect to a success page or perform other actions as needed.
                }
            } catch (error) {
                alert('Payment processing failed.');
                console.error(error);
            } finally {
                this.processing = false;
            }
        },
    },
});

app.mount("#app");
