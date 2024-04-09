<template>
  <div>
    <h1>Cars</h1>
    <h3>List of cars</h3>
    <div v-for="car in cars" :key="car.id">
      {{ car.make }} - {{ car.model }} - {{ car.licensePlate }} - {{ car.vin }}
      <button @click="viewCar(car.id)">View</button>
      <button @click="editCar(car)">Edit</button>
      <button @click="deleteCar(car.id)">Delete</button>
    </div>
    <h3>Create/Edit car</h3>
    <form @submit.prevent="saveCar">
      <input type="text" v-model="form.make" placeholder="Make" />
      <input type="text" v-model="form.model" placeholder="Model" />
      <input
        type="text"
        v-model="form.licensePlate"
        placeholder="License Plate"
      />
      <input type="text" v-model="form.vin" placeholder="VIN" />
      <button type="submit">Save</button>
    </form>
  </div>
</template>

<script>
import CarService from "@/service/CarService";

export default {
  data() {
    return {
      cars: [],
      form: {
        id: null,
        make: "",
        model: "",
        licensePlate: "",
        vin: "",
      },
    };
  },
  methods: {
    viewCar(id) {
      this.$router.push({ name: "car", params: { id } });
    },
    fetchCars() {
      CarService.getAll()
        .then(({ data }) => {
          this.cars = data;
        })
        .catch((error) => {
          console.error("There was an error fetching the cars:", error);
        });
    },
    editCar(car) {
      this.form = { ...car };
    },
    saveCar() {
      const { id, ...data } = this.form;
      if (id) {
        CarService.update(id, data).then(() => {
          this.fetchCars();
          this.resetForm();
        });
      } else {
        CarService.create(data).then(() => {
          this.fetchCars();
          this.resetForm();
        });
      }
    },
    deleteCar(id) {
      CarService.delete(id).then(() => {
        this.fetchCars();
      });
    },
    resetForm() {
      this.form = {
        id: null,
        make: "",
        model: "",
        licensePlate: "",
        vin: "",
      };
    },
  },
  created() {
    this.fetchCars();
  },
};
</script>

<style scoped></style>
