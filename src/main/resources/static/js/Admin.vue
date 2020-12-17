<template>
    <div class="form-wrapper-admin">
        <div>
            <div class="tab">
                <button class="tablinks" @click="openCity( 'brand')">BRAND</button>
                <button class="tablinks" @click="openCity( 'category')">CATEGORY</button>
                <button class="tablinks" @click="openCity('product')">PRODUCT</button>
            </div>

            <div id="brand" class="tabcontent">
                <h1>СREATE BRAND</h1>
                <input v-model="brandname" placeholder="Название бренда">
                <button class="button" @click="toBrand">Добавить бренд</button>
                <button class="button" @click="close">закрыть</button>
            </div>

            <div id="category" class="tabcontent">
                <h1>СREATE CATEGORY</h1>
                <input class="admin-edit" v-model="categoryname" placeholder="Название категории">
                <button class="button" @click="toCategory">Добавить категорию</button>
                <button class="button" @click="close">закрыть</button>
            </div>

            <div id="product" class="tabcontent">
                <h1>СREATE PRODUCT</h1>
                <input class="admin-edit" v-model="productname" placeholder="Название продукта">
                <input class="admin-edit" v-model="description" placeholder="Описание продукта">
                <select class="admin-edit" v-model="selectCategory">
                    <option value="0">Все категории</option>
                    <option v-for="category in categories" :key="category.categoryid" :value="category.categoryid">{{category.categoryname }}</option>
                </select>
                <select class="admin-edit" v-model="selectBrand">
                    <option value="0">Все категории</option>
                    <option v-for="brand in brands" :key="brand.brandid" :value="brand.brandid">{{brand.brandname}}</option>
                </select>
                <input class="admin-edit" v-model="price" placeholder="Цена продукта">
                <input class="button" v-model="img" placeholder="Изображение" type="file">
                {{img}}
                <button class="button" @click="toServer">Добавить продукт в каталог</button>
                <button class="button" @click="close">закрыть</button>
            </div>

            <div class="data">
                <p style="text-align: center;"><input class="button" type="submit" value="LOGOUT" @click="logout"></p>
                <h1>Привет {{this.currentu.username}}, это админский кабинет!</h1>
                <div>
                    <h1>Данные:</h1>
                    <h1>USERID: {{this.currentu.userid}}</h1>
                    <h1>Почта: {{this.currentu.email}}</h1><br>
                </div>

            </div>
            <div class="orders-list">
                <h1 style="text-align: center">Заказы</h1>
                <h1 style="text-align: center">Общее количество потраченных денег: {{totalPrice}}</h1>
                <div v-for="order in orders">
                    <h1>Номер заказа: {{order.order.ordersid}} | {{order.order.user.username}}</h1>
                    <h1>{{order.products.name}} | {{order.products.price}}</h1>
                </div>
            </div>
        </div>
    </div>
