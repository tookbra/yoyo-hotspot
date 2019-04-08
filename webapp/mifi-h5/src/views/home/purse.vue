<template>
  <div class="weui-panel weui-panel_access">
    <div class="weui-panel-hd">
      <div>
        <p>余额</p>
        <p><label class="label-red">{{ balance }}</label>元（其中充值余额{{ rechargeBalance }}元）</p>
      </div>
      <div>
        <button class="weui-btn_default btn-recharge" @click="recharge">充值</button>
      </div>
    </div>
    <div class="weui-panel-hd">
      <div>
        <p>优惠券</p>
        <p><label class="label-red">{{ coupon }}</label>张</p>
      </div>
      <div class="chevron">
        <font-icon id="icon-chevron-copy-copy" @click="myCoupon"></font-icon>
      </div>
    </div>
    <div class="weui-panel-hd">
      <div>
        <p>押金</p>
        <p><label class="label-red">{{ deposit }}元</label>（其中充值余额{{ rechargeBalance }}元）</p>
      </div>
      <div>
        <button class="weui-btn_default btn-recharge" @click="payDeposit">交押金</button>
      </div>
    </div>
    <div class="weui-panel-hd">
      <div>
        <p>我的订单</p>
        <p>暂无订单</p>
      </div>
      <div class="chevron">
        <font-icon id="icon-chevron-copy-copy" @click="myOrder"></font-icon>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        balance: 0,
        rechargeBalance: 0,
        coupon: 0,
        deposit: 0
      }
    },
    methods: {
      recharge () {

      },
      payDeposit () {

      },
      myOrder () {

      },
      myCoupon () {

      }
    },
    beforeCreate () {
      document.querySelector('body').setAttribute('style', 'background:#f9f9f9')
    },
    beforeDestroy () {
      document.querySelector('body').setAttribute('style', '')
    }
  }
</script>

<style scoped lang="less">
  .weui-panel {
    background-color: #FFFFFF;
    margin-top: 0.38rem;
    position: relative;
    overflow: hidden;
  }
  .weui-panel:before {
    content: " ";
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    height: 1px;
    border-top: 1px solid #E5E5E5;
    color: #E5E5E5;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
  }
  .weui-panel:after {
    content: " ";
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    height: 1px;
    border-top: 1px solid #E5E5E5;
    color: #E5E5E5;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
  }
  .weui-panel-hd {
    color: #999999;
    font-size: 13px;
    position: relative;
    height: 2.15rem;
  }
  .weui-panel-hd:after {
    content: " ";
    position: absolute;
    left: 0;
    bottom: 0;
    right: 0;
    height: 1px;
    border-bottom: 1px solid #E5E5E5;
    color: #E5E5E5;
    -webkit-transform-origin: 0 100%;
    transform-origin: 0 100%;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
    left: 15px;
  }
  .weui-panel-hd div:nth-child(1) {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    left: 0.45rem;

  }
  .weui-panel-hd div:nth-child(2) {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 0.5rem;
  }
  .weui-panel-hd div p:nth-child(1) {
    font-size: 0.38rem;color: #000;line-height: 0.7rem;
  }
  .weui-panel-hd div p:nth-child(2) {
    font-size: 0.28rem;
  }
  .btn-recharge {
    height: 0.71rem;
    width: 2.05rem;
    line-height: 0.71rem;
    border-radius: 25px;
    border-width: 0;
  }
  .label-red {
    color: #ff2a2a
  }
  .chevron {
    right: 0.83rem !important;
  }
</style>
