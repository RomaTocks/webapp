<template>
    <main class="main-container">
        <div class = "topnav" id="myTopnav">
            <router-link to="/"><p>DIGITAL.WLD</p></router-link>
            <router-link to="/catalog"><p>КАТАЛОГ</p></router-link>
            <router-link to="/about"><p>О ПРОЕКТЕ</p></router-link>
            <router-link class="right" to="/auth"><p v-if="this.currentu == null" class="right">ВОЙТИ</p><p v-else class="right">
                {{this.currentu.username}}</p></router-link>
            <router-link v-if="this.currentu == null" class="right" to="/register"><p>РЕГИСТРАЦИЯ</p></router-link>
            <div v-else></div>
            <router-link class="right" to="/basket"><p>КОРЗИНА({{count}})</p></router-link>
            <a href="javascript:void(0);" class="icon" @click="myFunction()">
                <i class="fa fa-bars"></i>
            </a>
        </div>
        <router-view v-bind:currentu="currentu" @changebasket="changeBasket" @logout="logOut" @toggle="toggleAuth"></router-view>
        <div class="footer"><h2>All rights are fictions. Powered by Tocks</h2></div>
    </main>
</template>
<script>
    define(["Vue","font-awesome"],
        function (Vue) {
            return Vue.component("App", {
                template: template,
                data(){
                    return{
                        currentu: null,
                        basketproducts:[],
                        count:0
                    }
                },
                methods: {
                    myFunction() {
                        var x = document.getElementById("myTopnav");
                        if (x.className === "topnav") {
                            x.className += " responsive";
                        } else {
                            x.className = "topnav";
                        }
                    },
                    clicked:function(user){
                        this.currentu = user;
                    },
                    changeBasket:function(count){
                        this.count = count
                    },
                    toggleAuth:function (user) {
                        this.currentu = user;
                    },
                    logOut:function () {
                        this.currentu = null;
                    }
                },
                beforeMount(){
                    if(localStorage.getItem('localproducts') || localStorage.getItem('localproducts') == null)
                    {
                        this.basketproducts = JSON.parse(localStorage.getItem('localproducts'));
                        this.count = this.basketproducts.length;
                    }
                    else
                    {
                        localStorage.setItem('session',"")
                    }
                    if(localStorage.getItem('session') || localStorage.getItem('session') == null)
                    {
                        this.currentu = JSON.parse(localStorage.getItem('session'));
                    }
                    else
                    {
                        localStorage.setItem('session',"")
                    }
                },
                mounted(){
                    if(localStorage.getItem('localproducts') || localStorage.getItem('localproducts') == null)
                    {
                        this.basketproducts = JSON.parse(localStorage.getItem('localproducts'));
                        this.count = this.basketproducts.length;
                    }
                    if(localStorage.getItem('session') || localStorage.getItem('session') == null)
                    {
                        this.currentu = JSON.parse(localStorage.getItem('session'));
                    }
                    else
                    {
                        localStorage.setItem('session',"")
                    }
                },
                beforeUpdate() {
                    if(localStorage.getItem('session') || localStorage.getItem('session') == null)
                    {
                        this.currentu = JSON.parse(localStorage.getItem('session'));
                    }
                    else
                    {
                        localStorage.setItem('session',"")
                    }
                }
            })
        }
    );
</script>
<style>
    .main-container
    {
        display: flex;
        flex-direction: column;
        min-height: 100%;
    }
    .main-container *
    {

    }
    html, body
    {
        margin: 0;
        height: 100%;
    }
    .right
    {
        float: right;
    }
    .wrapper
    {
        display: flex;
        flex-direction: column;
        height: 100%;
    }
    .wrappered *
    {
    }
    .footer
    {
        background-color: #5E5C6C;
        height: 100px;
        flex: 0 0 auto;
    }
    .footer h2
    {
        margin: 0;
        padding-top: 35px;
        text-align: left;
        color: white;
        font-family: Roboto;
        margin-left: 5%;
    }
    .topnav {
        background-color: #5299d3;
        position: sticky;
        top: 0;
        z-index: 3;
    }

    .topnav a {
        float: left;
        display: block;
        color: #f2f2f2;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
        font-size: 17px;
        font-family: Roboto;
        font-weight: bold;
        font-size: large;
    }

    .topnav a:hover {
        background-color: #ddd;
        color: black;
    }

    .active {
        background-color: #4CAF50;
        color: white;
    }

    .topnav .icon {
        display: none;
        padding: 32px;
    }

    @media screen and (min-width: 901px) {
        .topnav a.right {
            float: right;
        }
    }
    @media screen and (max-width: 900px) {
        .topnav a:not(:first-child) {display: none;}
        .topnav a.icon {
            float: right;
            display: block;
        }
    }

    @media screen and (max-width: 900px) {
        .topnav.responsive .icon {
            position: absolute;
            right: 0;
            top: 0;
        }
        .topnav.responsive a {
            float: none;
            display: block;
            text-align: left;
        }
        .right
        {
            float: none;
            display: block;
            text-align: left;
        }
    }
</style>