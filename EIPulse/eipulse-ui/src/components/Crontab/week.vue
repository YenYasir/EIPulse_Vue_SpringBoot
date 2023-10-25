<template>
	<el-form size='small'>
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				周，允許的通用字元[, - * / L #]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				不指定
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				周期從星期
				<el-input-number v-model='cycle01' :min="1" :max="7" /> -
				<el-input-number v-model='cycle02' :min="1" :max="7" />
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				第
				<el-input-number v-model='average01' :min="1" :max="4" /> 周的星期
				<el-input-number v-model='average02' :min="1" :max="7" />
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="5">
				本月最後一個星期
				<el-input-number v-model='weekday' :min="1" :max="7" />
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="6">
				指定
				<el-select clearable v-model="checkboxList" placeholder="可多選" multiple style="width:100%">
					<el-option v-for="(item,index) of weekList" :key="index" :value="index+1">{{item}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

	</el-form>
</template>

<script>
export default {
	data() {
		return {
			radioValue: 2,
			weekday: 1,
			cycle01: 1,
			cycle02: 2,
			average01: 1,
			average02: 1,
			checkboxList: [],
			weekList: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
			checkNum: this.$options.propsData.check
		}
	},
	name: 'crontab-week',
	props: ['check', 'cron'],
	methods: {
		// 單選按鈕值變化時
		radioChange() {
			if (this.radioValue === 1) {
				this.$emit('update', 'week', '*');
				this.$emit('update', 'year', '*');
			} else {
				if (this.cron.month === '*') {
					this.$emit('update', 'month', '0', 'week');
				}
				if (this.cron.day === '*') {
					this.$emit('update', 'day', '0', 'week');
				}
				if (this.cron.hour === '*') {
					this.$emit('update', 'hour', '0', 'week');
				}
				if (this.cron.min === '*') {
					this.$emit('update', 'min', '0', 'week');
				}
				if (this.cron.second === '*') {
					this.$emit('update', 'second', '0', 'week');
				}
			}
			switch (this.radioValue) {
				case 2:
					this.$emit('update', 'week', '?');
					break;
				case 3:
					this.$emit('update', 'week', this.cycle01 + '-' + this.cycle02);
					break;
				case 4:
					this.$emit('update', 'week', this.average01 + '#' + this.average02);
					break;
				case 5:
					this.$emit('update', 'week', this.weekday + 'L');
					break;
				case 6:
					this.$emit('update', 'week', this.checkboxString);
					break;
			}
		},
		// 根據互斥事件，更改radio的值

		// 周期兩個值變化時
		cycleChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'week', this.cycleTotal);
			}
		},
		// 平均兩個值變化時
		averageChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'week', this.averageTotal);
			}
		},
		// 最近工作日值變化時
		weekdayChange() {
			if (this.radioValue == '5') {
				this.$emit('update', 'week', this.weekday + 'L');
			}
		},
		// checkbox值變化時
		checkboxChange() {
			if (this.radioValue == '6') {
				this.$emit('update', 'week', this.checkboxString);
			}
		},
	},
	watch: {
		"radioValue": "radioChange",
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'weekdayCheck': 'weekdayChange',
		'checkboxString': 'checkboxChange',
	},
	computed: {
		// 計算兩個周期值
		cycleTotal: function () {
			this.cycle01 = this.checkNum(this.cycle01, 1, 7)
			this.cycle02 = this.checkNum(this.cycle02, 1, 7)
			return this.cycle01 + '-' + this.cycle02;
		},
		// 計算平均用到的值
		averageTotal: function () {
			this.average01 = this.checkNum(this.average01, 1, 4)
			this.average02 = this.checkNum(this.average02, 1, 7)
			return this.average01 + '#' + this.average02;
		},
		// 最近的工作日（格式）
		weekdayCheck: function () {
			this.weekday = this.checkNum(this.weekday, 1, 7)
			return this.weekday;
		},
		// 計算勾選的checkbox值合集
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str == '' ? '*' : str;
		}
	}
}
</script>
