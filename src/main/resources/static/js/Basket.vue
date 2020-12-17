<template>
    <div style="flex: 1 0 auto">
        <div id="basket-error" class="error-box" style="display: none"></div>
        <h1 class="title" v-if="localproducts < 3">Корзинка пуста</h1>
        <div v-else>
            <div v-for="(localproduct, n) in localproducts">
                <div class="basket-item">
                    <img style="width: 80px; height: 80px" :src="localproduct.img">
                    <h1>{{localproduct.name}}</h1>
                    <h2 style="margin-left: 10px"> Цена: {{localproduct.price}}</h2>
                    <button style="
                    margin-left: 15px;
                    border-radius: 15px;
                    width: 100px;
                    height: 30px;"
                    @click="removeProduct(n)">remove :(</button>
                </div>
            </div>
            <h1>Общая цена: {{totalPrice}}</h1>
            <button @click="Order">Оформить заказ</button>
        </div>
    </div>
</template>
<script>
    define(["Vue"],
        function (Vue) {
            return Vue.component("Basket", {
                template: template,
                props:{
                    currentu:{
                        type: Object
                    }
                },
                data() {
                    return {
                        localproducts: null,
                        localproductsprice:0
                    };
                },
                computed:
                {
                  totalPrice(){
                      let total = 0;
                      for(let item of this.localproducts)
                      {
                          total += item.price;
                      }
                      return total;
                  }
                },
                beforeUpdate()
                {
                    if(localStorage.getItem('localproducts')){
                        try
                        {
                            this.localproducts = JSON.parse(localStorage.getItem('localproducts'))
                        }
                        catch (e) {
                            localStorage.removeItem('localproducts');
                        }
                    }
                    else
                    {
                        localStorage.setItem('localproducts',"");
                    }
                },
                beforeMount(){
                    if(localStorage.getItem('localproducts')){
                        try
                        {
                            this.localproducts = JSON.parse(localStorage.getItem('localproducts'))
                        }
                        catch (e) {
                            localStorage.removeItem('localproducts');
                        }
                    }
                    else
                    {
                        localStorage.setItem('localproducts',"");
                    }
                },
                mounted(){
                    if(localStorage.getItem('localproducts')){
                        try
                        {
                            this.localproducts = JSON.parse(localStorage.getItem('localproducts'))
                        }
                        catch (e) {
                            localStorage.removeItem('localproducts');
                        }
                    }
                    else
                    {
                        localStorage.setItem('localproducts',"");
                    }
                },
                methods: {
                    showError:function(show=true, errorText) {
                        let box = document.getElementById("basket-error");
                        if (show) {
                            box.innerText = errorText;
                            box.style.display = 'block';
                            setTimeout(() => box.style.display = 'none',3000)
                        } else {
                            box.style.display = 'none';
                        }
                    },
                    Order(){
                        let message = {"user":this.currentu}
                        if(message.user == null)
                        {
                           this.showError(true, "Вы не зарегистрированы чтобы оформить заказ!")
                        }
                        else
                        {
                            fetch('/orders',{
                                method:'POST',
                                headers: {'Content-Type': 'application/json;charset=utf-8'},
                                body: JSON.stringify(message)
                            })
                                .then(response => {
                                    if (response.status !== 200) {
                                        console.log("Something going wrong in FETCH" + response.status)
                                        return;
                                    } else {
                                        response.json().then(data => {
                                            console.log("FETCH ЗАПРОС");
                                            this.localproducts.forEach(element => {
                                                let order = {"order":data,"products":element};
                                                console.log(element);
                                                fetch('/orders/products',{
                                                    method:'POST',
                                                    headers:{'Content-Type': 'application/json;charset=utf-8'},
                                                    body: JSON.stringify(order)
                                                })
                                                    .then(response => {
                                                        if (response.status !== 200) {
                                                            console.log("Something going wrong in FETCH" + response.status)
                                                            return;
                                                        }
                                                        else
                                                        {
                                                            localStorage.setItem('localproducts', "");
                                                            this.localproducts = localStorage.getItem('localproducts')
                                                            console.log("FETCH ЗАПРОС");
                                                        }
                                                    })
                                            });
                                            console.log("ЗАКАЗ ОФОРМЛЕН");
                                        });
                                    }
                                })
                        }
                    },
                    removeProduct(n){
                        this.localproducts.splice(n,1);
                        this.saveProducts();
                        this.$emit('changebasket', this.localproducts.length)
                    },
                    addProduct(){
                        this.localproducts.push(this.message);
                        this.saveProducts();
                        this.$emit('changebasket', this.localproducts.length)
                    },
                    saveProducts(){
                        const parsed = JSON.stringify((this.localproducts));
                        localStorage.setItem('localproducts', parsed);
                        this.$emit('changebasket', this.localproducts.length)

                    }
                }
            },)
        }
    );
</script>
<style>
    .basket-item
    {
        margin: 10px;
        padding-left: 5%;
        padding-right: 5%;
        display: flex;
        flex-direction: row;
        align-items: center;
        border-radius: 20px;
        border: 5px #2ee59d solid;
    }
    .error-box
    {
        background-color: #d45e5e;
        border: 1px solid #9a1818;
        border-radius: 10px;
        padding: 5px 15px;
        margin: 5px;
        text-align: center;
        font-family: 'Montserrat', sans-serif;
        color: #8b0000;
    }
    body{
        margin: 0;
    }
    .title
    {
        text-align: center;
        font-family: 'Montserrat', sans-serif;
    }
</style>