<template>
  <div>
    <prompt-bar></prompt-bar>
    <flexbox class="product" orient="vertical" :gutter="20">
      <flexbox-item v-for="item in productItem" :key="item.id">
        <div class="product-item">
          <div class="ribbon" v-if="item.gifted" >
            <label>{{ item.giftContent }}</label>
          </div>
          <p class="title">
            {{ item.title }}
            <span v-if="item.unlimited">无限流量</span>
          </p>
          <p class="content">{{ item.content }}</p>
        </div>
      </flexbox-item>
    </flexbox>
    <flexbox style="margin-top: 0.4rem">
      <flexbox-item>
        <flexbox>
          <flexbox-item :span="1/5" :order="1">
            <div class="icon-language">
              <FlatIcon url="test1" id="icon-en"></FlatIcon>
            </div>
          </flexbox-item>
          <flexbox-item :order="2">
            <x-button class="btn_return" style="background: #ffc600;">还</x-button>
          </flexbox-item>
          <flexbox-item :span="1/5" :order="3" style="text-align: right">
            <div class="icon-my">
              <FlatIcon url="test1" id="icon-purse"></FlatIcon>
              <FlatIcon url="test1" id="icon-Earphones-microphone" style="margin-top: 0.3rem"></FlatIcon>
            </div>
          </flexbox-item>
        </flexbox>
      </flexbox-item>
    </flexbox>
    <divider class="default-font" style="margin: 0.64rem">上海大厦</divider>
  </div>
</template>

<script>
  import { Divider, Flexbox, FlexboxItem, XButton } from 'vux'
  import PromptBar from '../../components/PromptBar'
  export default {
    components: {
      Divider,
      Flexbox,
      FlexboxItem,
      XButton,
      PromptBar
    },
    data () {
      return {
        productItem: [
          {
            title: '小时借',
            content: '1.2元/小时，一天12元封顶',
            unlimited: false,
            gifted: true,
            giftContent: '送2G流量'
          },
          {
            title: '包天借',
            content: '8元/天',
            unlimited: true,
            gifted: false
          },
          {
            title: '包月借',
            content: '49元/月',
            unlimited: true,
            gifted: false
          }
        ]
      }
    }
  }
</script>

<style scoped lang="less" >
  .product {
    margin-top: 0.42rem;
    margin-left: 0.66rem;
    margin-right: 0.66rem;
    width: 6.17rem;
  }
  .product-item:active {
    background-color: #ffc600;
  }
  .product-item {
    border: none;
    -webkit-tap-highlight-color: rgba(0,0,0,0);
    border-radius: 0.1rem;
    background-color: #efefef;
    text-align: center;
    height: 1.4rem;
    overflow: hidden;
    white-space: nowrap;
    position: relative;
    width: 6.17rem;

    .ribbon {
      z-index: 1000;
      background-color: #e20202;
      white-space: nowrap;
      /* top left corner */
      position: absolute;
      left: -1.5rem;
      top: 20px;
      /* 45 deg ccw rotation */
      -webkit-transform: rotate(-45deg);
      -moz-transform: rotate(-45deg);
      -ms-transform: rotate(-45deg);
      -o-transform: rotate(-45deg);
      transform: rotate(-45deg);
      font-size: 0.1rem;
      color: #ffffff;
      width: 3.5rem;
      label {
        margin-left: 25px;
      }
    }
    .title {
      font-size: 0.34rem;
      line-height: 0.53rem;
      padding-top: 0.3rem;
      position: relative;
      span {
        border-radius: 0.1rem;
        background-color: #ffc600;
        line-height: 0.3rem;
        font-size: 0.13rem;
        padding: 0 0.1rem 0rem 0.1rem;
        position: absolute;
        margin-left: 0.08rem;
        margin-top: 0.15rem;
      }
    }
    .content {
      font-size: 0.24rem;
      line-height: 0.44rem;
    }
  }
  .btn_return {
    border-radius: 1.1rem;
    width: 2.53rem;
  }
  .default-font{
    font-size: 0.3rem;
  }
  .icon-language {
    margin-left: 0.3rem;
  }
  .icon-my {
    margin-right: 0.27rem;
    display: inline-block;
  }
</style>
