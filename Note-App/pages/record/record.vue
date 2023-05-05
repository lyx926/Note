<template>
	<view>
		<!-- <page-head :title="title"></page-head> -->
		<view class="uni-padding-wrap uni-common-mt">
			<!-- <view style="font-size: 12px; color: #666;">注：PC 不支持下拉刷新</view> -->
			<view class="text" v-for="(row,index) in noteList" :key="index"><!-- list - {{num}} -->
				<uni-swipe-action>
					<!-- <uni-swipe-action-item :left-options="options2" :threshold="0" :right-options="options1"
						@click="bindClick">
						<view class="content-box" @click="contentClick">
							<text class="content-text">使用数据填充</text>
						</view>
					</uni-swipe-action-item> -->
					<uni-swipe-action-item @click="bindClick">
						<!-- <template v-slot:left>
							<view class="slot-button">
								<text class="slot-button-text"
									@click="bindClick({position:'left',content:{text:'置顶'}})">置顶</text>
							</view>
						</template> -->
						<view class="content-box" @click="contentClick(row.noteId)">
							<text class="content-text">{{row.noteTitle}}</text>
						</view>
						<template v-slot:right>
							<view class="slot-button"
								@click="bindClick({noteId:row.noteId,position:'right',content:{text:'删除'}})"><text
									class="slot-button-text">删除</text></view>
						</template>
					</uni-swipe-action-item>
					<!-- <uni-swipe-action-item :right-options="options1" @click="bindClick">
						<template v-slot:left>
							<view class="slot-button"><text class="slot-button-text"
									@click="bindClick({position:'left',content:{text:'置顶'}})">置顶</text></view>
						</template>
						<view class="content-box" @click="contentClick">
							<text class="content-text">数据与插槽混合使用</text>
						</view>
					</uni-swipe-action-item> -->
				</uni-swipe-action>
			</view>
			<view class="uni-loadmore" v-if="showLoadMore">{{loadMoreText}}</view>
		</view>
	</view>
