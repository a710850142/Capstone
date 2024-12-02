<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="userid">
      <el-input v-model="dataForm.userid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="doctid">
      <el-input v-model="dataForm.doctid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="apppointtime">
      <el-input v-model="dataForm.apppointtime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="1.成功2.取消3.过期" prop="status">
      <el-input v-model="dataForm.status" placeholder="1.成功2.取消3.过期"></el-input>
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
          userid: '',
          doctid: '',
          apppointtime: '',
          status: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          userid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          doctid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          apppointtime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '1.成功2.取消3.过期不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/product/appoint/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userid = data.appoint.userid
                this.dataForm.doctid = data.appoint.doctid
                this.dataForm.apppointtime = data.appoint.apppointtime
                this.dataForm.status = data.appoint.status
                this.dataForm.createTime = data.appoint.createTime
                this.dataForm.updateTime = data.appoint.updateTime
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
              url: this.$http.adornUrl(`/product/appoint/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userid': this.dataForm.userid,
                'doctid': this.dataForm.doctid,
                'apppointtime': this.dataForm.apppointtime,
                'status': this.dataForm.status,
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
