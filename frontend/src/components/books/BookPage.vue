<template>
  <div class="container">
    <div class="row">
      <div class="col col-12 col-md-8 offset-md-2">
        <div class="page">
          <h2 class="chapter-title" v-if="index==0">{{chaptertitle}}</h2>
          {{page.content}}
          <label class="page-counter">{{page.page_increment}}</label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BookPage",
  props: {
    page: Object,
    chaptertitle: Object,
    index: Object
  }
}
</script>

<style scoped lang="scss">
.page {
  margin-top: 10px;
  padding: 30px;
  background: #f7f7f7;
  width: 100%;
  min-height: 80vh;
  font-family: "Amiri";
  font-size: 1.25rem;
  text-align: justify;
  border:1px solid $light-grey;
  border-radius:5px;

  .chapter-title {
    font-family: "Amiri" !important;
  }

  .page-counter {
    display: block;
    text-align: center;
    margin-top: 10px;
  }
}
</style>