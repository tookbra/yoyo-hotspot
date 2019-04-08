export const appRouter = [
  {
    path: '/',
    name: 'index',
    component: () => import('@/views/home/index.vue'),
    meta: { title: '悠游随身wifi', keepAlive: true, auth: true }
  },
  {
    path: '/purse',
    name: 'purse',
    component: () => import('@/views/home/purse.vue'),
    meta: { title: '我的钱包', keepAlive: true, auth: true }
  },
  {
    path: '/auth',
    name: 'auth',
    component: () => import('@/views/auth/index.vue'),
    children: [{
      path: 'bind',
      name: 'bind',
      component: () => import('@/views/auth/bind.vue'),
      meta: {title: '绑定手机', keepAlive: true, auth: true}
    }]
  },
  {
    path: '/payment',
    name: 'payment',
    component: () => import('@/views/payment/index.vue'),
    children: [
      {
        path: 'deposit',
        name: 'deposit',
        component: () => import('@/views/payment/deposit.vue'),
        meta: {title: '押金充值', keepAlive: true, auth: true}
      },
      {
        path: 'balance',
        name: 'balance',
        component: () => import('@/views/payment/balance.vue'),
        meta: {title: '余额充值', keepAlive: true, auth: true}
      }
    ]
  }
]

export const routers = [
  ...appRouter
]
