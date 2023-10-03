const { createApp } = Vue;

import { loadCart, addToCart, updateCartItem, removeCartItem, emptyCart } from './utils.js';

const app = createApp({
  data() {
    return {
      productId: null,
      product: null,
      count: 1,
      moneyFormatter: new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
      }),
      relatedProducts: [],
      allProducts: [],
      productCategory: "",

      currentCustomer: [],
      checkUser: false,

      cart: {
          cartItems: [],
      },
    };
  },
  created() {
    this.loadCart();
    
    let urlParams = new URLSearchParams(location.search);
    this.productId = urlParams.get("id");

    console.log(this.productId);

    let urlParams2 = new URLSearchParams(location.search);
    this.productCategory = urlParams2.get("category");
    // Carga todos los productos al acceder a this.allProducts
    axios.get('/api/products')
      .then(res => {
        this.allProducts = res.data;
        console.log(this.allProducts);

        if(this.productCategory == "all") {
            this.productsByCategory = this.allProducts;
        } else {
            this.productsByCategory = this.allProducts.filter(prod => prod.category == this.productCategory);
        }

        axios.get(`/api/product/${this.productId}`)
          .then(res => {
            this.product = res.data;
            console.log(this.product);

            if (!this.product) {
              console.error("Producto no encontrado");
            }

            // Luego de obtener el producto actual, filtra los productos relacionados.
            if (this.product && this.product.category && Array.isArray(this.allProducts)) {
              this.relatedProducts = this.allProducts.filter(
                (prod) => prod.category === this.product.category
              );
            }
            console.log(this.relatedProducts);
          })
          .catch(error => {
            console.error("Error al obtener detalles del producto:", error);
          });
      })
      .catch(error => {
        console.error("Error al obtener todos los productos:", error);
      });

    axios.get('/api/customer/current')
      .then(res => {
          this.currentCustomer = res.data;
          if (this.currentCustomer) {
                           this.checkUser = true;
                      }
      })
      .catch(err => {
          console.error(err);
  });

  this.moneyFormatter = new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
  })
  },
  methods: {
    getHomeUrl() {
      // Lógica para construir la URL de la página de todos los productos
      return "/products.html"; // Ajusta la URL según la ubicación real de tu página principal
    },
    increment() {
      if (this.count < 10) {
        this.count++;
      }
    },
    decrement() {
      if (this.count > 1) {
        this.count--;
      }
    },
    getProductUrl(productId) {
        // Lógica para construir la URL del producto
        return `/product.html?id=${productId}`;
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
        if(this.checkUser) {
            return '../pages/profile.html'
        }
        return '../pages/login-signup.html'
    },
    checkUserLoggedCheckout() {
      if(this.checkUser) {
          return 'checkout.html'
      }
      return 'login-signup.html'
  },
}
});

app.mount("#app");
