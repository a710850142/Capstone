/**
 * 系统管理  预约管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Appointment Management</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">

      <el-form-item label="Search：">
        <el-input size="small" v-model="formInline.id" placeholder="Enter your appointment number"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="Enter doctor ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">Search</el-button>
        
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="Loading" style="width: 100%;opacity: 0.8;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>

      <el-table-column  align="center" sortable prop="id" label="Appointment ID" width="100"> 
      </el-table-column>
      <el-table-column align="center" sortable prop="userid" label="User ID" width="120">
      </el-table-column>

      <el-table-column align="center" sortable prop="doctid" label="Doctor ID" width="120"> 
      </el-table-column>
      
      <el-table-column align="center" sortable prop="apppointtime" label="Appointment Time" width="150">
        <template slot-scope="scope">
          <div>{{scope.row.apppointtime|timestampToTime}}</div>
        </template>
      </el-table-column>


      <el-table-column align="center"  sortable prop="status" label="Status" min-width="120">
        <template slot-scope="scope">
        <span v-if="scope.row.status== 1"><div >Success</div></span>
        <span v-if="scope.row.status== 2"><div >Cancel</div></span>
        <span v-if="scope.row.status== 3"><div >Expire</div></span>

      </template>
    </el-table-column>

    <el-table-column align="center" sortable prop="sxwu" label="Appointment Time" width="120"> 
      </el-table-column>

    <el-table-column align="center" sortable prop="createTime" label="Create Time" width="150">
        <template slot-scope="scope">
          <div>{{scope.row.createTime|timestampToTime}}</div>
        </template>
      </el-table-column>



      <el-table-column align="center" label="Operate" min-width="300">
        <template slot-scope="scope">
          
          <el-button size="mini" v-if="formInline.type == 3" @click="xdEdit(scope.$index, scope.row)">Treatment</el-button>
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">View</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table> 
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    
    <!-- 编辑界面 -->
    <el-dialog title="View" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="Appointment ID" prop="id">
          <el-input size="small" disabled v-model="editForm.id" auto-complete="off" placeholder="ID"></el-input>
        </el-form-item>
        <el-form-item label="User ID" prop="userid">
          <el-input size="small" disabled v-model="editForm.userid" auto-complete="off" placeholder="Please Enter"></el-input>
        </el-form-item>
        <el-form-item label="Doctor ID" prop="doctid">
          <el-input size="small" disabled v-model="editForm.doctid" auto-complete="off" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="Appointment Time" prop="apppointtime">
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


    <el-dialog title="Treatment" :visible.sync="xdFormVisible" width="50%" @click="closeDialog">
      <el-form label-width="120px" :model="xdForm" :rules="rules" ref="editForm">

        <el-row>
          <el-col :span="12">
        <el-form-item label="User ID" prop="userid">
          <el-input size="small" disabled v-model="xdForm.userid" auto-complete="off" placeholder="Please Enter"></el-input>
        </el-form-item>
        <el-form-item label="Doctor ID" prop="doctid">
          <el-input size="small" disabled v-model="xdForm.doctid" auto-complete="off" placeholder=""></el-input>
        </el-form-item>

        <el-form-item label="Select Pet" prop="petid">
          <el-select size="small" v-model="xdForm.petid"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in petdata" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="Select Disease" prop="dis1">
          <el-select size="small" v-model="xdForm.dis1"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Disease" prop="dis2">
          <el-select size="small" v-model="xdForm.dis2"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Disease" prop="dis3">
          <el-select size="small" v-model="xdForm.dis3"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Disease" prop="dis4">
          <el-select size="small" v-model="xdForm.dis4"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="Select Disease" prop="dis5">
          <el-select size="small" v-model="xdForm.dis5"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">

            <el-form-item label="Select Medicine" prop="med1">
          <el-select size="small" v-model="xdForm.med1"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med2">
          <el-select size="small" v-model="xdForm.med2"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med3">
          <el-select size="small" v-model="xdForm.med3"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med4">
          <el-select size="small" v-model="xdForm.med4"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med5">
          <el-select size="small" v-model="xdForm.med5"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med6">
          <el-select size="small" v-model="xdForm.med6"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med7">
          <el-select size="small" v-model="xdForm.med7"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Select Medicine" prop="med8">
          <el-select size="small" v-model="xdForm.med8"  placeholder="Please Choose" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>
          </el-col>

          

      </el-row>

      <el-form-item label="Suggestion" prop="jy">
          <el-input size="small"  v-model="xdForm.jy" auto-complete="off" placeholder="Please fill in the Suggestion and prescription"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">Save</el-button>
      </div>

    </el-dialog>

  </div>
</template>

<script>
import Axios from 'axios'
import { appointList, appointDelete,med,dis,pets,orderSave } from '../../api/payMG'
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
        value: 'Expire',
        label: 3
      }],

      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      xdFormVisible: false,

      medData: [], //
      disData: [], //数据
      petdata: [],

      petid:{
        userid:''
      },

      title: 'Add',
      editForm: {
        id:'',
        name:'',
        userid:'',
        classtype:'',
        status:'',
      },
      xdForm:{
        userid:'',
        petid:'',
        doct:'',
        dis1:'',
        dis2:'',
        dis3:'',
        dis4:'',
        dis5:'',
        med1:'',
        med2:'',
        med3:'',
        med4:'',
        med5:'',
        med6:'',
        med7:'',
        med8:'',
        jy:''



      },
      // rules表单验证
      rules: {
       
       
      },

      formInline: {
        page: 1,
        limit: 10,
        id:'',
        name:'',
        type:'',
        userid:'',
        token: localStorage.getItem('logintoken')
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
     this.formInline.type=getCookie("type")
     this.formInline.userid=getCookie("uid")
     this.getdata(this.formInline)

     this.getmed()
     this.getdis()
   
  },

  /**
   * 里面的方法只有被调用才会执行
   */

  methods: {


    getpet(pet){
      console.log("12312312312");

      pets(pet)
        .then(res => {
          
          this.petdata = res.data
          console.log(this.petdata);
        })
    },

    getdis(){
      console.log("12312312312");
      dis()
        .then(res => {
          
          this.disData = res.data
          console.log(this.disData);
        })
    },

    getmed(){
      console.log("12312312312");
      med()
        .then(res => {
       
          this.medData = res.data
          console.log(this.medData);
        })
    },
    

    // 获取数据方法
    getdata(parameter) {
      this.loading = true
 
      appointList(parameter)
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
        this.title = 'Modify'
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

    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          orderSave(this.xdForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.sta==1) {
                this.xdFormVisible = false
                this.getdata(this.formInline)
                this.$message.success({
                  message: 'Save Success！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.editFormVisible = false
              this.loading = false
              this.$message.error('Failed to save system environment variables. Please try again later！')
            })
        } else {
          return false
        }
      })
    },

    xdEdit: function(index, row) {

      if(row.status != 1)
      {
        this.$message.error('Appointment expire or cancel！')
      }else{

        this.petid.userid=row.userid

        console.log(this.petid);

        this.getpet(this.petid)

        this.xdFormVisible = true
      if (row != undefined && row != 'undefined') {
      
        this.xdForm.userid = row.userid
        this.xdForm.doctid = row.doctid
        this.xdForm.petid = ''
        this.dis1=''
        this.dis2=''
        this.dis3=''
        this.dis4=''
        this.dis5=''
        this.med1=''
        this.med2=''
        this.med3=''
        this.med4=''
        this.med5=''
        this.med6=''
        this.med7=''
        this.med8='' 
        this.jy=''     
      } else {
        this.xdForm.id = ''
        this.xdForm.userid = ''
        this.xdForm.name = ''
        this.xdForm.classtype = ''
        this.xdForm.status = ''
      }


    }
    },

   
    // 删除权限
    deleteUser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: '',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          appointDelete(row.id)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Data deletion success！'
                })
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
              this.$message.error('Deletion failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancel Deleted'
          })
        })
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.xdFormVisible = false
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

 