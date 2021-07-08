<template>
  <div>
    <div class="container">
      <div class="row">
        <div class="col col-md-6 offset-md-3">
          <img style="width:100%;" :src="this.content.singleRecording.url"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "RecordingDetail",
  data() {
    return {
      content: {
        singleRecording: {},
        id: router.currentRoute.params.id
      }
    }
  },
  mounted() {
    axios.get('/api/recording/' + this.content.id, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
        .then(response => this.content.singleRecording = response.data)
        .catch(err => console.log(err.message))
  },
}
</script>

<style scoped>

</style>