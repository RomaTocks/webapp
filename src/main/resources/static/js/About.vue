<template>
<div class="content">
    <div class="images">
        <p class="pics"><img id="vue" class="icons" src="pics/vue.png"></p>
        <p class="pics"><img id="spring" class="icons" src="pics/spring.png"></p><br>
    </div>
        danyavol<i id="heart" class="fa fa-heart"></i>
    <div>
        <h1 id="title">Проект создан с помощью этих технологий.</h1>
    </div>
</div>
</template>
<script>
    define(["Vue","font-awesome"],
        function (Vue) {
            return Vue.component("About", {
                template: template,
            })
        }
    );
</script>
<style>
    #heart
    {
        color: deeppink;
    }
    .images
    {
        margin-right: auto;
        margin-left: auto;
        width: 35em;
    }
    .pics
    {
        display: inline-block;
        text-align: center;
    }
    .content
    {
        flex: 1 0 auto;
        padding-top: 75px;
    }
    html
    {
        height: 100%;
    }
    #title
    {
        text-align: center;
        font-family: 'Montserrat', sans-serif;
        font-size: 50px;
    }
    body
    {
        margin: 0;
        height: 100%;
    }
    #vue
    {
        margin-right: 25px;
    }
    #spring
    {
        margin-left: 25px;
    }
    .icons
    {
        width: 250px;
        float: left;
        display: inline-block;
    }
</style>