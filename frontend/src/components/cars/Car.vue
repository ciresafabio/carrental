<template>
  <div>
    <h1>Car Details</h1>
    <div v-if="car">
      <p>Make: {{ car.make }}</p>
      <p>Model: {{ car.model }}</p>
      <p>License Plate: {{ car.licensePlate }}</p>
      <p>VIN: {{ car.vin }}</p>
      <p v-if="totalKilometers">
        <strong> Total Kilometers rented:</strong> {{ totalKilometers }}
      </p>
    </div>
  </div>
</template>

<script>
import CarService from "@/service/CarService";
import RentalService from "@/service/RentalService";

export default {
  data() {
    return {
      car: null,
      totalKilometers: null,
    };
  },
  methods: {
    fetchCar() {
      CarService.get(this.$route.params.id)
        .then(({ data }) => {
          this.car = data;
          this.fetchTotalKilometers();
        })
        .catch((error) => {
          console.error("There was an error fetching the car details:", error);
        });
    },
    fetchTotalKilometers() {
      RentalService.getTotalKilometers(this.$route.params.id)
        .then(({ data }) => {
          this.totalKilometers = data;
        })
        .catch((error) => {
          console.error(
            "There was an error fetching the total kilometers:",
            error
          );
        });
    },
  },
  created() {
    this.fetchCar();
  },
};
</script>

<style scoped></style>
