<template>
  <header class="navbar">
    <div style="margin-left: 0.55rem"><i class="iconfont">&#xe679;</i></div>
    <div>首日免单，更有机会获现金红包。</div>
    <div>
    立即认证
    </div>
    <!--<flexbox :gutter="0">-->
      <!--<flexbox-item :span="1/10" :order="1" style="text-align: right">-->
        <!--<i class="iconfont">&#xe679;</i>-->
      <!--</flexbox-item>-->
      <!--<flexbox-item :order="2" style="margin-left: 0.25rem">-->
        <!--<label class="title">首日免单，更有机会获现金红包。</label>-->
      <!--</flexbox-item>-->
      <!--<flexbox-item :span="1/5" :order="3">-->
        <!--<span class="btn-r">立即认证</span>-->
      <!--</flexbox-item>-->
    <!--</flexbox>-->
  </header>
</template>

<script>
  import { Flexbox, FlexboxItem, XButton } from 'vux'
  export default {
    name: 'PromptBar',
    components: {
      Flexbox,
      FlexboxItem,
      XButton
    }
  }
</script>

<style scoped lang="less">
  .navbar {
    height: .9rem;
    line-height: .9rem;
    border-bottom: solid 0.01rem #ececec;
    position: relative;
    text-align: center;
  }
  .navbar div:nth-child(1) {
    position: absolute;
    left: 0;
  }
  .navbar div:nth-child(2) {
    font-size: .12rem;
    display: inline-block;
    left: 1.3rem;
  }
  .navbar div:nth-child(3) {
    position: absolute;
    right: 0.34rem;
    top: 0.24rem;
    display: inline-block;
    padding: 0 0.1rem;
    line-height: 0.4rem;
    color: #ff7e00;
    border: 1px solid #ff7e00;
    border-radius: 5px;
  }
  .btn-r {
    color: #ff7e00;
    border: solid 0.01rem #ff7e00;
    border-radius: 5px;
    font-size: 0.24rem;
    display: inline-block;
    padding: 0 0.1rem;
    line-height: 0.4rem;
    margin-right: 0.54rem;
  }
</style>

