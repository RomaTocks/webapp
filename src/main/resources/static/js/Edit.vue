<template>
    <div style="display: flex; flex-direction: column" class="content">
        <input class="input-edit" v-model="product.name">
        <input class="input-edit" v-model="product.price">
        <input class="input-edit" v-model="product.description">
        <input class="input-edit" v-model="product.img">
        <input class="input-edit" v-model="product.categories.categoryid">
        <input class="input-edit" v-model="product.brands.brandid">
        <div style="justify-content: center">
            <button class="ui-button" @click="put">ИЗМЕНИТЬ</button>
            <button class="ui-button" @click="back">НА СТРАНИЦУ</button>
        </div>
    </div>
</template>
<script>
    define(["Vue","font-awesome"],
        function (Vue) {
            return Vue.component("Edit", {
                template: template,
                data(){
                    return{
                        product:{
                            type:Object
                        }
                    }
                },
                beforeMount(){
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
                    back(){
                        this.$router.push("/catalog/"+this.$route.params.id)
                    },
                    put(){
                        let path = this.$route.params.id
                        fetch('/products/'+path,{
                            method:'PUT',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify(this.product)
                        })
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    console.log("Something going wrong in FETCH" + response.status)
                                    return;
                                }
                                else
                                {
                                    this.$router.push("/catalog")
                                }
                            });
                    }
                }
            })
        }
    );
</script>
<style>
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
    .input-edit
    {
        margin: 10px;
        border-radius: 25px;
        padding: 15px;
    }
    .content
    {
        flex: 1 0 auto;
        padding-top: 20px;
    }
    html
    {
        height: 100%;
    }
    body
    {
        margin: 0;
        height: 100%;
    }
</style>