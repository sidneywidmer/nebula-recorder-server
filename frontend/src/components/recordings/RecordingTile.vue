<template>
  <router-link :to="'/recording/' + item.id">
    <img style="width:100%;" :src="item.url"/>
    <div class="icon">
      <ion-icon name="play-outline"></ion-icon>
    </div>
    <div class="recordingcover">
      <h3>{{ item.name }}</h3>
      <label class="label"><ion-icon name="person-circle-outline"></ion-icon></label>
    </div>
  </router-link>
</template>

<script>
export default {
  name: "RecordingTile",
  props: {
    item: Object
  }
};
</script>

<style scoped lang="scss">
.icon {
  display: inline-block;
  float: left;
  height: 100%;
  width: 60px;
  font-size: 2.5rem;
  color: #c9c9c9;
  padding-top: 10px;
}
.recordingcover {
  margin-bottom: 50px;
  h3 {
    color: $shady-blue;
  }
  &:hover {
    h3 {
      color: $blue;
    }
  }
}
p {
  margin: 0 0 10px 0;
  font-size: 1.25rem;
}
.image {
  width: 100%;
}
.label {
  color: $light-grey;
  font-weight: bold;
  a {
    color: $shady-grey;
  }
}
</style>
