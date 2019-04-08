// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import store from './store'
// import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import { WechatPlugin, LoadingPlugin, AlertPlugin, ConfigPlugin } from 'vux'
import { router } from './router/index'
import App from './App'
import fetch from './libs/fetch'
import FlatIcon from './components/FloatIcon'
import SvgIcon from './components/svg-icon.vue'
import FontIcon from './components/font-icon.vue'
import './assets/js/base.js'
import './assets/css/common.css'
// import eruda from 'eruda'

Vue.use(fetch)
Vue.use(WechatPlugin)
Vue.use(LoadingPlugin)
Vue.use(AlertPlugin)
Vue.use(ConfigPlugin)
Vue.use(VueRouter)
Vue.component('FlatIcon', FlatIcon)
Vue.component('SvgIcon', SvgIcon)
Vue.component('FontIcon', FontIcon)

Vue.prototype.$http = fetch

var el = document.createElement('div')
document.body.appendChild(el)
// eruda.init({
//   container: el,
//   tool: ['console', 'elements', 'network', 'info']
// })

const userAgent = navigator.userAgent
const ios = navigator.userAgent.match(/OS (\d+)_(\d+)_?(\d+)?/)
const android = navigator.userAgent.match(/Android (\d+)\.(\d+)?/i)
const o = {
  isAndroid: !!userAgent.match(/android/gi),
  isAndroidGte6: !!(ios && parseInt(ios[1], 10) > 5),
  isIos: !!userAgent.match(/iphone|ipod/gi),
  isIosGte9: !!(ios && parseInt(ios[1], 10) > 8),
  isWeixin: !!userAgent.match(/MicroMessenger/gi),
  isChrome: !!userAgent.match(/chrome\/(\d+\.\d+)/gi)
}

store.dispatch('setDevice', o)
// FastClick.attach(document.body)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app-box')
