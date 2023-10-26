<template>
	<el-form size="small">
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				日，允許的通用字元[, - * / L M]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				不指定
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				周期從
				<el-input-number v-model='cycle01' :min="0" :max="31" /> -
				<el-input-number v-model='cycle02' :min="0" :max="31" /> 日
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				從
				<el-input-number v-model='average01' :min="0" :max="31" /> 號開始，每
				<el-input-number v-model='average02' :min="0" :max="31" /> 日執行一次
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="5">
				每月
				<el-input-number v-model='workday' :min="0" :max="31" /> 號最近的那個工作日
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="6">
				本月最後一天
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="7">
				指定
				<el-select clearable v-model="checkboxList" placeholder="可多選" multiple style="width:100%">
					<el-option v-for="item in 31" :key="item" :value="item">{{item}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>
	</el-form>
</template>

<script>
export default {
	data() {
		return {
			radioValue: 1,
			workday: 1,
			cycle01: 1,
			cycle02: 2,
			average01: 1,
			average02: 1,
			checkboxList: [],
			checkNum: this.$options.propsData.check
		}
	},
	name: 'crontab-day',
	props: ['check', 'cron'],
	methods: {
		// 單選按鈕值變化時
		radioChange() {
			('day rachange');
			if (this.radioValue === 1) {
				this.$emit('update', 'day', '*', 'day');
				this.$emit('update', 'week', '?', 'day');
				this.$emit('update', 'month', '*', 'day');
			} else {
				if (this.cron.hour === '*') {
					this.$emit('update', 'hour', '0', 'day');
				}
				if (this.cron.min === '*') {
					this.$emit('update', 'min', '0', 'day');
				}
				if (this.cron.second === '*') {
					this.$emit('update', 'second', '0', 'day');
				}
			}

			switch (this.radioValue) {
				case 2:
					this.$emit('update', 'day', '?');
					break;
				case 3:
					this.$emit('update', 'day', this.cycle01 + '-' + this.cycle02);
					break;
				case 4:
					this.$emit('update', 'day', this.average01 + '/' + this.average02);
					break;
				case 5:
					this.$emit('update', 'day', this.workday + 'W');
					break;
				case 6:
					this.$emit('update', 'day', 'L');
					break;
				case 7:
					this.$emit('update', 'day', this.checkboxString);
					break;
			}
			('day rachange end');
		},
		// 周期兩個值變化時
		cycleChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'day', this.cycleTotal);
			}
		},
		// 平均兩個值變化時
		averageChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'day', this.averageTotal);
			}
		},
		// 最近工作日值變化時
		workdayChange() {
			if (this.radioValue == '5') {
				this.$emit('update', 'day', this.workday + 'W');
			}
		},
		// checkbox值變化時
		checkboxChange() {
			if (this.radioValue == '7') {
				this.$emit('update', 'day', this.checkboxString);
			}
		},
		// 父組件傳遞的week發生變化觸發
		weekChange() {
			//判斷week值與day不能同時為“?”
			if (this.cron.week == '?' && this.radioValue == '2') {
				this.radioValue = '1';
			} else if (this.cron.week !== '?' && this.radioValue != '2') {
				this.radioValue = '2';
			}
		},
	},
	watch: {
		"radioValue": "radioChange",
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'workdayCheck': 'workdayChange',
		'checkboxString': 'checkboxChange',
	},
	computed: {
		// 計算兩個周期值
		cycleTotal: function () {
			this.cycle01 = this.checkNum(this.cycle01, 1, 31)
			this.cycle02 = this.checkNum(this.cycle02, 1, 31)
			return this.cycle01 + '-' + this.cycle02;
		},
		// 計算平均用到的值
		averageTotal: function () {
			this.average01 = this.checkNum(this.average01, 1, 31)
			this.average02 = this.checkNum(this.average02, 1, 31)
			return this.average01 + '/' + this.average02;
		},
		// 計算工作日格式
		workdayCheck: function () {
			this.workday = this.checkNum(this.workday, 1, 31)
			return this.workday;
		},
		// 計算勾選的checkbox值合集
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str == '' ? '*' : str;
		}
	}
}
</script>
