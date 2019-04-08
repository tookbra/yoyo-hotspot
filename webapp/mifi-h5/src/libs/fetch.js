import axios from 'axios'
import qs from 'qs'

const baseUrl = 'http://127.0.0.1:10004/wechat'
const ajax = axios.create({
  baseURL: baseUrl,
  timeout: 30000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
  }
})

const cancel = {}
const promiseArr = {}
const CancelToken = axios.CancelToken
// 请求拦截器
axios.interceptors.request.use((config) => {
  const conf = config
  if (
    config.method === 'post' ||
    config.method === 'put' ||
    config.method === 'delete'
  ) {
    // 序列化
    conf.data = qs.stringify(config.data)
  }
  if (localStorage.token) {
    conf.headers.Authorization = localStorage.token
  }
  // 发起请求时，取消掉当前正在进行的相同请求
  if (promiseArr[conf.url]) {
    promiseArr[conf.url]('操作取消')
    promiseArr[conf.url] = cancel
  } else {
    promiseArr[conf.url] = cancel
  }
  return conf
}, (error) => {
  const err = Promise.reject(error)
  return err
})

// 响应拦截器即异常处理
axios.interceptors.response.use((response) => {
  const res = response
  return res
}, (error) => {
  const err = error
  if (err && err.response) {
    switch (error.response.status) {
      case 400:
        err.message = '错误请求'
        break
      case 401:
        err.message = '未授权，请重新登录'
        break
      case 403:
        err.message = '拒绝访问'
        break
      case 404:
        err.message = '请求错误,未找到该资源'
        break
      case 405:
        err.message = '请求方法未允许'
        break
      case 408:
        err.message = '请求超时'
        break
      case 500:
        err.message = '服务器端出错'
        break
      case 501:
        err.message = '网络未实现'
        break
      case 502:
        err.message = '网络错误'
        break
      case 503:
        err.message = '服务不可用'
        break
      case 504:
        err.message = '网络超时'
        break
      case 505:
        err.message = 'http版本不支持该请求'
        break
      default:
        err.message = `连接错误${error.response.status}`
    }
  } else {
    err.message = '连接到服务器失败'
  }
  return Promise.resolve(err.response)
})

// 对axios的实例重新封装成一个plugin ,方便 Vue.use(xxxx)
// export default {
//   install: (Vue) => { Object.defineProperty(Vue.prototype, '$http', { value: ajax }); },
// };

export default ajax
