(function () {
    'use strict';

    define(["Vue","vue!js/Router","vue!js/App"],
        function (Vue, Router) {
            new Vue({
                router: Router,
                el: '#app',
                template: '<App/>',
                data() {
                    return {

                    };
                },

            });
        }
    );
})();