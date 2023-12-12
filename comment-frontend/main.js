import Vue from 'vue'
import App from './App.vue'
import '@/assets/css/global.css'
import './mock/mock.js'


import axios from 'axios'

Vue.prototype.$axios = axios;
Vue.prototype.$httpUrl = 'http://localhost:8082/';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//这里的Vue要做修改，等会就知道了
Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app')
