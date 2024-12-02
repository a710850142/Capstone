/**
 * 系统管理  disease Management
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>disease Management</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="Search：">
        <el-input size="small" v-model="formInline.id" placeholder="Enter disease ID"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="Enter the disease name"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.symptom" placeholder="Enter disease Disease symptoms"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">Search</el-button>
        <el-button v-if="formInline.type != 2" size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">Add</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="Loading" style="width: 100%;opacity: 0.8;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column  align="center" sortable prop="id" label="Disease ID" width="100"> 
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="Disease Name" width="120">
      </el-table-column>

      <el-table-column align="center" sortable prop="symptom" label="Disease Symptoms" width="200"> 
      </el-table-column>
      <el-table-column align="center" sortable prop="proposal" label="Disease Suggestion" width="200">
      </el-table-column>


      <el-table-column align="center" sortable prop="createTime" label="Create Time" width="150">
        <template slot-scope="scope">
          <div>{{scope.row.createTime|timestampToTime}}</div>
        </template>
      </el-table-column>
      
      <el-table-column v-if="formInline.type != 2" align="center" label="Operate" min-width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="Disease ID" prop="id">
          <el-input size="small" :readonly="true" v-model="editForm.id" auto-complete="off" placeholder="ID automatic generation"></el-input>
        </el-form-item>
        <el-form-item label="Disease Name" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="Please enter the name of the disease"></el-input>
        </el-form-item>
        <el-form-item label="Disease Symptoms" prop="symptom">
          <el-input size="small" v-model="editForm.symptom" auto-complete="off" placeholder="Please enter disease symptoms"></el-input>
        </el-form-item>
        <el-form-item label="Disease Suggestion" prop="proposal">
          <el-input size="small" v-model="editForm.proposal" auto-complete="off" placeholder="Please enter disease suggestion"></el-input>
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
import { deptList, deptSave, deptDelete } from '../../api/userMG'
import Pagination from '../../components/Pagination'
import { getCookie } from '../../utils/util'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: 'Add',
      editForm: {
        id:'',
        name:'',
        symptom:'',
        proposal:'',

      },
      // rules表单验证

      rules: {
        name: [
          { required: true, message: 'Please enter the name of the disease', trigger: 'blur' }
        ]
      },
      formInline: {
        page: 1,
        limit: 10,
        id: '',
        name: '',
        symptom:'',
        type:''
      },
      // 删除部门
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

  /**
   * 创建完毕
   */
  created() {
    this.formInline.type=getCookie("type")
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    // 获取公司列表
    getdata(parameter) {
      this.loading = true
       deptList(parameter)
        .then(res => {
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
      console.log(row);
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = 'Modify'
        this.editForm.id= row.id
        this.editForm.name = row.name
        this.editForm.proposal = row.proposal
        this.editForm.symptom = row.symptom
      } else {
        this.title = 'Add'
        this.editForm.id = ''
        this.editForm.name = ''
        this.editForm.proposal = ''
        this.editForm.symptom = ''
      }
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          deptSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.sta==1) {
                this.getdata(this.formInline)
                this.$message.success({
                  message: 'Saved Successfully!'
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
              this.$message.error('Failed to save company, please try again later!！')
            })
        } else {
          return false
        }
      })
    },
    // 删除公司
    deleteUser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          // deptDelete(row.deptId)
          deptDelete(row.id)
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
              this.$message.error('Company deletion failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Undeleted!'
          })
        })
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

 