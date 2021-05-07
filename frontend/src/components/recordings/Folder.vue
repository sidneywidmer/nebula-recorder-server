<template>
  <div>
    <router-link :to="'/recording/'">
      <div class="icon">
        <ion-icon name="folder-outline"></ion-icon>
      </div>
      <div class="bookcover">
        <h3>{{item.title}}</h3></div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "Folder",
  props: {
    item: Object
  }
}
</script>

<style scoped lang="scss">
.icon {
  display: inline-block;
  float: left;
  height: 100%;
  width: 100%;
  font-size: 10.5rem;
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