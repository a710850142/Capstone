/**
 * 系统管理  预约医生
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Make an appointment with a doctor
      </el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">

      <el-form-item label="Search：">
        <el-input size="small" v-model="formInline.id" placeholder="Enter your appointments ID"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="Enter doctor's name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">Search</el-button>
        
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="Loading" style="width: 100%;opacity: 0.8;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>

      <el-table-column  align="center" sortable prop="id" label="Doctor's ID" width="100"> 
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="Doctor's Name" width="120">
      </el-table-column>

      <el-table-column align="center" sortable prop="phone" label="Doctor's phone number" width="120"> 
      </el-table-column>
      
      <el-table-column align="center" sortable prop="url" label="Doctor Profile" width="220"> 
      </el-table-column>

      <el-table-column align="center" sortable prop="yuyu" label="Remaining appointments for morning" width="120"> 
      </el-table-column>

      <el-table-column  align="center" label="Morning Appointment" min-width="120">
        <template slot-scope="scope">
          <el-button size="mini"  @click="deleteUser(scope.$index, scope.row)">Appointment</el-button>
        </template>
      </el-table-column>


      <el-table-column align="center" sortable prop="yuyu2" label="Remaining appointments for the afternoon" width="120"> 
      </el-table-column>

      <el-table-column  align="center" label="Afternoon Appointment" min-width="120">
        <template slot-scope="scope">
          <el-button size="mini"  @click="deleteUser1(scope.$index, scope.row)">Appointment</el-button>
        </template>
      </el-table-column>



    </el-table>
    <!-- 分页组件
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination> -->
    <!-- 编辑界面 -->
    <el-dialog title="View" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="Appointments ID" prop="id">
          <el-input size="small" disabled v-model="editForm.id" auto-complete="off" placeholder="ID"></el-input>
        </el-form-item>
        <el-form-item label="User ID" prop="userid">
          <el-input size="small" disabled v-model="editForm.userid" auto-complete="off" placeholder="Please Enter"></el-input>
        </el-form-item>
        <el-form-item label="Doctor ID" prop="doctid">
          <el-input size="small" disabled v-model="editForm.doctid" auto-complete="off" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="Appointments Time" prop="apppointtime">
          <el-input size="small" disabled v-model="editForm.apppointtime" auto-complete="off" placeholder="">
            
          </el-input>
        </el-form-item>
       
        <el-form-item label="Status" prop="status">
          <el-select size="small" v-model="editForm.status" disabled placeholder="Please Select" class="userRole">
            <el-option v-for="parm in statusxz" :key="parm.label" :label="parm.value"
                     :value="parm.label"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Axios from 'axios'
import { appointList, appointDelete ,yuyulist,appSave} from '../../api/payMG'
import Pagination from '../../components/Pagination'
import {getCookie, timestampToTime} from '../../../src/utils/util'

export default {
  data() {

    return {
      statusxz: [{
        value: 'Success',
        label: 1
      }, {
        value: 'Cancel',
        label: 2
      },
      {
        value: 'Expired',
        label: 3
      }],

      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加',
      editForm: {
        
        id:'',
        name:'',
        userid:'',
        classtype:'',
        status:'',
      },
      // rules表单验证
      rules: {
       
       
      },

      app:{
        userid:getCookie("uid"),
        doctid:'',
        sxwu:''
             
      },

      formInline: {
        page: 1,
        limit: 10,
        start:0,
        id:'',
        name:'',
        phone:'',
        url:'',
       yuyu:''
      },
      // 删除
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  // 注册组件
  components: {
    Pagination
  },
  /**
   * 数据发生改变
   */

  watch: {},

  /**
   * 创建完毕
   */

  created() {
     this.getdata(this.formInline)
   
  },

  /**
   * 里面的方法只有被调用才会执行
   */

  methods: {

    

    // 获取数据方法
    getdata(parameter) {
      this.loading = true
 
      yuyulist(parameter)
        .then(res => {
          console.log(JSON.stringify(res))
          this.loading = false
          if (res.success == false) {
            this.$message({
              type: 'info',
              message: res.msg
            })
          } else {
            this.listData = res.data
            // 分页赋值
            this.pageparm.currentPage = this.formInline.page
            this.pageparm.pageSize = this.formInline.limit
            this.pageparm.total = res.count
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('Failed to load the menu, please try again later!')
        })
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    // 搜索事件
    search() {
      this.getdata(this.formInline)
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = 'Revise'
        this.editForm.id = row.id
        this.editForm.userid = row.userid
        this.editForm.doctid = row.doctid
        this.editForm.apppointtime =timestampToTime(row.apppointtime)
        this.editForm.status = row.status
      } else {
        this.title = 'Add'
        this.editForm.id = ''
        this.editForm.userid = ''
        this.editForm.name = ''
        this.editForm.classtype = ''
        this.editForm.status = ''
      }
    },
   
    deleteUser1(index, row) {
      this.app.doctid=row.id
      this.app.sxwu='Afternoon'
      console.log("----------");
      console.log(row.id);
      console.log(this.app);
      if(row.yuyu2 <= 0)
      {
        console.log("---，mah----");
        this.$message.error('Appointment failed, appointment is full')
      }else{
      this.$confirm('Are you sure you want to make an appointment?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
         
          appSave(this.app)
            .then(res => {
              if (res.sta==1) {

                this.$message.success({
                  message: 'Appointment Successful!'})

                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('Appointment failed, please try again later！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Canceled'
          })
        })
      }
    },

    deleteUser(index, row) {
      this.app.doctid=row.id
      this.app.sxwu='Morning'
      console.log("----aaa----");
      console.log(row.id);
      console.log(this.app);
      if(row.yuyu <= 0)
      {
        console.log("---，mah----");
        this.$message.error('The appointment failed, the appointment is full!')
      }else{
      this.$confirm('Are you sure you want to make an appointment?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
         
          appSave(this.app)
            .then(res => {
              if (res.sta==1) {

                this.$message.success({
                  message: 'Appointment Successful!'})

                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('Appointment failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: ''
          })
        })
      }
    },


    deleteUser(index, row) {
      this.app.doctid=row.id
      this.app.sxwu='Morning'
      console.log("----------");
      console.log(row.id);
      console.log(this.app);
      if(row.yuyu <= 0)
      {
        console.log("---，mah----");
        this.$message.error('Appointment failed, please try again later！')
      }else{
      this.$confirm('Are you sure you want to make an appointment?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
         
          appSave(this.app)
            .then(res => {
              if (res.sta==1) {

                this.$message.success({
                  message: 'Appointment Successful!'})

                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('Deletion failed, please try again later')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Undeleted!'
          })
        })
      }
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    }
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>

 