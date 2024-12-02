/**
 * 订单管理 交易订单
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Trading Orders</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- Search筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="Search：">
        <el-input size="small" v-model="formInline.id" placeholder="Enter order ID" width="10"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.petid" placeholder="Enter pet ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.doctid" placeholder="Enter doctor ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.userid" placeholder="Enter user ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select size="small" v-model="formInline.status"  placeholder="Please Select" >
          <el-option v-for="type in payway" :label="type.key" :value="type.value" :key="type.value"></el-option>
        </el-select>
      </el-form-item>
 
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">Search</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="Loading" style="width: 100%;opacity: 0.8;">
     
      <el-table-column align="center"  sortable prop="id" label="Order ID" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column  align="center"  sortable prop="petid" label="Pet ID" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column align="center" sortable prop="userid" label="User ID" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column align="center" sortable prop="doctid" label="Doctor ID" width="140" show-overflow-tooltip>
      </el-table-column>
      <el-table-column align="center" sortable prop="sumprice" label="Order Price" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column align="center"  sortable prop="status" label="Status" min-width="120">
        <template slot-scope="scope" >
        <span v-if="scope.row.status== 1"><div >Payment to be made</div></span>
        <span v-if="scope.row.status== 2"><div >Paid</div></span>
        <!-- <span v-if="scope.row.status== 3"><div >Expired</div></span> -->
        </template>
      </el-table-column>
      
      
      
      <el-table-column align="center" sortable prop="createTime" label="Creation Time" width="150">
        <template slot-scope="scope">
          <div>{{scope.row.createTime|timestampToTime}}</div>
        </template>
      </el-table-column>

      <el-table-column align="center" label="Operate" min-width="150">
        <template slot-scope="scope">
          <el-button  size="mini"  @click="zifu(scope.$index, scope.row)">Pay</el-button>
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Preview</el-button>
          <el-button size="mini" type="danger" @click="deleteuser(scope.$index, scope.row)">Delete</el-button>

        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="50%" @click="closeDialog('editForm')">
      <el-form label-width="120px" :model="editForm" ref="editForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Order Number">
              <el-input size="small" v-model="editForm.id" auto-complete="off"  disabled></el-input>
            </el-form-item>
            <el-form-item label="User ID">
              <el-input size="small" v-model="editForm.userid" auto-complete="off"  disabled></el-input>
            </el-form-item>

            <el-form-item label="Order Status" prop="status">
          <el-select size="small" v-model="editForm.status" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in statusxz" :key="parm.label" :label="parm.value"
                     :value="parm.label"></el-option>
          </el-select>
        </el-form-item> 


        <el-form-item label="Have Disease" prop="dis1" v-if="editForm.dis1 != null" >
          <el-select size="small" v-model="editForm.dis1" disabled  placeholder="Please Select" class="userRole">
          <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
        <el-form-item label="Have Disease" prop="dis2"   v-if="editForm.dis2 != null" >
          <el-select size="small" v-model="editForm.dis2" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
        <el-form-item label="Have Disease" prop="dis3" v-if="editForm.dis3 != null" >
          <el-select size="small" v-model="editForm.dis3" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
        <el-form-item label="Have Disease" prop="dis4"  v-if="editForm.dis4 != null" >
          <el-select size="small" v-model="editForm.dis4" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
        <el-form-item label="Have Disease" prop="dis5" v-if="editForm.dis5 != null" >
          <el-select size="small" v-model="editForm.dis5" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in disData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
        
      
            
          </el-col>
          <el-col :span="12">
            <el-form-item label="Pet ID">
              <el-input size="small" v-model="editForm.petid" auto-complete="off"  disabled></el-input>
            </el-form-item>
            <el-form-item label="Doctor ID">
              <el-input size="small" v-model="editForm.doctid" auto-complete="off"   disabled></el-input>
            </el-form-item>

            <el-form-item label="Total order price">
              <el-input size="small" v-model="editForm.sumprice" auto-complete="off"   disabled></el-input>
            </el-form-item>

            <el-form-item label="Therapeutic Drugs" prop="med1" v-if="editForm.med1 != null" >
          <el-select size="small" v-model="editForm.med1" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 

        <el-form-item label="Therapeutic Drugs" prop="med2" v-if="editForm.med2 != null" >
          <el-select size="small" v-model="editForm.med2" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 

        <el-form-item label="Therapeutic Drugs" prop="med3" v-if="editForm.med3 != null" >
          <el-select size="small" v-model="editForm.med3" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 

        <el-form-item label="Therapeutic Drugs" prop="med4" v-if="editForm.med4 != null" >
          <el-select size="small" v-model="editForm.med4" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item>  

        <el-form-item label="Therapeutic Drugs" prop="med5" v-if="editForm.med5 != null" >
          <el-select size="small" v-model="editForm.med5" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 

        <el-form-item label="Therapeutic Drugs" prop="med6" v-if="editForm.med6 != null" >
          <el-select size="small" v-model="editForm.med6" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 

        <el-form-item label="Therapeutic Drugs" prop="med7" v-if="editForm.med7 != null" >
          <el-select size="small" v-model="editForm.med7" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
        <el-form-item label="Therapeutic Drugs" prop="med8" v-if="editForm.med8 != null" >
          <el-select size="small" v-model="editForm.med8" disabled  placeholder="Please Select" class="userRole">
            <el-option v-for="parm in medData" :key="parm.id" :label="parm.name"
                     :value="parm.id"></el-option>
          </el-select>
        </el-form-item> 
            
     


          </el-col>
        </el-row>
        
        <el-form-item label="Suggestion">
              <el-input size="small" v-model="editForm.jy" auto-complete="off"   disabled></el-input>
            </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { OrderList, OrderRefund,med,dis,orderDelete,zifu } from '../../api/payMG'
import Pagination from '../../components/Pagination'
import { getCookie } from '../../utils/util'
export default {
  data() {
    return {

      statusxz: [{
        value: 'Payment to be made',
        label: 1
      }, {
        value: 'Paid',
        label: 2
      }, {
        value: 'Expired',
        label: 3
      }],

      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: 'Preview',
     
      payway: [
        { key: 'Please Select', value: '' },
        { key: 'Payment to be made', value: 1 },
        { key: 'Paid', value: 2 },
        // { key: 'Expired', value: 3 }
        
      ],
      editForm: {
        id: '',
        name: '',
        payType: 1,
        partner: '',
        subMchId: '',
        appid: '',
        notifyUrl: '',
        signType: '',
        partnerKey: '',
        selleruserId: '',
        certPath: '',
        certPassword: '',
        rsaKey: '',
        
        token: localStorage.getItem('logintoken')
      },
      formInline: {
        page: 1,
        limit: 10,
        id:'',
        userid:'',
        doctid:'',
        petid:'',
        status: '',
        type:'',
        uid:'',
        token: localStorage.getItem('logintoken')
      },
      // Delete部门
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //Search权限
      listData: [], //用户数据
      medData: [], //
      disData: [], //数据

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
    this.formInline.uid=getCookie("uid")
    this.getdata(this.formInline)
    this.getmed()
    this.getdis()
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {

     
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


    // Delete
    deleteuser(index, row) {
      this.$confirm('Are you sure you want to delete?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          // Delete
          orderDelete(row.id)
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
              this.$message.error('Data Delete failed, please try again later!')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Delete Canceled！'
          })
        })
    },
     // Delete
     zifu(index, row) {
      this.$confirm('Are you sure you want to pay?', 'Information', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => {
          // Delete
          zifu(row.id)
            .then(res => {
              if (res.sta==1) {
                this.$message.success({
                  message: 'Payment Successful!'
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
              this.$message.error('Payment failed, please try again later！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Delete Canceled！'
          })
        })
    },

    // 获取公司列表
    getdata(parameter) {
      this.loading = true

      OrderList(parameter)
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
    // Search事件
    search() {
      this.getdata(this.formInline)
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      this.editForm = row
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          ConfigSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.success) {
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: 'Company saved successfully!'
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
              this.$message.error('Failed to save payment configuration information, please try again later!')
            })
        } else {
          return false
        }
      })
    },
    
    // 关闭编辑、增加弹出框
    closeDialog(formName) {
      this.editFormVisible = false
      this.$refs[formName].resetFields()
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

 
 

 