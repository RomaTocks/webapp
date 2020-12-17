<template>
    <div style="flex: 1 0 auto">
        <div style="
             margin: 15px;
             border: 3px #d25d7d solid;
             border-radius: 20px;
             padding: 20px;"
             v-if="this.check === true">
            <button class="ui-button" @click="deleteProduct">DELETE</button>
            <router-link class="ui-button" :to="{name:'edit'}">ИЗМЕНИТЬ</router-link>
            <router-view v-bind:product="product"></router-view>
        </div>
        <div v-else></div>
        <div class="product-container">
            <div class="image">
                <img :src="product.img">
            </div>
            <div class="text">
                <h1 class="font">{{product.name}}</h1>
                <p class="font">Описание: </p>
                <p class="font">{{product.description}}</p>
                <p class="font">{{product.price}} руб.</p><button class="ui-button" v-on:click="function() {
                        localproducts.push(product);
                        saveProducts();
                }">В корзинку</button>
            </div>
        </div>
    </div>
</template>
<script>
    define(["Vue"],
        function (Vue) {
            return Vue.component("Product", {
                template: template,
                props:{
                    currentu:{
                        type:Object
                    },
                    products:[]
                },
                data(){
                   return{
                       product:{
                           type:Object
                       },
                        check:false,
                       localproducts:[]

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
                },
                beforeMount()
                {
                    if(localStorage.getItem('session'))
                    {
                        fetch('/authentication/role',{
                            method:'POST',
                            headers:{'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify(JSON.parse(localStorage.getItem('session')))
                        })
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    console.log("Something going wrong in FETCH" + response.status)
                                    this.check = false
                                }
                                else
                                {
                                    this.check = true
                                }
                            })
                    }
                    else
                    {
                        this.check = false
                    }
                    let path = this.$route.params.id
                    fetch('/products/' + path)
                        .then((response) => {
                            return response.json();
                        })
                        .then(data => {
                            console.log(data)
                            this.product = data;
                        });
                },
                methods:{
                    removeProduct(n){
                        this.localproducts.splice(n,1);
                        this.saveProducts();
                        this.$emit('changebasket', this.localproducts.length)
                    },
                    addProduct(){
                        this.localproducts.push(this.product);
                        this.saveProducts();
                        this.$emit('changebasket', this.localproducts.length)
                    },
                    saveProducts(){
                        const parsed = JSON.stringify((this.localproducts));
                        localStorage.setItem('localproducts', parsed);
                        this.$emit('changebasket', this.localproducts.length)
                    },
                    deleteProduct(){
                        let path = '/products/'+this.$route.params.id;
                        fetch(path,{
                            method:'DELETE',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                        })
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    console.log("Something going wrong in FETCH" + response.status)
                                    return;
                                }
                            })
                        this.$emit('delete',this.$route.params.id)
                        this.$router.push("/catalog");
                    }
                }
            })
        }
    );
</script>
<style>
    .product-container
    {
        display: flex;
        flex-grow: 1;
    }
    .image
    {
        padding: 15px;
        width: 55%;
    }
    .image img
    {
        margin-left: 5%;
        width: 450px;
    }
    .text
    {
        padding-top: 150px;
        width: 35%;
    }
    @media (max-width: 830px){
        .product-container
        {
            flex-direction: column;
            align-items: center;
        }
        .text
        {
            padding-top: 100px;
        }
        .image img
        {
            margin-left: 0;
            width: 300px;
        }
        .image
        {
            width: auto;
        }
    }
    .ui-button
    {
        outline: none;
        display: inline-block;
        border: 0;
        width: 140px;
        height: 45px;
        line-height: 45px;
        border-radius: 45px;
        margin: 10px 20px;
        font-family: 'Montserrat', sans-serif;
        font-size: 11px;
        letter-spacing: 3px;
        font-weight: 600;
        color: #524f4e;
        background: white;
        box-shadow: 0 8px 15px rgba(0,0,0,.1);
        transition: .3s;
        text-align: center;
        text-decoration: none;
    }
    .ui-button:hover
    {
        background: #2EE59D;
        box-shadow: 0 15px 20px rgba(46,229,157,.4);
        color: white;
        transform: translateY(-7px);
    }
    html
    {
        height: 100%;
        width: 100%;
    }
    .font
    {
        font-family: 'Montserrat', sans-serif;
    }
</style>