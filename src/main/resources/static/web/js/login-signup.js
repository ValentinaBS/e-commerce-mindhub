const { createApp } = Vue

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const options = {
    data() {
        return {
            showForm: false,
            emailInput: "",
            passwordInput: "",
            nameInput: "",
            addressInput: "",
          
      currentCustomer: [],
      checkUser: false,
      errorMessage: "",

      cart: {
          cartItems: [],
      },

      moneyFormatter: {},
    }
  },
  created() {
    this.loadCart();

    axios.get('/api/customer/current')
      .then(res => {
          this.currentCustomer = res.data;
      })
      .catch(err => {
          console.error(err);
      })

    this.moneyFormatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    })
},
    methods: {
        showForms() {
            this.showForm = !this.showForm;
            console.log(this.showForm);
        },
        submitLogin() {
            axios.post("/api/login", `email=${this.emailInput}&password=${this.passwordInput}`)
                .then(res => {
                    if (this.emailInput.startsWith("admin")) {
                        window.location.href = "../pages/admin/product-manager.html?category=lips"
                    } else {
                        window.location.href = "../pages/profile.html"
                    }
                })
                .catch(error => {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                    this.errorMessage = "Incorrect email or password.";
                })
        },
        submitSignUp() {
            axios.post("/api/customer", { name: this.nameInput, email: this.emailInput, password: this.passwordInput, address: this.addressInput })
                .then(() => {
                    this.submitLogin();
                })
                .catch(error => {
                    if (error.response) {
                        console.log(error.response.data);
                        console.log(error.response.status);
                        console.log(error.response.headers);
                        this.showErrorMessage = true;
                        this.errorMessage = error.response.data;
                    } else if (error.request) {
                        console.log(error.request);
                    } else {
                        console.log('Error', error.message);
                    }
                    console.log(error.config);
                })
        },
        loadCart,
        addToCart,
        updateCartItem,
        removeCartItem,
        emptyCart,
        logOut() {
            Swal.fire({
                title: 'Are you sure you want to log out?',
                icon: 'warning',
                buttonsStyling: false,
                customClass: {
                    confirmButton: 'btn primary-btn btn-lg my-2',
                    cancelButton: 'btn secondary-btn btn-lg me-5 my-2'
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
            return '#'
        },
        checkUserLoggedCheckout() {
            if(this.checkUser) {
                return 'checkout.html'
            }
            return '#'
        },
    }
}




const app = createApp(options)

app.mount('#app')