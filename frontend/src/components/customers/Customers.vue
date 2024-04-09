<template>
  <div>
    <h1>Customers</h1>
    <h3>List of rentals</h3>
    <div v-for="customer in customers" :key="customer.id">
      {{ customer.name }} - {{ customer.email }}
      <button @click="editCustomer(customer)">Edit</button>
      <button @click="deleteCustomer(customer.id)">Delete</button>
    </div>
    <h3>Create/Edit customer</h3>
    <form @submit.prevent="saveCustomer">
      <input type="text" v-model="form.name" placeholder="Name" />
      <input type="email" v-model="form.email" placeholder="Email" />
      <button type="submit">Save</button>
    </form>
  </div>
</template>

<script>
import CustomerService from "@/service/CustomerService";

export default {
  data() {
    return {
      customers: [],
      form: {
        id: null,
        name: "",
        email: "",
      },
    };
  },
  methods: {
    fetchCustomers() {
      CustomerService.getAll()
        .then(({ data }) => {
          this.customers = data;
        })
        .catch((error) => {
          console.error("There was an error fetching the customers:", error);
        });
    },
    editCustomer(customer) {
      this.form = { ...customer };
    },
    saveCustomer() {
      const { id, ...data } = this.form;
      if (id) {
        CustomerService.update(id, data).then(() => {
          this.fetchCustomers();
          this.resetForm();
        });
      } else {
        CustomerService.create(data).then(() => {
          this.fetchCustomers();
          this.resetForm();
        });
      }
    },
    deleteCustomer(id) {
      CustomerService.delete(id).then(() => {
        this.fetchCustomers();
      });
    },
    resetForm() {
      this.form = { id: null, name: "", email: "" };
    },
  },
  created() {
    this.fetchCustomers();
  },
};
</script>

<style scoped></style>
