<template>
  <div class="container">
    <div class="row">
      <div class="col col-md-6 offset-md-3">
        <form @submit="register">
          <h2>Registrieren</h2>
          <div v-if="issetError" class="alert alert-warning">
            Bitte geben Sie eine gültige E-Mail und ein gültiges Passwort ein.
          </div>
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

export default {
  name: "RegisterScreenComponent",
  data() {
    return {
      user: {
        email: null,
        password: null
      },
      issetError:false
    }
  },
  methods: {
    register: function (e) {
      e.preventDefault();
      this.issetError = false;
      let email = this.user.email;
      let password = this.user.password;
      this.$store
          .dispatch("register", {email, password})
          .then(() => this.$router.push("/register/after"))
          .catch(
              this.issetError = true
          );
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