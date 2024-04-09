import http from "../http";

class RentalService {
  getAll() {
    return http.get("/rental");
  }

  get(id) {
    return http.get(`/rental/${id}`);
  }

  create(data) {
    return http.post("/rental", data);
  }

  update(id, data) {
    return http.put(`/rental/${id}`, data);
  }

  delete(id) {
    return http.delete(`/rental/${id}`);
  }

  getTotalKilometers(carId) {
    return http.get(`/rental/car/${carId}/total-kilometers`);
  }
}

export default new RentalService();
