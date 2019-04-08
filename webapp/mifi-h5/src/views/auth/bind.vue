<template>
  <div class="bind">
    <div class="item">
      <div class="log">
        <img src="../../assets/img/logo.png"/>
      </div>
    </div>
    <div class="phone-form item">
      <div class="row">
        <label>+86</label>
        <input type="tel" placeholder="请输入手机号" class="phone" maxlength="11"/>
      </div>
      <div class="row row-flex">
        <input type="tel" placeholder="请输入验证码" class="phone" maxlength="4"/>
        <div>
          <button type="button" class="btn btn-block" @click="sendCode" style="height: 0.73rem;"
                  :disabled="start"
                  :class="start ? 'btn-disabled' : ''"
                  :style="{}">{{tmpStr}}</button>
        </div>
      </div>
    </div>
    <div class="phone-bottom">
      <button class="btn btn-block" @click="bindPhone">绑定手机</button>
      <p>
        点击绑定手机即同意
        <a href="">《租用服务协议》</a>
        <a href="">《隐私协议》</a>
      </p>
    </div>
    <div v-transfer-dom>
      <x-dialog v-model="showRed"  :dialog-style="{'max-width': '6.34rem', width: '6.34rem', height: '5.86rem', 'background-color': '#d84e43'}">
        <div class="showRed-title">
          绑定手机成功
        </div>
        <div class="showRed-content">
          <div :style ="note" class="coupon">
            <div>
              <span style="font-size: 0.5rem">¥</span>
              <span style="font-size: 1.41rem; font-family: Impact;">5</span>
              <span style="font-size: 0.39rem;writing-mode: vertical-rl;text-align: center;letter-spacing: -1px;">优惠券</span>
            </div>
            <div style="text-align: left">
              <p>只限</p>
              <p style="margin-top: 0.14rem">抵用押金</p>
            </div>
          </div>
          <div class="purse" style="">
            <div>请在“我的钱包-优惠券”</div>
            <div>
              <a>查看礼券</a>
            </div>
          </div>
        </div>
        <div class="showRed-bottom" @click="bindOk">确定</div>
      </x-dialog>
    </div>
  </div>
</template>

<script>
  import { XDialog, TransferDomDirective as TransferDom } from 'vux'
  export default {
    name: 'bind',
    directives: {
      TransferDom
    },
    components: {
      XDialog
    },
    data () {
      return {
        second: 60,
        timer: null,
        start: false,
        runSecond: this.second,
        tmpStr: '获取验证码',
        storageKey: 'sendCode',
        runStr: {
          type: String,
          default: '{%s}秒后重新获取'
        },
        showRed: false,
        note: {
          backgroundImage: 'url(' + require('../../assets/img/red-dialog.png') + ')',
          backgroundRepeat: 'no-repeat',
          backgroundSize: '100%',
          height: '1.8rem'
        }
      }
    },
    methods: {
      run (lastSecond) {
        let second = lastSecond || this.runSecond
        if (this.storageKey) {
          const runSecond = new Date().getTime() + (second * 1000)
          window.sessionStorage.setItem(this.storageKey, runSecond)
        }
        if (!lastSecond) {
          this.tmpStr = this.getStr(second)
        }
        this.timer = setInterval(() => {
          second -= 1
          this.tmpStr = this.getStr(second)
          if (second <= 0) {
            this.stop()
          }
        }, 1000)
      },
      stop () {
        this.tmpStr = '重新获取验证码'
        this.start = false
        this.$emit('input', false)
        clearInterval(this.timer)
      },
      getStr (second) {
        return this.runStr.default.replace(/\{([^{]*?)%s(.*?)\}/g, second)
      },
      sendCode () {
        this.$vux.loading.show()
        console.log(this.$vux)
        setTimeout(() => {
          this.start = true
          this.$vux.loading.hide()
          this.run(this.second)
        }, 1000)
      },
      bindPhone () {
        // 没礼卷活动时，显示绑定手机成功，确定后跳至押金界面
        // this.$vux.alert.show({
        //   title: '绑定成功',
        //   content: this.$t('请使用'),
        //   onShow () {
        //     console.log('Plugin: I\'m showing')
        //   },
        //   onHide () {
        //     console.log('Plugin: I\'m hiding now')
        //   }
        // })
        this.showRed = true
      },
      bindOk () {
        this.showRed = false
        // 增加确定键，点击确定后直接跳至押金界面
      }
    },
    created () {
      const lastSecond = parseInt((window.sessionStorage.getItem(this.storageKey) - new Date()
        .getTime()) / 1000, 0)
      if (lastSecond > 0 && this.storageKey) {
        this.tmpStr = this.getStr(lastSecond)
        this.start = true
        this.run(lastSecond)
      } else if (this.initStr) {
        this.tmpStr = this.initStr
      }
    },
    destroyed () {
      if (this.storageKey) this.stop()
    }
  }
</script>

<style scoped lang="less">
  .bind {
    padding: 0 0.77rem;
    .item {
      margin-top: 1.06rem;
    }
    .log {
      text-align: center;
    }
    img {
      height: 0.62rem;
      width: 2.23rem;
    }
    .phone-form {
      border: 1px solid #e5e5e5;
      border-radius: 5px;
      .row {
        height: 0.9rem;
        line-height: 0.9rem;
        border-bottom: 1px solid #e4e4e4;
        overflow: hidden;
        padding: 0 0.07rem 0 0;
        label {
          padding: 0 0.38rem 0 0.45rem;
          border-right: 1px solid #e4e4e4;
        }
        .phone {
          outline: none;
          border: none;
          width: 68%;
          font-size: 0.28rem;
          padding-left: 0.45rem;
        }
      }
    }
  }
  .item div:nth-child(1) {
    border-bottom: none;
  }
  .row-flex {
    display: flex;
    border-bottom: none !important;
  }
  .btn-disabled {
    background-color: #ccc;
    color: #f0f0f0;
    pointer-events: none;
  }
  .phone-bottom {
    margin-top: 0.4rem;
    p {
      margin-top: 0.28rem;
    }
  }
  .purse {
    position: relative;
    height: 100%;
    line-height: 0.7rem;
  }
  .purse div:nth-child(1) {
    position: absolute;
    left: 0.63rem;
  }
  .purse div:nth-child(2) {
    position: absolute;
    right: 0.54rem;
    color: #ff7e00;
    a {
      border-bottom: 1px solid #ff7e00;
    }
  }
  .showRed-title {
    height: 1.2rem;
    font-size: 0.47rem;
    color: #ffffff;
    padding-top: 0.69rem;
  }
  .showRed-content > div:nth-child(1) {
    margin-left: 0.55rem;
    margin-right: 0.55rem
  }
  .showRed-content > div:nth-child(2) {
    height: 0.7rem;
    background-color: #f5f5f5;
    margin-left: 0.52rem;
    margin-right: 0.52rem
  }
  .showRed-bottom {
    height: 1.06rem;
    line-height: 1.06rem;
    font-size: 0.37rem;
    color: #ffffff;
    width: 5.62rem;
    margin-left: 0.35rem;
    border-top: 1px solid #bb2014;
    margin-top: 0.5rem
  }

  .coupon {
    position: relative;
  }
  .coupon div:nth-child(1) {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    width: 3.38rem;
    /*height: 1.44rem;*/
  }
  .coupon div:nth-child(1):after{
    content: " ";
    width: 1px;
    height: 1.44rem;
    position: absolute;
    top: 0.4rem;
    right: 0;
    border-right: 1px dashed #6d6d6d;
  }
  .coupon div:nth-child(2) {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 0.38rem;
  }
</style>
