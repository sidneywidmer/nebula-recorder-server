import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import LoginScreenComponent from "@/views/login/LoginScreenComponent";
import RegisterScreenComponent from "@/views/register/RegisterScreenComponent";
import Account from "@/views/account/Account";
import ManageRecordings from "@/views/account/ManageRecordings";
import AfterRegisterView from "@/views/register/AfterRegisterView";

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
    path: "/register/after",
    name: "after Register",
    component: AfterRegisterView
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
