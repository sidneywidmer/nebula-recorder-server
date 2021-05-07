<template>
  <div>
    <div class="container">
      <h2>Deine Records</h2><br/>
      <h2 class="color-shady-grey">
        <ion-icon name="list-outline"></ion-icon>
        Unsortierte
      </h2>
      <hr/>
      <div class="row">
        <div v-for="item in content.trendingItems.slice(0,3)" :key="item.public_id" class="col col-md-3">
          <RecordingTile :item="item"></RecordingTile>
        </div>
      </div>
      <h2 class="color-shady-grey">
        <ion-icon name="folder-outline"></ion-icon>
        Sortierte
      </h2>
      <hr/>
      <div class="row">
        <div v-for="item in content.trendingItems.slice(0,4)" :key="item.public_id" class="col col-md-4">
          <Folder :item="item"></Folder>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import RecordingTile from "@/components/recordings/RecordingTile";
import Folder from "@/components/recordings/Folder";

export default {
name: "ManageRecordings",
  components: {Folder, RecordingTile},
  data() {
    return {
      content: {
        trendingItems: []
      }
    }
  },
  mounted() {
    axios.get('https://jsonplaceholder.cypress.io/todos/?limit=3')
        .then(response => this.content.trendingItems = response.data)
        .catch(err => console.log(err.message))
  }
}
</script>

<style scoped>

</style>