
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Drug Management</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">

      <el-form-item label="Search：">
        <el-input size="small" v-model="formInline.id" placeholder="Enter drug code"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="Enter the name of the drug"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.treatment" placeholder="Effect of drug treatment"></el-input>
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
      <el-table-column align="center" sortable prop="id" label="Drug ID" width="100"> 
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="Drug Name" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="instruction" label="Usage Notes" width="160">
      </el-table-column>
      <el-table-column align="center" sortable prop="treatment" label="Treatment Effects" width="160">
      </el-table-column>
      <el-table-column align="center" sortable prop="price" label="Price" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="stock" label="Stock" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="createTime" label="Creat Time" width="135">
        <template slot-scope="scope">
          <div>{{scope.row.createTime|timestampToTime}}</div>
        </template>
      </el-table-column>
      <el-table-column v-if="formInline.type != 2" align="center" label="Operate" min-width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="120px" :model="editForm" ref="editForm" :rules="rules">
        <el-form-item label="Drug ID" prop="id">
          <el-input size="small" :readonly="true" v-model="editForm.id" auto-complete="off" placeholder="ID automatic generation"></el-input>
        </el-form-item>
        <el-form-item label="Drug Name" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="Please enter drug name"></el-input>
        </el-form-item>
        <el-form-item label="Instructions" prop="instruction">
          <el-input size="small" v-model="editForm.instruction" auto-complete="off" placeholder="Please enter instructions"></el-input>
        </el-form-item>
        <el-form-item label="Treatment Effects" prop="treatment">
          <el-input size="small" v-model="editForm.treatment" auto-complete="off" placeholder="Please enter treatment effects"></el-input>
        </el-form-item>
        <el-form-item label="Price" prop="price">
          <el-input size="small" v-model="editForm.price" auto-complete="off" placeholder="please enter price"></el-input>
        </el-form-item>
        <el-form-item label="Stock" prop="stock">
          <el-input size="small" v-model="editForm.stock" auto-complete="off" placeholder="Please enter stock"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("edit")'>Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">Save</el-button>
      </div>
    </el-dialog>
    <!-- 菜单权限 -->
    <el-dialog title="Menu Permissions" :visible.sync="menuAccessshow" width="30%" @click='closeDialog("perm")'>
      <el-tree ref="tree" default-expand-all="" :data="RoleRight" :props="RoleRightProps" :default-checked-keys="checkmenu" node-key="id" show-checkbox>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("perm")'>Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="menuPermSave">Save</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  roleList,
  roleSave,
  roleDelete,
  rolePwd,
  RoleRightTree,
  RoleRightSave
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
import { getCookie } from '../../utils/util'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      menuAccessshow: false, //控制数据权限显示与隐藏
      title: 'Add',
      editForm: {
        id:'',
        name:'',
        instruction:'',
        treatment:'',
        price:'',
        stock:''
      },
      // rules 表单验证
      rules: {
      

        name: [
          { required: true, message: 'Please enter drug name', trigger: 'blur' }
        ],
        price: [
          { required: true, message: 'Please enter the drug price(0.00)', trigger: 'blur'  ,pattern:/^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})$/}
        ],
        treatment: [
          { required: true, message: 'Please eneter treatment effects', trigger: 'blur' }
        ],
        instruction: [
          { required: true, message: 'Please enter usage notes', trigger: 'blur' }
        ],
        stock: [
          { required: true, message: 'Please eneter stock(123)', trigger: 'blur',pattern: /^(0|[1-9][0-9]*)$/ }
         
        ],
        
      },
      formInline: {
        page: 1,
        limit: 10,
        treatment:'',
        id:'',
        name:'',
        type:''
       
      },
      // 删除
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 数据权限
      RoleRight: [],
      RoleRightProps: {
        children: 'children',
        label: 'name'
      },
      // 选中
      checkmenu: [],
      //参数role
      saveroleId: '',
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
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */

  methods: {
    // 获取角色列表
    getdata(parameter) {
      roleList(parameter)
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
          this.$message.error('Failed to obtain the role list, please try again later!')
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
        this.editForm.name = row.name
        this.editForm.price = row.price
        this.editForm.treatment = row.treatment
        this.editForm.instruction =row.instruction
        this.editForm.stock=row.stock
      } else {
        this.title = 'Add'
        this.editForm.id=''
        this.editForm.name = ''
        this.editForm.price =''
        this.editForm.treatment = ''
        this.editForm.instruction =''
        this.editForm.stock=''
      }
    },
    // 编辑、增加页面Save方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          roleSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.sta==1) {
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
              this.$message.error('Failed to save the character, please try again later!')
            })
        } else {
          return false
        }
      })
    },
    // 删除角色
    deleteUser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {

          roleDelete(row.id)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Deletion Successful!！'
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
            message: 'Undeleted!'
          })
        })
    },
    // 数据权限
    menuAccess: function(index, row) {
      this.menuAccessshow = true
      this.saveroleId = row.roleId
      RoleRightTree(row.roleId)
        .then(res => {
          if (res.data.success) {
            this.$message({
              type: 'success',
              message: 'Successfully obtained permissions'
            })
            this.changemenu(res.data.data)
            this.RoleRight = this.changeArr(res.data.data)
          } else {
            this.$message({
              type: 'info',
              message: res.data.msg
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('Failed to obtain permission, please try again later')
        })
    },
    // 选中菜单
    changemenu(arr) {
      let change = []
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
          change.push(arr[i].id)
        }
      }
      this.checkmenu = change
    },
    // tree 递归
    changeArr(list) {
      var temptree = [],
        tree = [],
        items = []
      for (var i in list) {
        if (!temptree[list[i].id]) {
          var trow = {
            id: list[i].id,
            pId: list[i].pId,
            name: list[i].name,
            open: list[i].open,
            checked: list[i].checked,
            children: []
          }
          temptree[list[i].id] = trow
          items.push(trow)
        }
        if (list[i].uid > 0) {
          temptree[list[i].id]['children'].push({
            id: list[i].id,
            pId: list[i].pId,
            name: list[i].name,
            open: list[i].open,
            checked: list[i].checked,
            children: []
          })
        }
      }

      for (var j in items) {
        if (temptree[items[j].pId]) {
          temptree[items[j].pId]['children'].push(temptree[items[j].id])
        } else {
          tree.push(temptree[items[j].id])
        }
      }
      temptree = null
      items = null
      return tree
    },
    // 菜单权限-Save
    menuPermSave() {
      let parm = {
        roleId: this.saveroleId,
        moduleIds: ''
      }
      let node = this.$refs.tree.getCheckedNodes()
      let moduleIds = []
      if (node.length != 0) {
        for (let i = 0; i < node.length; i++) {
          moduleIds.push(node[i].id)
        }
        parm.moduleIds = JSON.stringify(moduleIds)
      }
      RoleRightSave(parm)
        .then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: 'Permissions saved successfully'
            })
            this.menuAccessshow = false
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
          this.$message.error('Failed to save permissions, please try again later！')
        })
    },
    // 关闭编辑、增加弹出框
    closeDialog(dialog) {
      if (dialog == 'edit') {
        this.editFormVisible = false
      } else if (dialog == 'perm') {
        this.menuAccessshow = false
      }
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

 