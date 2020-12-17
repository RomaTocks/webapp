<script>
    define(["Vue", "vue-router"], function (Vue, VueRouter) {
        Vue.use(VueRouter);

        const asyncComp = function (componentName) {
            return function (resolve) {
                require([componentName], resolve);
            };
        };

        const routes = [
            {
                path:'/register',
                component: asyncComp("vue!js/Register")
            },
            {
                path: '/auth',
                component: asyncComp("vue!js/Auth"),
                beforeEnter:(from,to,next) => {
                        if(localStorage.getItem('session'))
                        {
                            this.currentUser = JSON.parse(localStorage.getItem('session'));
                            fetch('/authentication/role',{
                                method:'POST',
                                headers:{'Content-Type': 'application/json;charset=utf-8'},
                                body: JSON.stringify(this.currentUser)
                            })
                                .then(response => {
                                    if (response.status !== 200)
                                    {
                                        console.log("Something going wrong in FETCH" + response.status)
                                        next();
                                    }
                                    else
                                    {
                                        next("/admin");
                                    }
                                })
                        }
                        else
                        {
                            next();
                        }
                }
            },
            {
                path: '/catalog',
                component: asyncComp("vue!js/Catalog")
            },
            {
                path: '/catalog/:id',
                component: asyncComp("vue!js/Product"),
                props: true,
                children:[
                    {
                        name:'edit',
                        path:'edit',
                        component: asyncComp("vue!js/Edit")
                    }
                ]
            },
            {
                path: '/admin',
                component: asyncComp("vue!js/Admin"),
                beforeEnter:(from,to,next) => {
                    if(localStorage.getItem('session') == null)
                    {
                        next("/");
                    }
                    else
                    {
                        if(localStorage.getItem('session'))
                        {
                            this.currentUser = JSON.parse(localStorage.getItem('session'));
                            fetch('/authentication/role',{
                                method:'POST',
                                headers:{'Content-Type': 'application/json;charset=utf-8'},
                                body: JSON.stringify(this.currentUser)
                            })
                                .then(response => {
                                    if (response.status !== 200)
                                    {
                                        console.log("Something going wrong in FETCH" + response.status)
                                        return;
                                    }
                                    else
                                    {
                                        next();
                                    }
                                })
                        }
                        else
                        {
                            next("/");
                        }
                    }
                }
            },
            {
                path: '/basket',
                component: asyncComp("vue!js/Basket")
            },
            {
                path: '/about',
                component: asyncComp("vue!js/About")
            },
            {
                path: '/',
                component: asyncComp("vue!js/Home"),
            },
            {
                path: '/Hello',
                component: asyncComp("vue!js/Hello"),
                beforeEnter:(from,to,next) => {
                    if(localStorage.getItem('session') == null)
                    {
                        next("/");
                    }
                    else
                    {
                        if(localStorage.getItem('session'))
                        {
                            this.currentUser = JSON.parse(localStorage.getItem('session'));
                            fetch('/authentication/role',{
                                method:'POST',
                                headers:{'Content-Type': 'application/json;charset=utf-8'},
                                body: JSON.stringify(this.currentUser)
                            })
                                .then(response => {
                                    if (response.status !== 200)
                                    {
                                        console.log("Something going wrong in FETCH" + response.status)
                                        return;
                                    }
                                    else
                                    {
                                        next();
                                    }
                                })
                        }
                        else
                        {
                            next("/");
                        }
                    }
                }
            },
        ];

        return new VueRouter({
            routes: routes
        });
    })
</script>