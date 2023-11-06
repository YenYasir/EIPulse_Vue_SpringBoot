<template>

  <li>
    <!-- First Dropdown item -->
    <a class="nav-link text-warning dropdown-toggle" data-bs-toggle="collapse" :href="'#' + menuId"><i :class="'bi bi-'+iconName"></i>
      {{ title }}
    </a>
    <div class="collapse text-white" :id="menuId">
      <ul class="nav flex-column">
        <slot></slot>
        <li class="nav-item" v-for="item in items" :key="item.name">
          <router-link :to="item.path ? item.path :'/'" class="nav-link  text-white" >{{ item.name }}</router-link>
        </li>
      </ul>
    </div>
  </li>
</template>
<script>

export default {
  name: "DropDown",
  props:{
    title:{
      type:String,
      required:true,
    },
    items:{
      type:Array,
      required: true
    },
    menuId:{
      type:String,
      required:true
    },
    iconName:{
      type:String,
      required:true
    }
  }
}
</script>



<style scoped>

</style>