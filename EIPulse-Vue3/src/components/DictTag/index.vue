<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.value)">
        <span
          v-if="(item.elTagType == 'default' || item.elTagType == '') && (item.elTagClass == '' || item.elTagClass == null)"
          :key="item.value"
          :index="index"
          :class="item.elTagClass"
          >{{ item.label + " " }}</span
        >
        <el-tag
          v-else
          :disable-transitions="true"
          :key="item.value + ''"
          :index="index"
          :type="item.elTagType === 'primary' ? '' : item.elTagType"
          :class="item.elTagClass"
          >{{ item.label + " " }}</el-tag
        >
      </template>
    </template>
    <template v-if="unmatch && showValue">
      {{ unmatchArray | handleArray }}
    </template>
  </div>
</template>

<script setup>
// // 記錄未匹配的項
const unmatchArray = ref([]);

const props = defineProps({
  // 數據
  options: {
    type: Array,
    default: null,
  },
  // 當前的值
  value: [Number, String, Array],
  // 當未找到匹配的數據時，顯示value
  showValue: {
    type: Boolean,
    default: true,
  },
});

const values = computed(() => {
  if (props.value !== null && typeof props.value !== "undefined") {
    return Array.isArray(props.value) ? props.value : [String(props.value)];
  } else {
    return [];
  }
});

const unmatch = computed(() => {
  unmatchArray.value = [];
  if (props.value !== null && typeof props.value !== "undefined") {
    // 傳入值為非數組
    if (!Array.isArray(props.value)) {
      if (props.options.some((v) => v.value == props.value)) return false;
      unmatchArray.value.push(props.value);
      return true;
    }
    // 傳入值為Array
    props.value.forEach((item) => {
      if (!props.options.some((v) => v.value == item))
        unmatchArray.value.push(item);
    });
    return true;
  }
  // 沒有value不顯示
  return false;
});

function handleArray(array) {
  if (array.length === 0) return "";
  return array.reduce((pre, cur) => {
    return pre + " " + cur;
  });
}
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>