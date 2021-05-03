<template>
  <div class="container">
    <div class="row">
      <div class="col col-md-6 offset-md-3">
        <form @submit="login">
          <h2>Anmelden</h2>
          <input
            v-model="user.email"
            placeholder="Benutzername"
            type="email"
            class="input"
          />
          <input
            v-model="user.password"
            placeholder="Passwort"
            type="password"
            class="input"
          />
          <br />
          <br />
          <button class="blue-button full-width">Login</button>
          <hr />
          <label
            >Hast du noch kein Konto?
            <router-link to="register">Jetzt registrieren</router-link>
          </label>
        </form>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "LoginScreenComponent",
  data() {
    return {
      user: {
        email: null,
        password: null
      }
    };
  },
  methods: {
    login: function(e) {
      e.preventDefault();
      localStorage.setItem("token","test");
      this.$router.push('/')
      /*let email = this.user.email;
      let password = this.user.password;
      this.$store
        .dispatch("login", { email, password })
        .then(() => this.$router.push("/"))
        .catch(err => console.log(err));*/
    }
  }
};
</script>

<style scoped lang="scss">
a {
  color: $blue;
  font-weight: bold;
}
</style>
