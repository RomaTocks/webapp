<template>
    <div style="flex: 1 0 auto">
        <div class="wrapper">
            <input class="catalog-thing" type="text" v-model="search" placeholder="Поиск по товарам">
            <select class="catalog-thing" v-model="selectSort">
                <option v-for="rule in sortRules" :key="rule.key" :value="rule.key">{{ rule.title }}</option>
            </select>
            <select class="catalog-thing" v-model="selectCategory">
                <option value="0">Все категории</option>
                <option v-for="category in categories" :key="category.categoryid" :value="category.categoryid">{{category.categoryname }}</option>
            </select>
            <select class="catalog-thing" v-model="selectBrand">
                <option value="0">Все бренды</option>
                <option v-for="brand in brands" :key="brand.brandid" :value="brand.brandid">{{brand.brandname}}</option>
            </select>
            <input class="catalog-thing" type="number" v-model="minPrice" placeholder="Мин. цена">
            <input class="catalog-thing" type="number" v-model="maxPrice" placeholder="Макс. цена">
            <p style="text-align: center"><button @click="clear" class="ui-button">Сбросить</button></p>
            <h1 id="popular">КАТАЛОГ ТОВАРОВ</h1>
            <div class="product-wrapper">
                <div class="product-wrap" v-for="product in filtered">
                    <div class="product-image">
                        <router-link :to="{path:'/catalog/'+product.productid}" v-bind:products="products"><a><img :src="product.img"></a></router-link>
                    </div>
                    <div class="product-list" >
                        <h1>{{product.categories.categoryname}}</h1>
                        <h1>{{product.name}}</h1>
                    </div>
                    <div class="price">{{product.price}}$</div>
                    <button class="ui-button" value="product" v-on:click="function() {
                        localproducts.push(product);
                        saveProducts();
                }">В КОРЗИНКУ</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    define(["Vue"],
        function (Vue) {
            return Vue.component("Catalog", {
                template: template,
                data() {
                    return {
                        minPrice:1,
                        maxPrice:99999,
                        search:'',
                        products:[],
                        categories: [],
                        brands:[],
                        selectCategory:0,
                        selectBrand:0,
                        localproducts:[],
                        sortRules: [
                            { key: 1, title: 'По порядку' },
                            { key: 2, title: 'По цене, сначала дешевые' },
                            { key: 3, title: 'По цене, сначала дорогие' }
                        ],
                        selectSort: 1
                    };
                },
                computed:
                {
                  filtered(){
                     var filteredProducts = this.products
                          .filter(product => {
                              return this.selectCategory == 0 || product.categories.categoryid == this.selectCategory;
                          })
                          .filter(product => {
                              return this.selectBrand == 0 || product.brands.brandid == this.selectBrand;
                          })
                          .filter(product => {
                              return this.minPrice <= Number(product.price) && Number(product.price) <= this.maxPrice;
                          })
                          .filter(item => item.name.toLowerCase().indexOf(this.search.toLowerCase()) !== -1);
                      this.$on('deleteProduct',function (id) {
                          filteredProducts.splice(filteredProducts.findIndex(product => product.productid === id),1)
                      });
                      switch(this.selectSort){
                          case 2: return filteredProducts.sort(sortAscByPrice);
                          case 3: return filteredProducts.sort(sortDescByPrice);
                          default: return filteredProducts;
                      }
                  }
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
                        fetch('/products')
                            .then((response) => {
                                return response.json();
                            })
                            .then(data => {
                                this.products = data;
                            });
                        this.current = localStorage.getItem('session')
                },
                mounted(){
                    fetch('/products')
                        .then((response) => {
                            return response.json();
                        })
                        .then(data => {
                            this.products = data;
                        });
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
                methods: {
                    clear() {
                      this.search = '';
                      this.selectSort = 1;
                      this.minPrice = 1;
                      this.maxPrice = 99999;
                      this.selectBrand = 0;
                      this.selectCategory = 0;
                    },
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
                }
            })
        }
    );
    var sortAscByPrice = function (d1, d2) { return (d1.price > d2.price) ? 1 : -1; };
    var sortDescByPrice = function (d1, d2) { return (d1.price < d2.price) ? 1 : -1; };
</script>
<style >
    .catalog-thing
    {
        margin-top: 15px;
        margin-left: 10%;
        margin-right: 10%;
        padding: 15px;
        border-radius: 35px;
        border: none;
        box-shadow: 5px 5px dodgerblue;
        outline: none;
    }
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