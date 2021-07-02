<template>
  <div>
    <div class="hero" v-if="!isLoggedIn">
      <div class="container">
        <div class="row">
          <div class="col col-md-5 text">
            <h2>Screenrecording direkt in die <span class="color-light-primary">Cloud.</span></h2>
            <p>Aufnehmen wann und wo du willst und alles automatisch in die Cloud hochladen.</p>
            <router-link to="/login">
              <button class="white-border-button">Login</button>
            </router-link>
            <router-link to="/register">
              <button class="white-button">Registrieren</button>
            </router-link>
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
        Deine Aufnahmen
      </h2>
      <br/>
      <div class="row" v-if="isLoggedIn">
        <div v-for="item in content.nebulaRecordings" :key="item.id" class="col col-md-4">
          <RecordingTile :item="item"></RecordingTile>
        </div>
      </div>
      <div class="row" v-if="!isLoggedIn">
        Bitte melde dich an um eine List deiner Nebula Recordings zu sehen.
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import RecordingTile from "@/components/recordings/RecordingTile";
import axios from "axios";

export default {
  name: "Home",
  components: {
    RecordingTile
  },
  data() {
    return {
      content: {
        nebulaRecordings: []
      }
    }
  },
  mounted() {
    axios.get('https://nebula.sidney.dev/api/recording/list', {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
        .then(response => this.content.nebulaRecordings = response.data)
        .catch(err => console.log(err.message))
  },
  computed: {
    isLoggedIn: function () {
      return this.$store.getters.isLoggedIn;
    }
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
    line-height: 1.5rem;
    text-align: justify;
  }

  .text {
    margin-top: 90px;
  }
}
</style>
