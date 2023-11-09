
<template>
  <div class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog ">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ titleName }}</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <slot></slot>
        </div>
        <div class="modal-footer justify-content-center">
          <button @click="submit" class="btn btn-primary">{{ buttonName }}</button>
          <button type="button" class="btn btn-secondary " data-bs-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props=defineProps({
  titleName:{
    type:String,
    required:true,
  },
  buttonName:{
    type:String,
    required:true,
    default:'送出'
  }
})
const emit = defineEmits(['submit']);
const submit=()=>{
  emit('submit')
}
</script>


<style >

</style>