<template>
	<view class="w-100">
		<!-- 自定义navbar -->
		<tickler-navbar :isShowRightIcon="false" :isShowLeft="false" title="用户详情" rightText="保存" leftIconColor="#fff"
		@rightClick="save"></tickler-navbar>
		<view class="flex flex-column align-center">
			<!-- 头像 -->
			<view>
				<u-avatar src="/static/icon/default-avatar.png" shape="square" size="100" class="u-avatar"/>
			</view>
			<!-- 用户信息 -->
			<view class="w-90">
				<u--form :model="userInfo" :rules="rules" ref="userForm" labelWidth="auto">
					<u-form-item label="昵称 :" prop="nickname" borderBottom required>
						<u--input v-model="userInfo.nickname" border="none"></u--input>
					</u-form-item>
					<u-form-item label="手机号 :" borderBottom>
						<u--input v-model="userInfo.mobile" border="none" disabled disabledColor="#ffffff"></u--input>
					</u-form-item>
					<u-form-item label="邮箱 :" prop="email" borderBottom required>
						<u--input v-model="userInfo.email" border="none"></u--input>
					</u-form-item>
					<u-form-item label="绑定微信 :" borderBottom>
						<u--input v-model="tip" border="none" readonly suffixIcon="weixin-fill"></u--input>
					</u-form-item>
					<u-form-item label="绑定QQ :" borderBottom>
						<u--input v-model="tip" border="none" readonly suffixIcon="qq-fill"></u--input>
					</u-form-item>
				</u--form>
			</view>
		</view>
	</view>
	
</template>

<script>
	import {userUpdateCurrentInfo} from '@/api/user.js'
	export default {
		data() {
			return {
				userInfo:{},
				tip:"暂缓开发",
				rules:{
					nickname:[{
						required: true,
						message: '请输入昵称',
						trigger: ['blur', 'change']
					},{
						min: 2,
						max: 250,
						message: '最少2个字,最大250个字',
						trigger: ['blur', 'change']
					}],
					email:[{
						required: true,
						message: '请输入邮箱',
						trigger: ['blur', 'change']
					},{
						validator: (rule, value, callback) => {
							return uni.$u.test.email(value);
						},
						message: '邮箱格式不正确',
						trigger: ['change','blur'],
					}],
				}
			}
		},
		onLoad(e) {
			this.userInfo=this.$store.state.userInfo;
		},
		methods: {
			save(){
				this.$refs.userForm.validate().then(res => {
					let nickName=this.userInfo.nickname;
					let email=this.userInfo.email;
					userUpdateCurrentInfo({"nickName":nickName,"email":email}).then(rsp=>{
						if(2000==rsp.code){
							uni.$u.toast('更新成功')
							this.$store.commit('setStateAttr', {"userInfo":this.userInfo});
							uni.navigateBack()
						}
					})
				})
			}
		}
	}
</script>

<style>

</style>
