<template>
  <div>
    <div class="container">
      <div class="row">
        <div class="col col-md-8 offset-md-2">
          <h1>Der Bär und der Fuchs</h1>
          <label class="label">
            <ion-icon name="person-circle-outline"></ion-icon>
            Von
            <router-link to="/">Marjan Popov</router-link>
          </label>
          <p class="description">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
            invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo
            dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
            dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
            clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
          <label class="label">
            <ion-icon name="pricetags-outline"></ion-icon>
            Horror-Krimi - 200 Seiten - In Arbeit</label>
          <hr/>
          <button class="black-border-button">
            <ion-icon name="bookmark-outline"></ion-icon>
            Merken
          </button>
          <router-link :to="'/book/read/' + $route.params.id">
            <button class="blue-button right-button">
              <ion-icon name="glasses-outline"></ion-icon>
              Jetzt lesen
            </button>
          </router-link>
        </div>
      </div>
    </div>
    <div class="container youmayalsolike">
      <h2 class="color-light-grey">
        <ion-icon name="heart"></ion-icon>
        Vielleicht gefällt Dir auch hier etwas...
      </h2>
      <br/>
      <div class="row">
        <div v-for="block in content.body" :key="block._uid" class="col col-md-4">
          <BookCover :is="block.component" :block="block"></BookCover>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BookCover from "@/components/books/BookCover";

export default {
  name: "SingleBook",
  components: {BookCover},
  data() {
    return {
      content: {
        body: [
          {
            _uid: "BUY6Drn9e1",
            component: "BookCover",
            title: "Der Junge im gestreiften Pyjama",
            description: "Eine traurige Geschichte über einen Jungen...",
            author: "Hans-Jörg Schaufelberger",
            image: "https://via.placeholder.com/200x150"
          },
          {
            _uid: "gJZoSLkfZV",
            component: "BookCover",
            title: "Der Bär und der Fuchs",
            description: "Zwei Freunde auf einer Reise durch die Ewigkeit",
            author: "Gisella Werner",
            image: "https://via.placeholder.com/200x150"
          },
          {
            _uid: "X1JAfdsZxy",
            component: "BookCover",
            title: "Die Astrologie und Wissenschaft",
            description: "Planeten, die Erde und die Wissenschaft. Geht das?",
            author: "James D. Brown",
            image: "https://via.placeholder.com/200x150"
          },
          {
            _uid: "X1JAdfadsZxy",
            component: "BookCover",
            title: "Programmieren in C# 101",
            description: "Lernen Sie in wenigen Schritten Programmieren",
            author: "Marjan Popov",
            image: "https://via.placeholder.com/200x150"
          }
        ]
      }
    }
  }
}
</script>

<style scoped lang="scss">
.description {
  font-size: 1.25rem;
}

.label {
  color: $light-grey;
  font-weight: bold;

  a {
    color: $primary;
  }
}

.youmayalsolike {
  margin-top: 100px;
}
</style>