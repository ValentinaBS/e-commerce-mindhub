const { createApp } = Vue

const app = createApp({
    data() {
        return {
            paymentMethodId: 'pm_card_visa',
            processing: false,
            clientSecret: 'pi_3NxDZUDxOCV4zZt42jEtY98j_secret_JuMhECF70tB8EejcI2Rruciq',
            amount : 50,
        };
    },
    methods: {
        async submitPayment() {

            this.processing = true;

            try {
                const response = await axios.post('http://localhost:8080/api/process-payment', {
                    paymentMethodId: this.paymentMethodId,
                    amount : this.amount
                });

                if (response.status === 200) {

                    Swal.fire({
                        icon: 'success',
                        title: 'Thanks for your purchase!',
                        confirmButtonText: 'OK',
                        buttonsStyling: false,
                        customClass: {
                        confirmButton: 'btn primary-btn btn-lg mb-3 mb-md-0',
                    
                },
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = "http://localhost:8080/web/pages/profile.html";
                        }
                    });

                    axios.post('/api/order/create')
                        .then(response => {
                            
                
                
                
                        })
                        .catch((error) => console.log(error));
                    
                    // Redirect to a success page or perform other actions as needed.
                } else {
                    alert('Payment processing failed.');
                    console.error(response.data);
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