</template>
<script>

    define(["Vue"],
        function (Vue) {
            return Vue.component("Admin", {
                template: template,
                props:{
                    currentu:{
                        type: Object
                    }
                },
                data(){
                    return{
                        currentUser:null,
                        orders:[],
                        categories:[],
                        brands:[],
                        img:'',
                        selectCategory:0,
                        selectBrand:0,
                        productname:'',
                        description:'',
                        price:0,
                        categoryname:'',
                        brandname:''
                    };
                },
                mounted()
                {
                    fetch('/products/categories')
                        .then((response) => {
                            return response.json();
                        })
                        .then(data => {
                            this.categories = data;
                        });
                    fetch("/products/brands")
                        .then((response) => {
                            return response.json();
                        })
                        .then(data => {
                            this.brands = data;
                        });
                },
                beforeMount(){
                    fetch('/products/categories')
                        .then((response) => {
                            return response.json();
                        })
                        .then(data => {
                            this.categories = data;
                        });
                    fetch("/products/brands")
                        .then((response) => {
                            return response.json();
                        })
                        .then(data => {
                            this.brands = data;
                        });
                    if(this.currentu !== null)
                    {
                        console.log(this.currentu.userid)
                        fetch('/orders/products/')
                            .then((response) => {
                                return response.json();
                            })
                            .then(data => {
                                this.orders = data;
                            });
                    }
                    if(localStorage.getItem('session') || localStorage.getItem('session') == null)
                    {
                        this.currentUser = JSON.parse(localStorage.getItem('session'));
                    }
                    else
                    {
                        localStorage.setItem('session',"")
                    }
                },
                beforeUpdate(){
                    if(localStorage.getItem('session') || localStorage.getItem('session') == null)
                    {
                        this.currentUser = JSON.parse(localStorage.getItem('session'));
                    }
                    else
                    {
                        localStorage.setItem('session',"")
                    }
                },
                computed:{
                    totalPrice(){
                        let total = 0;
                        for(let item of this.orders)
                        {
                            total += item.products.price;
                        }
                        return total;
                    }
                },
                methods: {
                    close:function(){
                        var i, tabcontent, tablinks;
                        tabcontent = document.getElementsByClassName("tabcontent");
                        for (i = 0; i < tabcontent.length; i++) {
                            tabcontent[i].style.display = "none";
                        }
                    },
                    openCity:function( cityName) {
                        var i, tabcontent, tablinks;
                        tabcontent = document.getElementsByClassName("tabcontent");
                        for (i = 0; i < tabcontent.length; i++) {
                            tabcontent[i].style.display = "none";
                        }
                        tablinks = document.getElementsByClassName("tablinks");
                        for (i = 0; i < tablinks.length; i++) {
                            tablinks[i].className = tablinks[i].className.replace(" active", "");
                        }
                        document.getElementById(cityName).style.display = "block";
                    },
                    logout:function(){
                        localStorage.setItem("session","");
                        localStorage.setItem("localproducts", "");
                        this.$emit("logout")
                        this.$router.push("/")
                    },
                    clicked:function(login){;
                        this.$emit('toggle', login);
                    },
                    toBrand:function(){
                        fetch('/products/brands',{
                            method:'POST',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify({brandname:this.brandname})
                        })
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    console.log("Something going wrong in FETCH" + response.status)
                                    return;
                                }
                            })
                    },
                    toCategory:function(){
                        fetch('/products/categories',{
                            method:'POST',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify({categoryname:this.categoryname})
                        })
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    console.log("Something going wrong in FETCH" + response.status)
                                    return;
                                }
                            })
                    },
                    toServer: function () {
                        let product = {name:this.productname, price:this.price, img: "pics/products/"+this.img.slice(12), description: this.description,categories:{categoryid:this.selectCategory},brands:{brandid:this.selectBrand}}
                        let user;
                        //console.log(message);
                        fetch('/products',{
                            method:'POST',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify(product)
                        })
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    console.log("Something going wrong in FETCH" + response.status)
                                    return;
                                }
                            })

                    }
                }
            })
        }
    );
</script>
<style>
    .admin-edit
    {
        margin: 10px;
        border-radius: 25px;
        padding: 15px;
    }
    .orders-list
    {
        border: 5px #e52ed7 solid;
        margin-top: 15px;
        padding: 25px;
    }
    .data
    {
        border: 5px yellow solid;
        padding: 25px;
        margin-top: 15px;
    }
    .form-wrapper-admin
    {
        margin: 25px;
    }
    .tab {
        overflow: hidden;
        border: 1px solid #ccc;
        background-color: #f1f1f1;
    }

    /* Style the buttons inside the tab */
    .tab button {
        background-color: inherit;
        float: left;
        border: none;
        outline: none;
        cursor: pointer;
        padding: 14px 16px;
        transition: 0.3s;
        font-size: 17px;
    }

    /* Change background color of buttons on hover */
    .tab button:hover {
        background-color: #ddd;
    }

    /* Create an active/current tablink class */
    .tab button.active {
        background-color: #ccc;
    }

    /* Style the tab content */
    .tabcontent {
        display: none;
        padding: 6px 12px;
        border: 1px solid #ccc;
        border-top: none;
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
    .button
    {
        outline: none;
        display: inline-block;
        border: 0;
        width: 175px;
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
    }
    .button:hover
    {
        background: #2EE59D;
        box-shadow: 0 15px 20px rgba(46,229,157,.4);
        color: white;
        transform: translateY(-7px);
    }
</style>