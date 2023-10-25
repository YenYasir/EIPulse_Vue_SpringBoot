<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'EIPulseGitHub',
  data() {
    return {
      url: 'https://github.com/YenYasir/EIPulseinSpringboot'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>