<script>
export default {
  props:{
    empId:{
      type:[String,Number],
      default:null,
    }
  },
  name: "index"
}
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-primary " >
    <div class="container-fluid">
      <a class="navbar-brand text-white " href="#">Eipulse</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
          <a class="nav-link active text-white" aria-current="page" href="#">上班</a>
          <a class="nav-link text-white" >忘記密碼</a>
          <a class="nav-link text-white" href="#">福委會</a>
        </div>
      </div>
    </div>
  </nav>
  <h3>{{empId}}</h3>
</template>

<style scoped>

</style>