<template>
  <div>
    <h1>Rentals</h1>
    <h3>Currently rented cars</h3>
    <div>
      {{ currentlyRentedCarsCount }}
    </div>
    <h3>List of rentals</h3>
    <div v-for="rental in rentals" :key="rental.id">
      Customer: {{ rental.customer.email }} - Car:
      {{ rental.car.licensePlate }} - {{ rental.startDateTime }} -
      {{ rental.endDateTime }} - {{ rental.distanceInKilometers }}km
      <button @click="editRental(rental)">Edit</button>
      <button @click="deleteRental(rental.id)">Delete</button>
    </div>
    <h3>Create/Edit rental</h3>
    <form @submit.prevent="saveRental">
      <select v-model="form.customerId">
        <option value="">--Please choose a customer--</option>
        <option
          v-for="customer in customers"
          :key="customer.id"
          :value="customer.id"
        >
          {{ customer.name }}
        </option>
      </select>
      <select v-model="form.carId" label="Car">
        <option value="">--Please choose a car--</option>
        <option v-for="car in cars" :key="car.id" :value="car.id">
          {{ car.make }} - {{ car.model }}
        </option>
      </select>
      <!-- TODO validate start before end -->
      <input
        type="datetime-local"
        v-model="form.startDateTime"
        placeholder="Start Date Time"
      />
      <input
        type="datetime-local"
        v-model="form.endDateTime"
        placeholder="End Date Time"
      />
      <input
        type="number"
        v-model="form.distanceInKilometers"
        placeholder="Distance in Kilometers"
      />
      <button type="submit">Save</button>
    </form>
  </div>
</template>

<script>
import RentalService from "@/service/RentalService";
import CustomerService from "@/service/CustomerService";
import CarService from "@/service/CarService";

export default {
  data() {
    return {
      rentals: [],
      customers: [],
      cars: [],
      form: {
        id: null,
        customerId: "",
        carId: "",
        startDateTime: "",
        endDateTime: "",
        distanceInKilometers: 0,
      },
    };
  },
  computed: {
    currentlyRentedCarsCount() {
      const now = new Date();
      return this.rentals.filter(
        (rental) =>
          new Date(rental.startDateTime) <= now &&
          new Date(rental.endDateTime) > now
      ).length;
    },
  },
  methods: {
    fetchRentals() {
      RentalService.getAll()
        .then(({ data }) => {
          this.rentals = data;
        })
        .catch((error) => {
          console.error("There was an error fetching the rentals:", error);
        });
    },
    fetchCustomers() {
      CustomerService.getAll()
        .then(({ data }) => {
          this.customers = data;
        })
        .catch((error) => {
          console.error("There was an error fetching customers:", error);
        });
    },
    fetchCars() {
      CarService.getAll()
        .then(({ data }) => {
          this.cars = data;
        })
        .catch((error) => {
          console.error("There was an error fetching cars:", error);
        });
    },
    editRental(rental) {
      this.form = {
        id: rental.id,
        customerId: rental.customer.id,
        carId: rental.car.id,
        startDateTime: rental.startDateTime,
        endDateTime: rental.endDateTime,
        distanceInKilometers: rental.distanceInKilometers,
      };
    },
    isCarBooked(carId, startDateTime, endDateTime) {
      return this.rentals.some(
        (rental) =>
          rental.car.id === carId &&
          ((new Date(startDateTime) >= new Date(rental.startDateTime) &&
            new Date(startDateTime) < new Date(rental.endDateTime)) ||
            (new Date(endDateTime) > new Date(rental.startDateTime) &&
              new Date(endDateTime) <= new Date(rental.endDateTime)))
      );
    },
    isCustomerBooked(customerId, startDateTime, endDateTime) {
      return this.rentals.some(
        (rental) =>
          rental.customer.id === customerId &&
          ((new Date(startDateTime) >= new Date(rental.startDateTime) &&
            new Date(startDateTime) < new Date(rental.endDateTime)) ||
            (new Date(endDateTime) > new Date(rental.startDateTime) &&
              new Date(endDateTime) <= new Date(rental.endDateTime)))
      );
    },
    validateRental() {
      const { carId, customerId, startDateTime, endDateTime } = this.form;
      if (!carId || !customerId || !startDateTime || !endDateTime) {
        alert("Please fill in all the fields.");
        return false;
      }
      if (new Date(startDateTime) >= new Date(endDateTime)) {
        alert("Start date must be before end date.");
        return false;
      }
      if (this.isCarBooked(carId, startDateTime, endDateTime)) {
        alert("This car is already booked for the selected timeframe.");
        return false;
      }
      if (this.isCustomerBooked(customerId, startDateTime, endDateTime)) {
        alert("This customer has an overlapping booking.");
        return false;
      }
      return true;
    },
    saveRental() {
      if (!this.validateRental()) {
        return;
      }
      const { id, ...data } = this.form;
      if (id) {
        RentalService.update(id, data)
          .then(() => {
            this.fetchRentals();
            this.resetForm();
          })
          .catch((error) => {
            console.error("There was an error updating the rental:", error);
          });
      } else {
        RentalService.create(data)
          .then(() => {
            this.fetchRentals();
            this.resetForm();
          })
          .catch((error) => {
            console.error("There was an error creating the rental:", error);
          });
      }
    },
    deleteRental(id) {
      RentalService.delete(id).then(() => {
        this.fetchRentals();
      });
    },
    resetForm() {
      this.form = {
        id: null,
        customerId: "",
        carId: "",
        startDateTime: "",
        endDateTime: "",
        distanceInKilometers: 0,
      };
    },
  },
  created() {
    this.fetchRentals();
    this.fetchCars();
    this.fetchCustomers();
  },
};
</script>

<style scoped></style>
