<template>
	<el-form size='small'>
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				月，允許的通用字元[, - * /]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				周期從
				<el-input-number v-model='cycle01' :min="1" :max="12" /> -
				<el-input-number v-model='cycle02' :min="1" :max="12" /> 月
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				從
				<el-input-number v-model='average01' :min="1" :max="12" /> 月開始，每
				<el-input-number v-model='average02' :min="1" :max="12" /> 月月執行一次
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				指定
				<el-select clearable v-model="checkboxList" placeholder="可多選" multiple style="width:100%">
					<el-option v-for="item in 12" :key="item" :value="item">{{item}}</el-option>
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
			cycle01: 1,
			cycle02: 2,
			average01: 1,
			average02: 1,
			checkboxList: [],
			checkNum: this.check
		}
	},
	name: 'crontab-month',
	props: ['check', 'cron'],
	methods: {
		// 單選按鈕值變化時
		radioChange() {
			if (this.radioValue === 1) {
				this.$emit('update', 'month', '*');
				this.$emit('update', 'year', '*');
			} else {
				if (this.cron.day === '*') {
					this.$emit('update', 'day', '0', 'month');
				}
				if (this.cron.hour === '*') {
					this.$emit('update', 'hour', '0', 'month');
				}
				if (this.cron.min === '*') {
					this.$emit('update', 'min', '0', 'month');
				}
				if (this.cron.second === '*') {
					this.$emit('update', 'second', '0', 'month');
				}
			}
			switch (this.radioValue) {
				case 2:
					this.$emit('update', 'month', this.cycle01 + '-' + this.cycle02);
					break;
				case 3:
					this.$emit('update', 'month', this.average01 + '/' + this.average02);
					break;
				case 4:
					this.$emit('update', 'month', this.checkboxString);
					break;
			}
		},
		// 周期兩個值變化時
		cycleChange() {
			if (this.radioValue == '2') {
				this.$emit('update', 'month', this.cycleTotal);
			}
		},
		// 平均兩個值變化時
		averageChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'month', this.averageTotal);
			}
		},
		// checkbox值變化時
		checkboxChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'month', this.checkboxString);
			}
		}
	},
	watch: {
		"radioValue": "radioChange",
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'checkboxString': 'checkboxChange'
	},
	computed: {
		// 計算兩個周期值
		cycleTotal: function () {
			this.cycle01 = this.checkNum(this.cycle01, 1, 12)
			this.cycle02 = this.checkNum(this.cycle02, 1, 12)
			return this.cycle01 + '-' + this.cycle02;
		},
		// 計算平均用到的值
		averageTotal: function () {
			this.average01 = this.checkNum(this.average01, 1, 12)
			this.average02 = this.checkNum(this.average02, 1, 12)
			return this.average01 + '/' + this.average02;
		},
		// 計算勾選的checkbox值合集
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str == '' ? '*' : str;
		}
	}
}
</script>
