<template>
  <div id="app">
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-4-grid@3.4.0/css/grid.min.css"
      crossorigin="anonymous"
    />
    <div id="nav">
      <div class="container">
        <div class="row">
          <div class="col col-md-2">
            <router-link to="/">
              <div class="logo">Nebula</div>
            </router-link>
          </div>
          <div class="col col-md-10">
            <ul class="nav">
              <li v-if="!isLoggedIn">
                <router-link to="/login">
                  <ion-icon name="person-outline"></ion-icon>
                  Anmelden
                </router-link>
              </li>
              <li v-if="isLoggedIn">
                <a @click="logout">
                  Abmelden
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <router-view />
  </div>
</template>

<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Amiri&family=Poppins&display=swap");

body,
h1,
h2,
h3 {
  font-family: "Poppins", sans-serif !important;
  color: black;
  margin: 0;
}

h1,
h2,
h3,
h4,
h5,
h6 {
  width: 100%;
}

p {
  font-family: "Amiri", serif;
}

a {
  text-decoration: none !important;
  text-underline: none;
  color: black;
}

#logo {
  width: 120px;
  margin-top: 10px;
}

.logo {
  font-family: "Amiri" !important;
  font-size: 2.3rem;
}

#nav {
  height: 70px;
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 20px;
  box-shadow: -1px -7px 19px 0px rgba(0, 0, 0, 0.35);
  -webkit-box-shadow: -1px -7px 19px 0px rgba(0, 0, 0, 0.35);
  -moz-box-shadow: -1px -7px 19px 0px rgba(0, 0, 0, 0.35);
}

ul.nav {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  display: inline-block;
  float: right;

  li {
    float: left;

    a {
      display: block;
      text-align: center;
      padding: 14px 16px;
      text-decoration: none;
      height: 70px;
      padding-top: 22px;
    }
  }
}

hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 1px solid $light-grey;
  margin: 1em 0;
  padding: 0;
}

ion-icon {
  position: relative;
  display: inline-block;
  float: left;
  margin-top: 0.3rem;
  margin-right: 0.3rem;
}
</style>

<script>
export default {
  created: function() {
    this.$http.interceptors.response.use(undefined, function(err) {
      return new Promise(function() {
        console.log("error", err.status);
        if (
            err.response.status === 401 &&
            err.response.config &&
            !err.response.config.__isRetryRequest
        ) {
          this.$store.dispatch("logout");
        }
        throw err;
      });
    });
  },
  computed: {
    isLoggedIn: function() {
      return this.$store.getters.isLoggedIn;
    }
  },
  methods: {
    logout: function() {
      this.$store.dispatch("logout").then(() => {
        this.$router.push("/login");
      });
    }
  }
};
</script>
