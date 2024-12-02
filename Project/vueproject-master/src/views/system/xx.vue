
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Personal Information</el-breadcrumb-item>
    </el-breadcrumb>
    
    <el-descriptions class="margin-top"  :column="3" size=" "  border >
    
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        Username
      </template>
      {{this.userData.name}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        Account Number
      </template>
      {{this.userData.account}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        Password
      </template>
      {{this.userData.password}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        Phone Number
      </template>
      {{this.userData.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        Account Types
      </template>
      <el-tag size="small"> 
      <span v-if="this.userData.type== 1"><div >Administrator</div></span>
        <span v-if="this.userData.type== 2"><div >User</div></span>
        <span v-if="this.userData.type== 3"><div >Doctor</div></span></el-tag>

    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        Account Status
      </template>
      <el-tag size="small"> 
        <span v-if="this.userData.status== 1"><div >Enable</div></span>
        <span v-if="this.userData.status== 2"><div >Deactivate</div></span></el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-office-building"></i>
        Account Introduction
      </template>
      {{this.userData.url}}
    </el-descriptions-item>
    <template slot="extra">
      <!-- <el-button type="primary" size="small">操作</el-button> -->
      <el-button size="mini" @click="handleEdit()">Edit</el-button>
    </template>
  </el-descriptions>
    
    <!-- Edit界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
        
       
        <el-form-item label="Password" prop="password">
          <el-input size="small" v-model="editForm.password" auto-complete="off" placeholder="Please eneter the new password"></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input size="small" v-model="editForm.name" placeholder="Please enter a new name"></el-input>
        </el-form-item>
        <el-form-item label="Phone Number" prop="phone">
          <el-input size="small" v-model="editForm.phone" placeholder="Please enter your new phone number"></el-input>
        </el-form-item>
        <el-form-item label="Introduction" prop="url">
          <el-input size="small" v-model="editForm.url" placeholder="Please enter a new description"></el-input>
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
        <el-button size="small" type="primary" :loading="loading" class="title" @click="menuPermSave">保存</el-button>
      </div>
    </el-dialog>
    
    <el-dialog title="所属单位" :visible.sync="unitAccessshow" width="30%" @click='closeDialog("unit")'>
      <el-tree ref="tree" default-expand-all="" :data="UserDept" :props="defaultProps" @check-change="handleClick" :default-checked-keys="checkmenu" node-key="id" show-checkbox check-strictly>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("unit")'>Cancel</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="unitPermSave">保存</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>

// 导入请求方法
import {
  userList,
  xxSave,
  userDelete,
  userPwd,
  userExpireToken,
  userFlashCache,
  userLock,
  UserDeptTree,
  UserDeptSave,
  UserDeptdeptTree,
  UserChangeDept,
  xx
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
import { getCookie } from '../../utils/util'
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
        status:'',
        url:'',
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
        
      },
      // 删除User
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      // 重置Password
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
      uid:'',
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
    this.uid=getCookie("uid")
    this.getdata(this.formInline)
  },
 


  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {

    

    // 获取数据方法
    getdata(formInline) {
      this.loading = true
      
      xx(this.uid).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.userData = res.data
          console.log(this.userData.account);
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
      // 修改状态
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
    handleEdit: function(index,row) {
      console.log(row);
      this.editFormVisible = true
      
        this.title = 'Modify User'
        this.editForm.id = this,uid
        
      
    },
    // Edit、添加提交方法
    submitForm(editData) {
        this.editForm.id=this.uid
        this.editForm.account=this.userData.account
      this.$refs[editData].validate(valid => {
        if (valid) {
          // 请求方法
          xxSave(this.editForm)
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
                  message: 'The account already exists! Please try again'
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('Failed to save, please try again later!')
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
    // 保存部门
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
              message: 'Setup successful!'
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
          this.$message.error('Setting failed, please try again later!')
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
    // 删除User
    deleteUser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          // 删除
          userDelete(row.id)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Data deleted successfully!'
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
              this.$message.error('Data deletion failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Undeleted!'
          })
        })
    },
    // 重置Password
    resetpwd(index, row) {
      this.resetpsd.userId = row.id
      this.$confirm('Are you sure you want to reset your Password?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          userPwd(this.resetpsd.userId)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Password reset successful!'
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
              this.$message.error('Password reset failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancel resetting Password!'
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
          this.$message.error('Failed to obtain permission, please try again later!')
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
    // 菜单权限-保存
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
              this.$message.error('User offline failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancelled'
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
              message: 'Refresh successful!'
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
          this.$message.error('Refresh failed, please try again later!')
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
.margin-top{
    opacity: 0.95;

}
</style>

 