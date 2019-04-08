<template>
  <div style="margin: 0 0.47rem 0 0.47rem">
    <div style="font-size: 0.36rem; margin-top: 1rem">
      充值金额
    </div>
    <div class="box">
      <div v-for="(item,index) in money" :class="{on:index == num}" @click="selectMoney(index)">
        {{item}}
      </div>
      <!--<div>-->
        <!--<span class="recharge-item">充150</span>-->
        <!--<span class="recharge-item" style="margin-left: 0.25rem">充100</span>-->
      <!--</div>-->
      <!--<div style="margin-top: 0.5rem">-->
        <!--<span class="recharge-item">充500</span>-->
        <!--<span class="recharge-item" style="margin-left: 0.25rem">充20</span>-->
      <!--</div>-->
    </div>
    <div style="text-align: center; margin-top: 1.4rem">
      <div style="margin: 0 0.3rem 0 0.3rem;">
        <button class="btn btn-block" @click="recharge">立即充值</button>
      </div>
      <p style="margin-top: 0.28rem;">
        点击立即充值即同意
        <a href="">《租用服务协议》</a>
      </p>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        money: ['充150', '充100', '充50', '充20'],
        num: 9
      }
    },
    methods: {
      selectMoney (index) {
        this.num = index
      },
      recharge () {

      }
    }
  }
</script>

<style scoped lang="less">
  .recharge-item {
    width: 3.05rem;
    height: 1.4rem;
    background: #efefef;
    border-radius: 5px;
    display:-moz-inline-box;
    display:inline-block;
    line-height: 1.4rem;
    text-align: center;
    font-size: 0.34rem;
  }
  .box{
    width: 100%;
    text-align: center;
  }
  .box div{
    display: inline-block;
    width:3rem;
    line-height: 1.4rem;
    background-color: #efefef;
    border-radius: 5px;
    text-align: center;
    margin-top: 0.5rem;
  }
  .box div:nth-child(1),.box div:nth-child(3){
    margin-right: 0.3rem;
  }
  .box div.on{
    background-color:#ffc600;
  }
</style>

