<template>
  <router-link :to="'/recording/' + item.public_id">
    <img style="width:100%;" src="https://via.placeholder.com/350x250"/>
    <div class="icon">
      <ion-icon v-if="item.completed == true" name="play-outline"></ion-icon>
      <ion-icon v-if="item.completed == false" name="image-outline"></ion-icon>
    </div>
    <div class="bookcover">
      <h3>{{ item.title }}</h3>
      <p> #funny #lol{{/* item.description | truncate(100, '...')*/}}</p>
      <label class="label"><ion-icon name="person-circle-outline"></ion-icon> von <router-link to="/">Hans Muster{{item.author}}</router-link></label>
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
.bookcover {
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
