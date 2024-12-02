<template>
  <!-- <el-container class="index-con">
    <el-header class="index-header">
      <navcon></navcon>
    </el-header>
    <el-container class="index-con">
      <el-aside :class="showclass">
        <leftnav></leftnav>
      </el-aside>
      <el-container class="main-con">
        <el-main clss="index-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container> -->

  <el-container  class="index-con">
    <el-aside :class="showclass">
      <leftnav v-if="type == 1"></leftnav>
      <leftnav2 v-if="type == 2"></leftnav2>
      <leftnav3 v-if="type == 3"></leftnav3>

    </el-aside>
   
    
    
    <el-container class="main-con">
      <el-header class="index-header">
        <navcon></navcon>
      </el-header>
      <el-main clss="index-main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
// 导入组件
import navcon from '../components/navcon.vue'
import leftnav from '../components/leftnav.vue'
import leftnav2 from '../components/leftnav2.vue'
import leftnav3 from '../components/leftnav3.vue'
import { getCookie } from '../utils/util'
export default {
  name: 'index',
  data() {
    return {
      showclass: 'asideshow',
      showtype: false,
      type:""
    }
  },
  // 注册组件
  components: {
    navcon,
    leftnav,
    leftnav2,
    leftnav3
  },
  methods: {},
  created() {
    console.log(getCookie("type"));
    this.type=getCookie("type")
    // 监听
    this.$root.Bus.$on('toggle', value => {
      if (value) {
        this.showclass = 'asideshow'
      } else {
        setTimeout(() => {
          this.showclass = 'aside'
        }, 300)
      }
    })
  },
  beforeUpdate() {},
  // 挂载前状态(里面是操作)
  beforeMount() {
    // 弹出登录成功
    this.$message({
      message: 'Login Successful',
      type: 'success'
    })
  }
}
</script>
<style >
.index-con {
  background-image: url("../../project/static/微信图片_20230326162957.jpg");
  height: 100%;
  width: 100%;
  box-sizing: border-box;
}

.aside {
  opacity: 0.99;
  width: 64px !important;
  height: 100%;
  background-color: #334157;
  margin: 0px;
}
.asideshow {
  opacity: 0.9;
  width: 240px !important;
  height: 100%;
  background-color: #334157;
  margin: 0px;
}
.index-header,
.index-main {
  opacity: 0.99;
  padding: 0px;
  border-left: 2px solid #333;
}
</style>
