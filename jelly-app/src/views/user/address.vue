<template>
  <div>
    <!-- 收货地址显示-->
    <div class="table-responsive-lg mb-3 m-content">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">收货人</th>
            <th scope="col">所在地区</th>
            <th scope="col">详细地址</th>
            <th scope="col">联系电话</th>
            <th scope="col">邮编</th>
            <th scope="col">默认地址</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="addr in userAddress" :key="addr.id">
            <td> {{addr.consignee}} </td>
            <td> {{addr.address}} </td>
            <td> {{addr.detail}} </td>
            <td> {{addr.telephone}} </td>
            <td> {{addr.postcode}} </td>
            <td>
              <span v-if="addr.isDefault">
                默认地址
              </span>
              <el-button v-else type="primary" size="mini">
                设置为默认
              </el-button>
            </td>
            <td>
              <el-button type="primary" size="mini">
                编辑
              </el-button>
              <el-button type="error" size="mini">
                删除
              </el-button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <!-- 新增表单 -->
    <div class="form-wrap m-content">
      <el-form :model="addrForm" :rules="rules" ref="addrForm" label-width="100px" class="addr-form">
        <el-form-item label="地址信息" prop="address">
          <el-cascader v-model="addrForm.address" :options="options" 
            @change="handleChange" class="w-100">
          </el-cascader>
        </el-form-item>
        <el-form-item label="详细地址" prop="detail">
          <el-input type="textarea" v-model="addrForm.detail"></el-input>
        </el-form-item>
        <el-form-item label="邮政编码">
          <el-input v-model="addrForm.postcode"></el-input>
        </el-form-item>
        <el-form-item label="收货人姓名" prop="consignee">
          <el-input v-model="addrForm.consignee"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="telephone">
          <el-input v-model="addrForm.telephone"></el-input>
        </el-form-item>
        <div style="padding-left: 100px">
          <el-checkbox label="设置为默认地址" v-model="addrForm.isDefault"></el-checkbox>
        </div>
        <el-form-item label="">
          <el-button type="primary" @click="submitForm">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator'
import { Form } from 'element-ui'
import { getUserAddress, addAddress } from '@/api/user/address'
import { copyOf } from '@/common/utils/ObjectUtil'
import { State, Getter } from 'vuex-class'

@Component
export default class Address extends Vue {
  @Ref('addrForm') private refAddrForm!: Form
  @Getter('userId') private userId!: string;
  
  private userAddress: any = []
  private addrForm = {
    address: [],
    detail: '',
    postcode: '',
    consignee: '',
    telephone: '',
    isDefault: false
  }
  private rules = {
    address: [
      { required: true, message: '请选择地址信息', trigger: 'change' },
    ],
    detail: [
      { required: true, message: '请输入详细地址', trigger: 'change' },
      { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'change' },
    ],
    consignee: [
      { required: true, message: '请输入收货人姓名', trigger: 'change' },
    ],
    telephone: [
      { required: true, message: '请输入手机号码', trigger: 'change' },
    ]
  }
  private options = [
    { value: '福建省', label: '福建省', 
      children: [
        { value: '福州市', label: '福州市',
            children: [
              { value: '鼓楼区', label: '鼓楼区',
                children: [
                  { value: '鼓东街道', label: '鼓东街道' }, 
                  { value: '鼓西街道', label: '鼓西街道' }, 
                ]
              },
              { value: '台江区', label: '台江区',
                children: [
                  { value: '新港街道', label: '新港街道' }, 
                  { value: '上海街道', label: '上海街道' }, 
                ]
              },
            ]
        }, 
        { value: '厦门市', label: '厦门市',
            children: [
              { value: '集美区', label: '集美区',
                children: [
                  { value: '集美街道', label: '集美街道' }, 
                  { value: '侨英街道', label: '侨英街道' }, 
                ]
              },
              { value: '思明区', label: '思明区',
                children: [
                  { value: '夏港街道', label: '夏港街道' }, 
                  { value: '中华街道', label: '中华街道' }, 
                ]
              },
            ]
        }, 
      ]
    },
  ]
  // method
  private handleChange(value: []) {
    console.log(value)
  }
  // 提交表单
  private submitForm() {
    this.refAddrForm.validate((valid: any) => {
      if (valid) {
        const form = copyOf(this.addrForm)
        form.address = form.address.join(' ')
        form.userId = this.userId
        form.id = 2
        addAddress(form).then((res: any) => {
          this.$log.info('添加收货地址', res)
          this.userAddress.push(res.data)
        })
      } else {
        console.log('error submit!!')
        return false;
      }
    })
  }
  // 获取用户收货地址
  private getUserAddress() {
    getUserAddress(this.$route.params.id).then((res: any) => {
      this.userAddress = res.data
    })
  }
  // 初始化函数
  private mounted() {
    this.getUserAddress()
  }
}
</script>

<style scoped lang="scss">
.form-wrap {
  .addr-form {
    width: 600px;
    padding: 30px 0 10px;
  }
}
</style>
