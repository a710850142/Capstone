<template>
  <div class="login-wrap">
    <el-form
      label-position="left"
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="0px"
      class="demo-ruleForm login-container"
    >
      <h3 class="title">Pet Hospital</h3>
      <el-form-item prop="username">
        <el-input
          type="text"
          v-model="ruleForm.username"
          auto-complete="off"
          placeholder="Account"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="ruleForm.password"
          auto-complete="off"
          placeholder="Password"
        ></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="code">
            <el-input
              type="text"
              v-model="ruleForm.code"
              auto-complete="off"
              placeholder="Graphic verification code"
              @keyup.enter.native="submitForm('ruleForm')"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" class="code-box">
          <img
            :src="'/qin/product/account/captcha.jpg?uuid=' + ruleForm.codeimg"
            alt=""
            class="codeimg"
            @click="getcode()"
          />
          <!-- <img :src="ruleForm.codeimg" alt="" class="codeimg" @click="getcode()"> -->
        </el-col>
      </el-row>
      <el-checkbox class="remember" v-model="rememberpwd">Remember Password</el-checkbox>

      <a class="aaa" @click="getzhuce()">Register an account?</a>
      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%"
          @click="submitForm('ruleForm')"
          :loading="logining"
          >Log In</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script type="text/ecmascript-6">
import { login } from "../api/userMG";
import { setCookie, getCookie, delCookie } from "../utils/util";
import md5 from "js-md5";
import { loginreq } from "../api/axiosFun";
import { getUUID } from "../utils/util";
import Axios from "axios";

export default {
  name: "login",
  data() {
    return {
      //定义loading默认为false
      logining: false,
      // 记住密码
      rememberpwd: false,
      ruleForm: {
        //username和password默认为空
        username: "",
        password: "",
        code: "",
        randomStr: "",
        codeimg: "",
      },
      //rules前端验证
      rules: {
        username: [{ required: true, message: "Please enter your account number", trigger: "blur" }],
        password: [{ required: true, message: "Please enter your password", trigger: "blur" }],
        code: [{ required: true, message: "Please enter the verification code", trigger: "blur" }],
      },
    };
  },
  // 创建完毕状态(里面是操作)
  created() {
    this.ruleForm.codeimg = getUUID();
    this.getuserpwd();
  },
  // 里面的函数只有调用才会执行
  methods: {
    // 获取用户名密码
    getuserpwd() {
      if (getCookie("user") != "" && getCookie("pwd") != "") {
        this.ruleForm.username = getCookie("user");
        this.ruleForm.password = getCookie("pwd");
        this.rememberpwd = true;
      }
    },

    // 获取验证码
    getcode() {
      this.ruleForm.codeimg = getUUID();
    },

    // 跳转注册
    getzhuce() {
      this.$router.push({ path: "/zhuce" });
    },

    //获取info列表
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.logining = true;
          // 测试通道，不为空直接登录
          loginreq("http://localhost:8080/qin/product/account/login", {
            username: this.ruleForm.username,
            password: this.ruleForm.password,
            captcha: this.ruleForm.code,
            uuid: this.ruleForm.codeimg,
          })
            .then((result) => {
              console.log("result", result);
              if (result && result.code === 0) {
                setTimeout(() => {
                  this.logining = false;
                  this.$store.commit("login", "true");
                  setCookie("user", this.ruleForm.username, 7);
                  setCookie("type", result.type,7);
                  setCookie("userid", result.userid,7);
                  setCookie("uid",result.uid,7);
                  setCookie("guuid",result.guuid,7);
                  console.log(getCookie("userid"));

                  console.log(getCookie("-------------"));
                  this.$router.push({ path: "/system/xx" });
                }, 1000);
              } else {
                this.logining = false;
                this.$message.error(result.msg);
                this.ruleForm.codeimg = getUUID();
              }
            })
            .catch((err) => {});

          
        } else {
          // 获取图形验证码
          this.getcode();
          this.$message.error("Please enter your username and password!");
          this.logining = false;
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.aaa {
  float: right;
}
.login-wrap {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-top: 10%;
  background-image: url("../../project/static/3z1frbn3cx43z1frbn3cx4.jpg");
  /* background-color: #112346; */
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}
.login-container {
  opacity: 0.95;
  border-radius: 10px;
  margin: 0px auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}
.title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
.remember {
  margin: 0px 0px 35px 0px;
}
.code-box {
  text-align: right;
}
.codeimg {
  height: 40px;
}
</style>