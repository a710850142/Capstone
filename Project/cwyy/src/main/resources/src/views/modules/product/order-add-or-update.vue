<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="petid">
      <el-input v-model="dataForm.petid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="userid">
      <el-input v-model="dataForm.userid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="doctid">
      <el-input v-model="dataForm.doctid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="1.待支付2.已支付3.已过期" prop="status">
      <el-input v-model="dataForm.status" placeholder="1.待支付2.已支付3.已过期"></el-input>
    </el-form-item>
    <el-form-item label="" prop="sumprice">
      <el-input v-model="dataForm.sumprice" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="dis1">
      <el-input v-model="dataForm.dis1" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="dis2">
      <el-input v-model="dataForm.dis2" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="dis3">
      <el-input v-model="dataForm.dis3" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="dis4">
      <el-input v-model="dataForm.dis4" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="dis5">
      <el-input v-model="dataForm.dis5" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med1">
      <el-input v-model="dataForm.med1" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med2">
      <el-input v-model="dataForm.med2" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med3">
      <el-input v-model="dataForm.med3" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med4">
      <el-input v-model="dataForm.med4" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med5">
      <el-input v-model="dataForm.med5" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med6">
      <el-input v-model="dataForm.med6" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med7">
      <el-input v-model="dataForm.med7" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="med8">
      <el-input v-model="dataForm.med8" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          petid: '',
          userid: '',
          doctid: '',
          status: '',
          sumprice: '',
          dis1: '',
          dis2: '',
          dis3: '',
          dis4: '',
          dis5: '',
          med1: '',
          med2: '',
          med3: '',
          med4: '',
          med5: '',
          med6: '',
          med7: '',
          med8: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          petid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          userid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          doctid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '1.待支付2.已支付3.已过期不能为空', trigger: 'blur' }
          ],
          sumprice: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          dis1: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          dis2: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          dis3: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          dis4: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          dis5: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med1: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med2: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med3: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med4: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med5: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med6: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med7: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          med8: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/product/order/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.petid = data.order.petid
                this.dataForm.userid = data.order.userid
                this.dataForm.doctid = data.order.doctid
                this.dataForm.status = data.order.status
                this.dataForm.sumprice = data.order.sumprice
                this.dataForm.dis1 = data.order.dis1
                this.dataForm.dis2 = data.order.dis2
                this.dataForm.dis3 = data.order.dis3
                this.dataForm.dis4 = data.order.dis4
                this.dataForm.dis5 = data.order.dis5
                this.dataForm.med1 = data.order.med1
                this.dataForm.med2 = data.order.med2
                this.dataForm.med3 = data.order.med3
                this.dataForm.med4 = data.order.med4
                this.dataForm.med5 = data.order.med5
                this.dataForm.med6 = data.order.med6
                this.dataForm.med7 = data.order.med7
                this.dataForm.med8 = data.order.med8
                this.dataForm.createTime = data.order.createTime
                this.dataForm.updateTime = data.order.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/order/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'petid': this.dataForm.petid,
                'userid': this.dataForm.userid,
                'doctid': this.dataForm.doctid,
                'status': this.dataForm.status,
                'sumprice': this.dataForm.sumprice,
                'dis1': this.dataForm.dis1,
                'dis2': this.dataForm.dis2,
                'dis3': this.dataForm.dis3,
                'dis4': this.dataForm.dis4,
                'dis5': this.dataForm.dis5,
                'med1': this.dataForm.med1,
                'med2': this.dataForm.med2,
                'med3': this.dataForm.med3,
                'med4': this.dataForm.med4,
                'med5': this.dataForm.med5,
                'med6': this.dataForm.med6,
                'med7': this.dataForm.med7,
                'med8': this.dataForm.med8,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
