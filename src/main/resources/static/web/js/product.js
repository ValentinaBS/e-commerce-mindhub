let { createApp } = Vue

const options = {
    data() {
        return {
            count: 1,
        };
    },
    methods: {
        increment() {
            if (this.count < 10) {
                this.count++;
            }
        },
        decrement() {
            if (this.count > 1) {
                this.count--;
            }
        }
    }
}



const app = createApp(options)

app.mount('#app')