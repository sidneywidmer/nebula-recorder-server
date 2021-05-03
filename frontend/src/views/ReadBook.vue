<template>
  <div>
    <div class="container">
      <div class="row">
        <div class="col col-md-8 offset-md-2">
          <h2 class="book-title">{{ book.title }}</h2>
          <button class="black-border-button small-button right-button">
            <ion-icon name="book-outline"></ion-icon>
            Lesemodus starten
          </button>
        </div>
      </div>
    </div>
    <div v-for="chapter in book.output_chapters" :key="chapter.title">
      <BookPage v-for="(page, index) in chapter.pages" :key="page.page_increment" :index="index" :chaptertitle="chapter.title"
                :page="page"></BookPage>
    </div>
  </div>
</template>

<script>
import BookPage from "@/components/books/BookPage";
import router from "@/router";
import axios from "axios";

export default {
  name: "ReadBook",
  components: {BookPage},
  data() {
    return {
      book: {},
      public_id: router.currentRoute.params.id
    }
  },
  mounted() {
    axios.get('http://localhost:5000/v1/book/' + this.public_id)
        .then(response => this.book = response.data)
        .catch(err => console.log(err.message))
  }
}
</script>

<style scoped lang="scss">
.book-title {
  display: inline;
}
</style>