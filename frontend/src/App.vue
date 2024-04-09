<template>
  <div id="app">
    <router-link to="/"><h1>Car Rental</h1></router-link>
    <TheMenu />
    <router-view />
  </div>
</template>

<script>
import TheMenu from "./components/TheMenu.vue";

export default {
  components: {
    TheMenu,
  },
};
</script>

<style scoped></style>
