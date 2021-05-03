<template>
  <button class="load-more">
    <ion-icon name="refresh-outline"></ion-icon> Mehr laden
  </button>
</template>

<script>
export default {
name: "LoadMore"
}
</script>

<style scoped lang="scss">
.load-more {
  color: $primary;
  font-size: 1.25rem;
  background: transparent;
  border: none;
  margin: auto;
  display: block;
  cursor: pointer;
}
</style>