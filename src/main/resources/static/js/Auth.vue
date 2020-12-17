<template>
    <div class="form-wrapper-auth">
        <div v-if="this.currentu == null">
            <div class="form">
                <h1 class="auth">Авторизация</h1>
                <div id="error" class="error-box" style="display: none"></div>
                <input class="input" required v-model="login" style="display: block; margin: 20px 166px 15px;" placeholder="Введите логин">
                <input class="input" required type="password" v-model="password" style="margin-left: 166px;margin-right: 150px;margin-bottom: 15px;" placeholder="Введите пароль">
                <input class="button" style="margin-left: 168px" type="submit" value="Подтвердить" @click="toServer">
            </div>
        </div>
        <div style="margin-top: 100px" v-else>
            <p style="text-align: center;"><input class="button" type="submit" value="LOGOUT" @click="logout"></p>
            <h1>Привет {{this.currentu.username}}, это твой личный кабинет!</h1>
            <h1>Данные:</h1>
            <h1>USERID: {{this.currentu.userid}}</h1>
            <h1>Почта: {{this.currentu.email}}</h1><br>
            <h1>Заказы</h1>
            <h1>Общее количество потраченных денег: {{totalPrice}}</h1>
            <div v-for="order in orders">
                <h1>Номер заказа: {{order.order.ordersid}}</h1>
                <h1>{{order.products.name}} | {{order.products.price}}</h1>

            </div>
        </div>
    </div>
</template>
<script>
    define(["Vue"],
        function (Vue) {
            return Vue.component("Auth", {
                template: template,
                props:{
                    currentu:{
                        type: Object
                    }
                },
                data(){
                    return{
                        currentUser:null,
                        login:'',
                        password:'',
                        orders:[],
                    };
                },
                beforeMount(){
                    if(this.currentu !== null)
                    {
                        console.log(this.currentu.userid)
                        fetch('/orders/products/'+this.currentu.userid)
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
                    showError:function(show=true, errorText) {
                        let box = document.getElementById("error");
                        if (show) {
                            box.innerText = errorText;
                            box.style.display = 'block';
                            setTimeout(() => box.style.display = 'none',3000)
                        } else {
                            box.style.display = 'none';
                        }
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
                        toServer: function () {
                            let message = {username:this.login, password:this.password}
                            if(message.username === '' && message.password === '')
                            {
                                this.showError(true, "Введите данные");
                            }
                            else
                            {
                                this.showError(false);
                                fetch('/authentication',{
                                    method:'POST',
                                    headers: {'Content-Type': 'application/json;charset=utf-8'},
                                    body: JSON.stringify(message)
                                })
                                    .then(response => {
                                        if (response.status !== 200)
                                        {
                                            response.json().then(data => {this.showError(true, data.error) });
                                            console.log("Something going wrong in FETCH" + response.status)
                                            return;
                                        }
                                        else
                                        {
                                            response.json().then(data => {
                                                localStorage.setItem('session',JSON.stringify(data));
                                                console.log("FETCH ЗАПРОС");
                                                this.currentUser = data;
                                                this.$emit('toggle',data)
                                                this.$router.push('/');
                                            } );
                                        }
                                    })

                            }

                      }
                }
            })
        }
    );
</script>
<style>
    .error-box {
        background-color: #d45e5e;
        border: 1px solid #9a1818;
        border-radius: 10px;
        padding: 5px 15px;
        margin: 5px;
        text-align: center;
        font-family: 'Montserrat', sans-serif;
        color: #8b0000;

    }
    .auth
    {
        font-family: 'Montserrat', sans-serif;
        margin: 0;
        text-align: center;
        padding-top: 30px;
    }
    .input
    {
        outline: none;
        padding: 10px;
        border: 0;
        border-bottom: 3px darkviolet solid;
    }
    .input:hover
    {
        transition: 0.5s;
        border-bottom: 3px solid #2EE59D;
    }
    .form
    {
        width: 500px;
        height: 300px;
        border-radius: 15px;
        background-color: white;
    }
    .form-wrapper-auth
    {
        display: flex;
        flex: 1 0 auto;
        align-items: center;
        justify-content: center;
        background-color: lightgray;
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