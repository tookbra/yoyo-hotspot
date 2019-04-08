import Vue from 'vue'
import VueRouter from 'vue-router'
import Cookies from 'js-cookie'
import store from '@/store'
import axios from '@/libs/fetch.js'
import util from '@/libs/util.js'
import { routers } from './router'

Vue.use(VueRouter)

const RouterConfig = {
  // mode: 'hash',
  // linkActiveClass: 'active',
  routes: routers
}

export const router = new VueRouter(RouterConfig)

// router.beforeEach((to, from, next) => {
//   // 是否需要授权
//   if (to.meta && to.meta.auth) {
//     if (store.getters.device.isWeixin) {
//       const token = JSON.parse(localStorage.getItem('token'))
//       if (!token) {
//         const authCode = util.getUrlParam('code')
//         if (typeof authCode === 'undefined' || !authCode) {
//           axios.get('/auth').then((res) => {
//             const data = res.data
//             if (data.success) {
//               window.location.href = data.data
//             }
//           })
//         } else {
//           axios.get('/auth/token', {params: {code: authCode}}).then((res) => {
//             const data = res.data
//             if (data.success) {
//               localStorage.setItem('wxCode', authCode)
//               localStorage.setItem('token', JSON.stringify(data.data))
//               next({ path: '/' })
//             }
//           })
//         }
//       }
//     }
//   } else {
//     next()
//   }
// })

export default router