</template>
<script>
	import {
		listNote,
		getNote,
		delNote,
		addNote,
		updateNote
	} from "@/api/system/note";
	export default {
		data() {
			return {
				// 总条数
				total: 0,
				// 笔记表格数据
				noteList: [],
				// 查询参数
				queryParams: {
					pageNum: 1,
					pageSize: 20,
					noteTitle: null,
					reminderDate: null,
					noteContent: null,
					noteStatus: null,
					noteType: null,
				},

				title: '下拉刷新 + 加载更多',
				data: [],
				loadMoreText: "加载中...",
				showLoadMore: false,
				max: 0,



				show: false,
				isOpened: 'none',
				options1: [{
					text: '取消置顶'
				}],
				options2: [{
						text: '取消',
						style: {
							backgroundColor: '#007aff'
						}
					},
					{
						text: '确认',
						style: {
							backgroundColor: '#F56C6C'
						}
					}
				],
				swipeList: [{
						options: [{
							text: '添加',
							style: {
								backgroundColor: '#F56C6C'
							}
						}],
						id: 0,
						content: '左滑点击添加新增一条数据'
					},
					{
						id: 1,
						options: [{
								text: '置顶'
							},
							{
								text: '删除',
								style: {
									backgroundColor: 'rgb(255,58,49)'
								}
							}
						],
						content: 'item2'
					},
					{
						id: 2,
						options: [{
								text: '置顶'
							},
							{
								text: '标记为已读',
								style: {
									backgroundColor: 'rgb(254,156,1)'
								}
							},
							{
								text: '删除',
								style: {
									backgroundColor: 'rgb(255,58,49)'
								}
							}
						],
						content: 'item3'
					}
				]

			}
		},
		onLoad() {
			this.initData();
		},
		onShow() {
			this.initData();
		},
		onReady() {
			// 模拟延迟赋值
			setTimeout(() => {
				this.isOpened = 'right';
			}, 1000);

			uni.$on('update', res => {
				this.swipeClick({
					content: {
						text: '添加'
					}
				})
			})
		},
		/* 
		 if (that.noteList.length >= that.total) {
		 	uni.stopPullDownRefresh();
		 }
		 */
		onUnload() {
			// 监听页面卸载
			let that = this
			that.noteList = []
			that.loadMoreText = "加载更多"
			that.showLoadMore = false
		},
		onReachBottom() {
			// 触底触发
			let that = this
			// 数据下标大于等于总条数
			if (that.noteList.length >= that.total) {
				that.loadMoreText = "没有更多数据了!"
				return
			}
			that.showLoadMore = true
			// 下一页方法
			setTimeout(() => {
				that.setListData()
			}, 300)
		},
		onPullDownRefresh() {
			this.initData();
		},
		methods: {

			contentClick(noteId) {
				uni.showToast({
					title: '点击内容',
					icon: 'none'
				})
				this.$tab.reLaunch(`/pages/editor/editor?noteId=${noteId}`)
			},
			bindClick(e) {
				/* uni.showToast({
					title: `点击了${e.position === 'left' ? '左侧' : '右侧'} ${e.content.text}按钮`,
					icon: 'none'
				}); */
				delNote(e.noteId);
				this.noteList = []
				this.queryParams.pageNum = 1
				this.initData();
			},
			setOpened() {
				if (this.isOpened === 'none') {
					this.isOpened = 'left';
					return;
				}
				if (this.isOpened === 'left') {
					this.isOpened = 'right';
					return;
				}
				if (this.isOpened === 'right') {
					this.isOpened = 'none';
					return;
				}
			},
			change(e) {
				this.isOpened = e;
			},
			swipeChange(e, index) {},
			swipeClick(e, index) {
				let {
					content
				} = e;
				if (content.text === '删除') {
					uni.showModal({
						title: '提示',
						content: '是否删除',
						success: res => {
							if (res.confirm) {
								this.swipeList.splice(index, 1);
							} else if (res.cancel) {}
						}
					});
				} else if (content.text === '添加') {
					if (this.swipeList.length < 10) {
						this.swipeList.push({
							id: new Date().getTime(),
							options: [{
									text: '置顶'
								},
								{
									text: '标记为已读',
									style: {
										backgroundColor: 'rgb(254,156,1)'
									}
								},
								{
									text: '删除',
									style: {
										backgroundColor: 'rgb(255,58,49)'
									}
								}
							],
							content: '新增' + new Date().getTime()
						});
						uni.showToast({
							title: `添加了一条数据`,
							icon: 'none'
						});
					} else {
						uni.showToast({
							title: `最多添加十条数据`,
							icon: 'none'
						});
					}
				} else {
					uni.showToast({
						title: `点击了${e.content.text}按钮`,
						icon: 'none'
					});
				}
			},
			initData() {
				let that = this
				// 默认页
				listNote(that.queryParams).then(response => {
					that.noteList = response.rows;
					that.total = response.total;
				});
			},
			setListData() {
				let that = this
				// 页数+1
				that.queryParams.pageNum += 1
				listNote(that.queryParams).then(response => {
					that.total = response.total;
					// 拼接新一页数据
					that.noteList = that.noteList.concat(response.rows);
				});
			}
		}
	}
</script>

<style>
	.uni-loadmore {
		width: 100%;
		display: flex;
		justify-content: center;
	}

	.text {
		margin: 16rpx 0;
		width: 100%;
		background-color: #fff;
		height: 120rpx;
		line-height: 120rpx;
		text-align: center;
		color: #555;
		border-radius: 8rpx;
	}

	.content-box {
		flex: 1;
		/* #ifdef APP-NVUE */
		justify-content: center;
		/* #endif */
		/* height: 44px; */
		/* line-height: 44px; */
		padding: 0 15px;
		position: relative;
		background-color: #fff;
		border-bottom-color: #f5f5f5;
		border-bottom-width: 1px;
		border-bottom-style: solid;
	}

	.content-text {
		font-size: 15px;
	}

	.example-body {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: center;
		padding: 10px 0;
		background-color: #fff;
	}

	.button {
		border-color: #e5e5e5;
		border-style: solid;
		border-width: 1px;
		padding: 4px 8px;
		border-radius: 4px;
	}

	.button-text {
		font-size: 15px;
	}

	.slot-button {
		/* #ifndef APP-NVUE */
		display: flex;
		height: 100%;
		/* #endif */
		flex: 1;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		padding: 0 20px;
		background-color: #ff5a5f;
	}

	.slot-button-text {
		color: #ffffff;
		font-size: 14px;
	}
</style>