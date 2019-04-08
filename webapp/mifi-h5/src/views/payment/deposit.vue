<template>
  <flexbox orient="vertical" class="deposit">
    <flexbox-item class="prop">
        <img src="../../assets/img/shape_25.png" style="height: 0.23rem"/>
        您还未充值押金，请充值
    </flexbox-item>
    <flexbox-item class="pay-amount">
      <p>押金金额</p>
      <p>¥49</p>
      <p>押金随时退，安全速到账</p>
    </flexbox-item>
    <flexbox-item class="pay-style">
      <div>
        <img src="../../assets/img/wepay.png"/>
        微信支付
      </div>
      <div>
        <font-icon id="icon-success"></font-icon>
      </div>
    </flexbox-item>
    <flexbox-item style="margin-top: 0.5rem; color: #999999;">
      <ul>
        <li>租借随身WiFi需要充值押金</li>
        <li>归还WiFi后，可随时申请退还押金，预计到账时间为0-3个工作日</li>
      </ul>
    </flexbox-item>
    <flexbox-item style="margin-top: 1.09rem">
      <div style="margin: 0 0.45rem 0 0.45rem; width: auto">
        <x-button>确认支付</x-button>
      </div>
      <div class="protocol">
        点击确认支付即同意<a>《租用服务协议》</a>
      </div>

    </flexbox-item>
  </flexbox>
</template>

<script>
  import { Flexbox, FlexboxItem, XButton, Checklist } from 'vux'
  export default {
    components: {
      Flexbox,
      FlexboxItem,
      XButton,
      Checklist
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
  .deposit {
    margin: 0 0.33rem 0 0.33rem;
    width: auto;
    .prop {
      text-align: center;
      height: 0.93rem;
      line-height: 0.93rem;
      color:#ff7e00
    }
    .pay-amount {
      background: #ffffff;
      height: 3.93rem;
      text-align: center;
    }
    .pay-amount p:nth-child(1) {
      padding-top: 0.59rem;font-size: 0.28rem
    }
    .pay-amount p:nth-child(2) {
      padding-top: 0.29rem;font-size: 0.6rem
    }
    .pay-amount p:nth-child(3) {
      padding-top: 0.18rem;font-size: 0.28rem; color: #999999
    }
    .pay-style {
      margin-top: 0.45rem !important;
      height: 0.89rem;
      line-height: 0.89rem;
      background: #ffffff;
      position: relative;
    }
    .pay-style div:nth-child(1){
      position: absolute;
      height: 100%;
      left: 0.37rem;
      img {
        width: 0.49rem;
        height: 0.43rem;
        top: 12%;
        position: relative;
      }
    }
    .pay-style div:nth-child(2){
      position: absolute;
      right: 0.22rem;
      color: #ffc600;
    }
    ul li:before {color: #ffc600}
  }
  .protocol {
    text-align: center;
    margin-top: 0.28rem;
    color: #757576;
    a {
      color: #3f89e0;
    }
  }

</style>
