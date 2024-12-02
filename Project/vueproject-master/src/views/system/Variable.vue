/**
 * 系统管理  宠物管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Pet Management</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- Search筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">

      <el-form-item label="Search：">
        <el-input size="small" v-model="formInline.id" placeholder="Enter pet ID"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="Enter pet's name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">Search</el-button>
        <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">Add</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table  size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="Loading" style="width: 100%;opacity: 0.8;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>

      <el-table-column  align="center" sortable prop="id" label="Pet ID" width="100"> 
      </el-table-column>
      <el-table-column align="center" sortable prop="userid" label="User ID" width="120">
      </el-table-column>

      <el-table-column align="center" sortable prop="name" label="Pet Name" width="120"> 
      </el-table-column>
      
      <el-table-column align="center" sortable prop="classtype" label="Pet Type" width="120">
      </el-table-column>

      <el-table-column align="center"  sortable prop="status" label="Status" min-width="120">
        <template slot-scope="scope">
        <span v-if="scope.row.status== 1"><div >Healthy</div></span>
        <span v-if="scope.row.status== 2"><div >Unhealthy</div></span>
      </template>
    </el-table-column>

    <el-table-column align="center" sortable prop="createTime" label="Create Time" width="150">
        <template slot-scope="scope">
          <div>{{scope.row.createTime|timestampToTime}}</div>
        </template>
      </el-table-column>



      <el-table-column align="center" label="Operate" min-width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- Edit界面 -->
    <el-dialog title="Modify" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="Pet ID" prop="id">
          <el-input size="small" :readonly="true" v-model="editForm.id" auto-complete="off" placeholder="ID automatic generation"></el-input>
        </el-form-item>
        <el-form-item label="User ID" prop="userid"> <!-- v-model="editForm.userid"  -->
          <el-input size="small" :readonly="true" auto-complete="off" placeholder="Please enter user ID"></el-input>
        </el-form-item>
        <el-form-item label="Pet Name" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="Please enter pet name"></el-input>
        </el-form-item>
        <el-form-item label="Pet Type" prop="classtype">
          <el-select size="small" v-model="editForm.classtype" placeholder="Please Select" class="userRole">
            <el-option v-for="parm in cwlx" :key="parm.label" :label="parm.value"
                     :value="parm.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Status" prop="status">
          <el-select size="small" v-model="editForm.status" placeholder="Please Select" class="userRole">
            <el-option v-for="parm in statusxz" :key="parm.label" :label="parm.value"
                     :value="parm.label"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">save</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from 'axios'
import { variableList, variableSave, variableDelete } from '../../api/userMG'
import Pagination from '../../components/Pagination'
import { getCookie } from '../../utils/util'
export default {
  data() {

    return {
      statusxz: [{
        value: 'Healthy',
        label: 1
      }, {
        value: 'Unhealthy',
        label: 2
      }],

      cwlx: [{
        value: 'Cat',
        label: 'Cat'
      }, {
        value: 'Dog',
        label: 'Dog'
      },
      {
        value: 'Pig',
        label: 'Pig'
      },
      {
        value: 'Goose',
        label: 'Goose'
      },
      {
        value: 'Fish',
        label: 'Fish'
      },
      {
        value: 'Other',
        label: 'Other'
      }],


      type:'',
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制Edit页面显示与隐藏
      title: 'Add',
      editForm: {
        id:'',
        name:'',
        userid:'',
        classtype:'',
        status:'',
      },
      // rules表单验证
      rules: {
        // userid: [
        //   { required: true, message: 'Please enter user ID', trigger: 'blur' }
        // ],
        status: [
          { required: true, message: 'Please enter the status', trigger: 'blur' }
        ],
        name: [
          { required: true, message: 'Please enter pet name', trigger: 'blur' }
        ]
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
      // Delete
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //Search权限
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
     this.formInline.userid=getCookie("guuid")
     this.getdata(this.formInline)
     editForm.userid=getCookie("guuid")
   
  },

  /**
   * 里面的方法只有被调用才会执行
   */

  methods: {

    

    // 获取数据方法
    getdata(parameter) {
      this.loading = true
 
      variableList(parameter)
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

           // this.type=getCookie("type")

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
    // Search事件
    search() {
      this.getdata(this.formInline)
    },
    //显示Edit界面
    handleEdit: function(index, row) {
      this.editForm.userid=getCookie("guuid")
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = 'Modify'
        this.editForm.id = row.id
        //this.editForm.userid = row.userid
        this.editForm.name = row.name
        this.editForm.classtype = row.classtype
        this.editForm.status = row.status
      } else {
        this.title = 'Add'
        this.editForm.id = ''
        //this.editForm.userid = ''
        this.editForm.name = ''
        this.editForm.classtype = ''
        this.editForm.status = ''
      }
    },
    // Edit、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          variableSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.sta==1) {
                this.getdata(this.formInline)
                this.$message.success({
                  message: 'Saved Successfully!！'
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
              this.$message.error('Failed to save system environment variables, please try again later!')
            })
        } else {
          return false
        }
      })
    },
    // Delete权限
    deleteUser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          // variableDelete(row.varId)
          variableDelete(row.id)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Delete success!'
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
              this.$message.error('System environment variable deletion failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Undeleted!'
          })
        })
    },
    // 关闭Edit、增加弹出框
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

 