<template>
  <div id="app" class="max-width">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  created () {
    this.$http.get('/config').then((res) => {
      if (res.data.success) {
        const wechat = this.$wechat
        wechat.config(res.data.data)
        wechat.ready(() => {
        })
      }
    })
  }
}
</script>

<style lang="less">
@import '~vux/src/styles/reset.less';
@import '~vux/src/styles/1px.less';
@import './styles/index.less';

body {
  background-color: #ffffff;
}
</style>
