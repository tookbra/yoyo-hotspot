<template>
  <div class="circle" @click="goPage">
    <i v-if="id" class="iconfont" :class="id"></i>
  </div>
</template>

<script>
  export default {
    name: 'FlatIcon',
    props: {
      id: {
        type: String,
        default: ''
      },
      url: {
        type: String,
        required: true
      }
    },
    methods: {
      goPage () {
        this.$router.push(this.url)
      }
    }
  }
</script>

<style scoped lang="less">
  i {
    front-size: 2.1rem
  }
  .circle {
    z-index: 1000;
    background-color: #ffffff;
    border: 1px solid #ffffff;
    border-radius: 50%;
    height: 0.7rem;
    width: 0.7rem;
    box-shadow: 0 0 30px 0px rgba(119,118,118,0.33);
    -moz-box-shadow: 0 0 30px 0px rgba(119,118,118,0.33);
    -webkit-box-shadow: 0 0 30px 0px rgba(119,118,118,0.33);
    text-align: center;
    line-height: 0.69rem;
  }
</style>
