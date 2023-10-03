const { createApp } = Vue;

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
    };
  },
  created() {
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
  },
});

app.mount("#app");
