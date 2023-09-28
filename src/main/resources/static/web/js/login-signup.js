let { createApp } = Vue

const options = {
    data() {
        return {
            showForm:false,

        }
    },

    created() {
      
        
        
    },

    methods: {
          showForms(){
            this.showForm = !this.showForm;
            console.log(this.showForm);
          }
          

            }
        }



const app = createApp(options)

app.mount('#app')