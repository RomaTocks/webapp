<template>
    <div class="form-wrapper-register">
        <div class="form-register">
            <h1 class="register">Регистрация</h1>
            <div id="error" class="error-box-reg" style="display: none"></div>
            <input class="input-register" v-model="login" style="display: block; margin: 20px 166px 15px;" placeholder="Введите логин">
            <input class="input-register" type="password" v-model="password"  style="margin-left: 166px;margin-right: 150px;margin-bottom: 15px;" placeholder="Введите пароль">
            <input class="input-register" v-model="mail" style="margin-left: 166px;margin-right: 150px;margin-bottom: 15px;" placeholder="Введите почту">
            <input class="button" style="margin-left: 168px" type="submit" value="Подтвердить" @click="toServer">
        </div>
    </div>
</template>
<script>
    define(["Vue"],
        function (Vue) {
            return Vue.component("Auth", {
                template: template,
                props:['currentUser'],
                data: function(){
                    return{
                        login:'',
                        password:'',
                        mail:''
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
                    toServer: function () {
                        let message = {username:this.login, mail:this.mail, password:this.password, "roles":{"rolesid":1}};
                        fetch('/message',{method:'POST',headers: {'Content-Type': 'application/json;charset=utf-8'},body:JSON.stringify(message)})
                            .then(response => {
                                if (response.status !== 200)
                                {
                                    response.json().then(data => {this.showError(true, data.error) });
                                    console.log("Something going wrong in FETCH" + response.status)
                                    return;
                                }
                                else
                                {
                                    this.$router.push("/")
                                }
                            });
                    }
                }
            })
        }
    );
</script>
<style>
    .error-box-reg {
        background-color: #d45e5e;
        border: 1px solid #9a1818;
        border-radius: 10px;
        padding: 5px 15px;
        margin: 5px;
        text-align: center;
        font-family: 'Montserrat', sans-serif;
        color: #8b0000;

    }
    .register
    {
        font-family: 'Montserrat', sans-serif;
        margin: 0;
        text-align: center;
        padding-top: 30px;

    }
    .input-register
    {
        outline: none;
        padding: 10px;
        border: 0;
        border-bottom: 3px darkviolet solid;
    }
    .input-register:hover
    {
        transition: 0.5s;
        border-bottom: 3px solid #2EE59D;
    }
    .form-register
    {
        width: 500px;
        height: 340px;
        border-radius: 15px;
        background-color: white;
    }
    .form-wrapper-register
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