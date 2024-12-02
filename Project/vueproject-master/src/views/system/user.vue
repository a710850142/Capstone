/**
 * 系统管理 User管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>User Management</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="Search：">
        <el-select size="small" v-model="formInline.isLock" placeholder="Please Select">
          <el-option label="All" value=""></el-option>
          <el-option label="Enable" value="1"></el-option>
          <el-option label="Deactivate" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.userName" placeholder="Enter account number"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.userMobile" placeholder="Enter your phone number"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">Search</el-button>
        <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">Add</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" @selection-change="selectChange" :data="userData" highlight-current-row v-loading="loading" border element-loading-text="Loading" style="width: 100%;opacity: 0.8;">
      <el-table-column align="center" type="selection" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="account" label="Account" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="password" label="Password" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="Account Name" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="phone" label="Phone Number" min-width="120">
      </el-table-column>
      <el-table-column align="center"  sortable prop="type" label="Type" min-width="120">
        <template slot-scope="scope">
        <span v-if="scope.row.type== 1"><div >Administrator</div></span>
        <span v-if="scope.row.type== 2"><div >User</div></span>
        <span v-if="scope.row.type== 3"><div >Doctor</div></span>
        </template>
      </el-table-column>
      <el-table-column align="center"  sortable prop="status" label="Status" min-width="120">
        <template slot-scope="scope">
        <span v-if="scope.row.status== 1"><div >Enable</div></span>
        <span v-if="scope.row.status== 2"><div >Deactivate</div></span>
      </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="createTime" label="Modification Time" min-width="150">
        <template slot-scope="scope">
          <div>{{scope.row.createTime|timestampToTime}}</div>
        </template>
      </el-table-column>

      <el-table-column align="center"  label="Operate" min-width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">Delete</el-button>
          <!-- <el-button size="mini" type="success" @click="resetpwd(scope.$index, scope.row)">Reset Password</el-button> -->
          <!-- <el-button size="mini" type="success" @click="dataAccess(scope.$index, scope.row)">数据权限</el-button>
          <el-button size="mini" type="success" @click="offlineUser(scope.$index, scope.row)">下线</el-button>
          <el-button size="mini" type="success" @click="refreshCache(scope.$index, scope.row)">刷新缓存</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- Edit界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
        <el-form-item label="ID:">
          <el-input  :readonly="true" size="small" v-model="editForm.id" auto-complete="off" placeholder="ID automatic generation"></el-input>
        </el-form-item>
        <el-form-item label="Account" prop="account">
          <el-input id="text1" readonly="true" size="small" v-model="editForm.account" auto-complete="off" placeholder="Please enter account number"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input size="small" v-model="editForm.password" auto-complete="off" placeholder="Please enter password"></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input size="small" v-model="editForm.name" placeholder="Please enter name"></el-input>
        </el-form-item>
        <el-form-item label="Phone Number" prop="phone">
          <el-input size="small" v-model="editForm.phone" placeholder="Please enter your phone number"></el-input>
        </el-form-item>
        <el-form-item label="Type" prop="type">
          <el-select size="small" v-model="editForm.type"  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in typexz" :key="parm.label" :label="parm.value"
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
        <el-button size="small" @click='closeDialog("edit")'>Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">Save</el-button>
      </div>
    </el-dialog>
    <!-- 数据权限
    <el-dialog title="数据权限" :visible.sync="dataAccessshow" width="30%" @click='closeDialog("perm")'>
      <el-tree ref="tree" default-expand-all="" :data="UserDept" :props="defaultProps" :default-checked-keys="checkmenu" node-key="id" show-checkbox>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("perm")'>Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="menuPermSave">Save</el-button>
      </div>
    </el-dialog>
    
    <el-dialog title="所属单位" :visible.sync="unitAccessshow" width="30%" @click='closeDialog("unit")'>
      <el-tree ref="tree" default-expand-all="" :data="UserDept" :props="defaultProps" @check-change="handleClick" :default-checked-keys="checkmenu" node-key="id" show-checkbox check-strictly>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("unit")'>Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="unitPermSave">Save</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>

// 导入请求方法
import {
  userList,
  userSave,
  userDelete,
  userPwd,
  userExpireToken,
  userFlashCache,
  userLock,
  UserDeptTree,
  UserDeptSave,
  UserDeptdeptTree,
  UserChangeDept
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {


      statusxz: [{
        value: 'Enable',
        label: 1
      }, {
        value: 'Deactivate',
        label: 2
      }],
        
      typexz: [{
        value: 'Administrator',
        label: 1
      }, {
        value: 'User',
        label: 2
      }, {
        value: 'Doctor',
        label: 3
      }],
        
      

      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      title: 'Add User',
      editFormVisible: false, //控制Edit页面显示与隐藏
      dataAccessshow: false, //控制数据权限显示与隐藏
      unitAccessshow: false, //控制所属单位隐藏与显示
      // Edit与添加
      editForm: {
        id:'',
        account:'',
        password:'',
        phone:'',
        name:'',
        type:'',
        status:''
      },
      // 部门参数
      unitparm: {
        userIds: '',
        deptId: '',
        deptName: ''
      },
      // 选择数据
      selectdata: [],
      // rules表单验证
      rules: {
        account: [
          { required: true, message: 'Please enter account number', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter password', trigger: 'blur' }
        ],
        phone: [
          { message: 'Please enter your phone number', trigger: 'blur' },
          {
            //pattern: /^1(3\d|47|5((?!4)\d)|7(0|1|[6-8])|8\d)\d{8,8}$/,
            pattern: /^[0-9]*[1-9][0-9]*$/,
            message: 'Please enter a valid phone Number',
            trigger: 'blur'
          }
        ],
        type: [{ required: true, message: 'Please SelectType', trigger: 'blur' }],
        status: [{ required: true, message: 'Please SelectType', trigger: 'blur' }]
      },
      // DeleteUser
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      // Reset Password
      resetpsd: {
        userId: '',
        token: localStorage.getItem('logintoken')
      },
      // User下线
      offline: {
        token: localStorage.getItem('logintoken')
      },
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        deptId: '',
        userName: '',
        userMobile: '',
        isLock: ''
      },
      typees:'',
      ids:'',
      //User数据
      userData: [],
      // 数据权限
      UserDept: [],
      defaultProps: {
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
        total: ''
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
    getdata(formInline) {
      this.loading = true
      

      /***
       * 调用接口，注释上面模拟数据 Cancel下面注释
       */
      //获取User列表
      userList(formInline).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.userData = res.data
          console.log(res);
          // 分页赋值
          this.pageparm.currentPage = this.formInline.page
          this.pageparm.pageSize = this.formInline.limit
          this.pageparm.total = res.count

        }
      })
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    //搜索事件
    search() {
      this.getdata(this.formInline)
    },
    // 修改type
    editType: function(index, row) {
      this.loading = true
      let parm = {
        lock: '',
        userId: '',
        token: localStorage.getItem('logintoken')
      }
      parm.userId = row.userId
      let lock = row.isLock
      if (lock == 'N') {
        parm.lock = 'Y'
      } else {
        parm.lock = 'N'
      }
      // 修改Status
      userLock(parm).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.$message({
            type: 'success',
            message: 'Status modified successfully'
          })
          this.getdata(this.formInline)
        }
      })
    },
    //显示Edit界面
    handleEdit: function(index, row) {
      console.log(row);
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        //document.getElementById("text1").readOnly=true
        this.xaccount=row.account
        this.title = 'Modify User'
        this.editForm.id = row.id
        this.editForm.account = row.account
        this.editForm.password = row.password
        this.editForm.name = row.name
        this.editForm.phone = row.phone
        this.editForm.type = row.type
        this.editForm.status = row.status
      } else {
        document.getElementById("text1").readOnly=false
        this.title = 'Add User'
        this.editForm.id = ''
        this.editForm.account = ''
        this.editForm.password = ''
        this.editForm.name = ''
        this.editForm.phone = ''
        this.editForm.type = ''
        this.editForm.status = ''
      }
    },
    // Edit、添加提交方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          // 请求方法
          userSave(this.editForm)
            .then(res => {
              //this.editFormVisible = false
              this.loading = false
              if (res.sta==1) {
                console.log(res);
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: 'Data saved successfully!'
                })
                this.editFormVisible = false
              } else {
                this.$message({
                  type: 'info',
                  message: 'Account already exists! Please re-operate!'
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('Save failed, please try again later!')
            })
        } else {
          return false
        }
      })
    },
    // 显示部门设置
    handleunit: function(index, row) {
      this.unitAccessshow = true
      let parms = 0
      UserDeptdeptTree(parms)
        .then(res => {
          if (res.data.success) {
            this.UserDept = this.changeArr(res.data.data)
          } else {
            this.$message({
              type: 'info',
              message: res.msg
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('Failed to configure permissions, please try again later!')
        })
    },
    handleClick(data, checked, node) {
      if (checked) {
        this.$refs.tree.setCheckedNodes([])
        this.$refs.tree.setCheckedNodes([data])
        this.unitparm.deptId = data.id
        this.unitparm.deptName = data.name
        //交叉点击节点
      } else {
      }
    },
    // Save部门
    unitPermSave() {
      let len = this.selectdata
      let ids = []
      if (len != 0) {
        for (let i = 0; i < len.length; i++) {
          ids.push(len[i].userId)
        }
      }
      this.unitparm.userIds = ids.join(',')
      UserChangeDept(this.unitparm)
        .then(res => {
          this.unitAccessshow = false
          if (res.success) {
            this.$message({
              type: 'success',
              message: 'Department setup successful！'
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
          this.$message.error('Department setting failed, please try again later!')
        })
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
    },
    // 关闭Edit、增加弹出框
    closeDialog(dialog) {
      if (dialog == 'edit') {
        this.editFormVisible = false
      } else if (dialog == 'perm') {
        this.dataAccessshow = false
      } else if (dialog == 'unit') {
        this.unitAccessshow = false
      }
    },
    // DeleteUser
    deleteUser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          // Delete
          userDelete(row.id)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Data was deleted successfully!'
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
              this.$message.error('Data deletion failed, please try again later！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Undeleted！'
          })
        })
    },
    // Reset Password
    resetpwd(index, row) {
      this.resetpsd.userId = row.id
      this.$confirm('Are you sure you want to reset Password?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          userPwd(this.resetpsd.userId)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Password reset successful！'
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
              this.$message.error('Reset pssword failed, please try again later！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancel reset password！'
          })
        })
    },
    // 数据权限
    dataAccess: function(index, row) {
      this.dataAccessshow = true
      this.saveroleId = row.userId
      UserDeptTree(row.userId)
        .then(res => {
          if (res.data.success) {
            this.checkmenu = this.changemenu(res.data.data)
            this.UserDept = this.changeArr(res.data.data)
          } else {
            this.$message({
              type: 'info',
              message: res.data.msg
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('Failed to obtain permission, please try again later！')
        })
    },
    //数据格式化
    changeArr(data) {
      var pos = {}
      var tree = []
      var i = 0
      while (data.length != 0) {
        if (data[i].pId == 0) {
          tree.push({
            id: data[i].id,
            name: data[i].name,
            pId: data[i].pId,
            open: data[i].open,
            checked: data[i].checked,
            children: []
          })
          pos[data[i].id] = [tree.length - 1]
          data.splice(i, 1)
          i--
        } else {
          var posArr = pos[data[i].pId]
          if (posArr != undefined) {
            var obj = tree[posArr[0]]
            for (var j = 1; j < posArr.length; j++) {
              obj = obj.children[posArr[j]]
            }

            obj.children.push({
              id: data[i].id,
              name: data[i].name,
              pId: data[i].pId,
              open: data[i].open,
              checked: data[i].checked,
              children: []
            })
            pos[data[i].id] = posArr.concat([obj.children.length - 1])
            data.splice(i, 1)
            i--
          }
        }
        i++
        if (i > data.length - 1) {
          i = 0
        }
      }
      return tree
    },
    // 选中菜单
    changemenu(arr) {
      let change = []
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
          change.push(arr[i].id)
        }
      }
      return change
    },
    // 菜单权限-Save
    menuPermSave() {
      let parm = {
        userId: this.saveroleId,
        deptIds: ''
      }
      let node = this.$refs.tree.getCheckedNodes()
      let moduleIds = []
      if (node.length != 0) {
        for (let i = 0; i < node.length; i++) {
          moduleIds.push(node[i].id)
        }
        parm.deptIds = JSON.stringify(moduleIds)
      }
      UserDeptSave(parm)
        .then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: 'Permissions saved successfully'
            })
            this.dataAccessshow = false
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
          this.$message.error('Failed to save permissions, please try again later!')
        })
    },
    // 下线User
    offlineUser(index, row) {
      this.$confirm('Are you sure you want to' + row.userName + 'User offline?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          userExpireToken(row.userName)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: 'User' + row.userName + 'Forced offline successful！'
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
              this.$message.error('User failed to log off, please try again later！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Canceled'
          })
        })
    },
    // 刷新缓存
    refreshCache(index, row) {
      userFlashCache(row.userName)
        .then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: 'Refresh Successful!'
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
          this.$message.error('Refresh failed, please try again later！')
        })
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

 