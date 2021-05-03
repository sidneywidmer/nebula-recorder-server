<template>
  <div>
      <div class="hero">
        <div class="container">
          <div class="row">
            <div class="col col-md-5 text">
              <h2>Screenrecording direkt in die <span class="color-light-primary">Cloud.</span></h2>
              <p>Aufnehmen wann und wo du willst und alles automatisch in die Cloud hochladen. Organisiere deine Aufnahmen.</p>
              <button class="white-border-button">Jetzt anmelden</button>
              <button class="white-button">Durchstöbern</button>
            </div>
            <div class="col col-md-7 text">

            </div>
          </div>
        </div>
      </div>
    <br/>
    <div class="container">
      <h2 class="color-shady-grey">
        <ion-icon name="trending-up-outline"></ion-icon>
        Community Records
      </h2>
      <br/>
      <div class="row">
        <div v-for="item in content.trendingItems" :key="item.public_id" class="col col-md-4">
          <RecordingTile :item="item"></RecordingTile>
        </div>
      </div>
      <LoadMore></LoadMore>
    </div>
    <div class="container">
      <hr/>
    </div>
    <div class="container">
      <h2 class="color-shady-blue">
        <ion-icon name="person-outline"></ion-icon>
        Für dich persönlich
      </h2>
      <br/>
      <!--
      <div class="row">
        <div v-for="block in content.body" :key="block._uid" class="col col-md-4">
          <BookCover :is="block.component" :block="block"></BookCover>
        </div>
      </div>-->
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import RecordingTile from "@/components/recordings/RecordingTile";
import LoadMore from "@/components/utility/LoadMore";
import axios from "axios";

export default {
  name: "Home",
  components: {
    LoadMore,
    RecordingTile
  },
  data() {
    return {
      content: {
        trendingItems: []
      }
    }
  },
  mounted() {
    axios.get('https://jsonplaceholder.cypress.io/todos/')
        .then(response => this.content.trendingItems = response.data)
        .catch(err => console.log(err.message))
  }
};
</script>

<style lang="scss">
.hero {
  margin-top: -20px;
  background: $shady-blue;
  height: 400px;
  h2, p {
    color: white;
  }
  p {
    font-size: 1.25rem;
    line-height:1.5rem;
    text-align: justify;
  }
  .text {
    margin-top: 90px;
  }
}
</style>
