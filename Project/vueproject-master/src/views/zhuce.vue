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
      <h3 class="title">Pet Hospital Registration</h3>
      <el-form-item prop="username">
        <el-input
          type="text"
          v-model="ruleForm.username"
          auto-complete="off"
          placeholder="New Account"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="ruleForm.password"
          auto-complete="off"
          placeholder="Enter Password"
        ></el-input>
      </el-form-item>
      <el-form-item prop="passwords">
        <el-input
          type="password"
          v-model="ruleForm.passwords"
          auto-complete="off"
          placeholder="Re-enter Password"
        ></el-input>
      </el-form-item>

    <el-form-item prop="typees">
      <el-select class="abca" v-model="ruleForm.typees" placeholder="Registration Type">
      <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
      </el-option>
      </el-select>
    </el-form-item>

    
      <el-row>
        <el-col :span="12">
          <el-form-item prop="code">
            <el-input
              type="text"
              v-model="ruleForm.code"
              auto-complete="off"
              placeholder="Graphic Verification Code"
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
      <!-- <el-checkbox class="remember" v-model="rememberpwd">记住密码</el-checkbox> -->

      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%"
          @click="submitForm('ruleForm')"
          :loading="logining"
          >Register</el-button
        >
      </el-form-item>

      <el-form-item ><a class="aaa" @click="getzhuce()">Back to Login</a></el-form-item>

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
        passwords: "",
        code: "",
        randomStr: "",
        codeimg: "",
        typees:"",
      },
      //rules前端验证
      rules: {
        username: [{ required: true, message: "Please enter your account number", trigger: "blur" }],
        password: [{ required: true, message: "Please enter your password", trigger: "blur" }],
        passwords: [{ required: true, message: "Please enter your password", trigger: "blur" }],
        typees: [{ required: true, message: "Please enter the registration type", trigger: 'change'}],
        code: [{ required: true, message: "Please enter the verification code", trigger: "blur" }],
      },

      options: [{
          value: '2',
          label: 'User'
        }, {
          value: '3',
          label: 'Doctor'
        },],
        value: ''

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

    // 跳转登录
    getzhuce() {
      this.$router.push({ path: "/login" });
    },

    //获取info列表
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.logining = true;
          if((this.ruleForm.password) == (this.ruleForm.passwords))
          {

            loginreq("http://localhost:8080/qin/product/account/zhuce", {
            username: this.ruleForm.username,
            password: this.ruleForm.password,
            captcha: this.ruleForm.code,
            uuid: this.ruleForm.codeimg,
            type: this.ruleForm.typees,

            })
            .then((result) => {
              console.log("result", result);
              if (result && result.code === 0) {
                setTimeout(() => {
                  this.logining = false;
                  //this.$store.commit("login", "true");
                  this.$message.success("Registration successful, please log in!");
                  this.$router.push({ path: "/login" });
                }, 1000);
              } else {
                this.logining = false;
                this.$message.error(result.msg);
                this.ruleForm.codeimg = getUUID();
              }
            })
            .catch((err) => {});


          }else
          {
            
            this.logining = false;
            this.$message.error("The password is different!!");
          }


        } else {
          // 获取图形验证码
          this.getcode();
          this.$message.error("Please Enter!!!");
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

.abca {
    width: 350px;
}


</style>