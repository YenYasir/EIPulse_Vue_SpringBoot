<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'EIPulseDoc',
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