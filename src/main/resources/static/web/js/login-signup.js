let { createApp } = Vue

const options = {
  data() {
    return {
      showForm: false,
      emailInput: "",
      passwordInput: "",
      nameInput: "",
      addressInput: "",

      errorMessage: ""
    }
  },

  created() {



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
                  window.location.href = "/web/pages/admin/product-manager.html"
              } else {
                  window.location.href = "/web/pages/products.html?category=all"
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
    axios.post("/api/register", {name: this.nameInput, email: this.emailInput, password: this.passwordInput, address: this.addressInput})
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
}

  }
}



const app = createApp(options)

app.mount('#app')