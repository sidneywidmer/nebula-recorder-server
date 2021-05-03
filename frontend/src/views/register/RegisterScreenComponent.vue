<template>
  <div class="container">
    <div class="row">
      <div class="col col-md-6 offset-md-3">
        <form @submit="register">
          <h2>Registrieren</h2>
          <input v-model="user.firstname" placeholder="Vorname (optional)" type="text" name="firstname" class="input"/>
          <input v-model="user.lastname" placeholder="Nachname (optional)" type="text" name="lastname" class="input"/>
          <input v-model="user.email" placeholder="E-Mail" name="email" type="email" class="input"/>
          <input v-model="user.password" placeholder="Passwort" name="password" type="password" class="input"/>
          <br/>
          <br/>
          <button class="blue-button full-width">Registrieren</button>
          <hr/>
          <label>Hast du noch kein Konto?
            <router-link to="login">Jetzt Anmelden</router-link>
          </label>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
//import Vue from "vue";
import axios from "axios";
//import VueAxios from "vue-axios";

export default {
  name: "LoginScreenComponent",
  data() {
    return {
      user: {
        firstname:null,
        lastname:null,
        email:null,
        password:null
      }
    }
  },
  methods: {
    register(e) {
      e.preventDefault();
      axios.post('http://localhost:5000/v1/account/register', this.user);
    }
  }
}
</script>

<style scoped lang="scss">
a {
  color: $blue;
  font-weight: bold;
}
</style>