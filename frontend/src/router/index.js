import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import LoginScreenComponent from "@/views/login/LoginScreenComponent";
import RegisterScreenComponent from "@/views/register/RegisterScreenComponent";
import AfterRegisterView from "@/views/register/AfterRegisterView";
import RecordingDetail from "@/views/RecordingDetail";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
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
    path: "/recording/:id",
    name: "detail recording",
    component: RecordingDetail
  }
];

const router = new VueRouter({
  routes
});

export default router;
