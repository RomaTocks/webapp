<template>
        <div class="wrapper">
            <div class="shop-block shop-block-first">
                <h1>Блок или каталог или я не знаю что-то блин такое ну да, чтобы вот цепляло, знаешь изюминка такая</h1>
            </div>
            <div class="shop-block shop-block-second">
                <h1>Это вот тоже такое вот оно прекалдесное чтобы вот их целых 2 было, типа ты вот смотришь на них и всёёёёёё</h1>
            </div>
          <!--  <h1>ОТМЕЧЕННЫЕ ПОЛЬЗОВАТЕЛИ</h1>
            <div v-for="(localmessage, n) in localmessages">
                <h1>{{localmessage}}</h1>
                <button @click="removeMessage(n)">remove :(</button>
            </div>
            <h1 id="popular">ПОПУЛЯРНОЕ</h1>-->
           <!-- <div class="product-wrapper">
                <div class="product-wrap" v-for="message in messages">
                    <div class="product-image">
                        <a href=""><img src="pics/undefined.jpg"></a>
                    </div>
                    <div class="product-list" >
                        <h1>{{message.username}}</h1>
                    </div>
                    <div class="price">{{message.password}}</div>
                    <button class="ui-button" value="message" v-on:click="function() {
                        localmessages.push(message);
                        saveMessages();
                }">В КОРЗИНКУ</button>
                </div>
            </div>-->
            <div class="shop-block shop-block-third">
                <h1>Ну ладна, ладна, последний ну всё уже всё не будет больше, ну вот не хотите и не надо, а вот и ладно вот и не будет больше, а всё, всё уже, а раньше надо было</h1>
            </div>
        </div>
</template>
<script>
    define(["Vue"],
        function (Vue) {
            return Vue.component("Home", {
                template: template,
                data() {
                    return {
                        messages:null,
                        localmessages:[],
                        current:''
                    };
                },
                beforeMount(){
                    if(this.messages == null) {
                        fetch('/message')
                            .then((response) => {
                                return response.json();
                            })
                            .then(data => {
                                this.messages = data;
                            });
                    }
                    this.current = localStorage.getItem('session')
                },
                mounted(){
                    if(localStorage.getItem('localmessages')){
                        try
                        {
                            this.localmessages = JSON.parse(localStorage.getItem('localmessages'))
                        }
                        catch (e) {
                            localStorage.removeItem('localmessages');
                        }
                    }
                },
                methods: {
                    removeMessage(n){
                      this.localmessages.splice(n,1);
                      this.saveMessages();
                    },
                    addMessage(){
                        this.localmessages.push(this.message);
                        this.saveMessages();
                    },
                    saveMessages(){
                        const parsed = JSON.stringify((this.localmessages));
                        localStorage.setItem('localmessages', parsed);

                    }
                }
            })
        }
    );
</script>
<style>
    .wrapper
    {
        height: 100%;
    }
    body {
        margin-top: 0;
        margin-left: 0;
        margin-right: 0;
        margin:0;
        background-color: white;
    }
    .shop-block-first
    {
        background-color: #beb8eb;
    }
    .shop-block-second
    {
        background-color: #0b5563;
        color: white;
    }
    .shop-block-third
    {
        background-color: #a2bce0;
    }
    .shop-block
    {
        height: 690px;
    }
    .shop-block h1
    {
        margin: 0;
        padding-left: 50px;
        padding-right: 50px;
        font-family: system-ui;
        padding-top: 20%;
    }
    #popular
    {
        text-align: center;
        font-family: system-ui;
    }
    .product-wrapper
    {
        display: flex;
        flex-wrap: wrap;
        width: 320px;
        margin: auto auto 25px;
    }

    @media (min-width: 576px) {
        .product-wrapper {
            width: 320px;
        }
    }


    @media (min-width: 768px) {
        .product-wrapper {
            width: 640px;
        }
    }


    @media (min-width: 992px) {
        .product-wrapper {
            width: 960px;
        }
    }


    @media (min-width: 1320px) {
        .product-wrapper {
            width: 1280px;
        }
    }
    .product-list
    {
        font-family: system-ui;
    }
    .product-wrap {
        width: 300px;
        text-align: center;
        background: white;
        margin: 10px;
    }
    .product-wrap:hover
    {
        transition: 1s;
        background-color: #dad7f1;
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
    }
    .ui-button:hover
    {
        background: #2EE59D;
        box-shadow: 0 15px 20px rgba(46,229,157,.4);
        color: white;
        transform: translateY(-7px);
    }
    h4
    {
        text-decoration: none;
        outline: none;
        display: inline-block;
        width: 140px;
        height: 45px;
        line-height: 45px;
        border-radius: 45px;
        margin: 10px 20px;
        font-family: 'Montserrat', sans-serif;
        font-size: 11px;
        text-transform: uppercase;
        text-align: center;
        letter-spacing: 3px;
        font-weight: 600;
        color: #524f4e;
        background: white;
        box-shadow: 0 8px 15px rgba(0,0,0,.1);
        transition: .3s;
    }
    h4:hover {
        background: #2EE59D;
        box-shadow: 0 15px 20px rgba(46,229,157,.4);
        color: white;
        transform: translateY(-7px);
    }
    .product-image
    {
        position: relative;
    }
    .product-image a
    {
        font-family:system-ui;
        display: block;
    }
    .product-image img
    {
        display: block;
        width: 100%;
    }
</style>