<template>
  <div>
    <div class="container">
      <h2>@gifmaster_1992</h2>
      <router-link to="/account/recordings">
        <button class="small-button right-button black-border-button">
          <ion-icon name="settings-outline"></ion-icon>
          Records bearbeiten
        </button>
      </router-link>
      <p>
        Welcome to my Profile.<br/>
        I'm a #programmer and #guitar player.<br/>
        😄 😁 😆
      </p>
      <hr/>
      <div class="row">
        <div v-for="item in content.trendingItems" :key="item.public_id" class="col col-md-4">
          <RecordingTile :item="item"></RecordingTile>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import RecordingTile from "@/components/recordings/RecordingTile";

export default {
  name: "Account",
  components: {RecordingTile},
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
}
</script>

<style scoped>

</style>