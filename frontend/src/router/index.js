import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import SingleBook from "../views/SingleBook";
import ReadBook from "@/views/ReadBook";
import LoginScreenComponent from "@/views/login/LoginScreenComponent";
import RegisterScreenComponent from "@/views/register/RegisterScreenComponent";
import Account from "@/views/account/Account";
import ManageRecordings from "@/views/account/ManageRecordings";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/book/:id",
    name: "bookDetail",
    component: SingleBook
  },
  {
    path :"/book/read/:id",
    name: "bookRead",
    component: ReadBook
  },
  {
    path: "/login",
    name: "login",
    component: LoginScreenComponent
  },
  {
    path: "/register",
    name: "register",
    component: RegisterScreenComponent
  },
  {
    path: "/account",
    name: "account",
    component: Account
  },
  {
    path: "/account/recordings",
    name: "manage recordings",
    component: ManageRecordings
  }
];

const router = new VueRouter({
  routes
});

export default router;
