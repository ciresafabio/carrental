import Vue from "vue";
import VueRouter from "vue-router";
import CustomersView from "../views/CustomersView.vue";
import CarsView from "../views/CarsView.vue";
import CarView from "../views/CarView.vue";
import RentalsView from "../views/RentalsView.vue";
import HomeView from "../views/HomeView.vue";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/customers",
      name: "customers",
      component: CustomersView,
    },
    {
      path: "/cars",
      name: "cars",
      component: CarsView,
    },
    {
      path: "/car/:id",
      name: "car",
      component: CarView,
      props: true,
    },
    {
      path: "/rentals",
      name: "rentals",
      component: RentalsView,
    },
  ],
});

export default router;
