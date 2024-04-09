import http from "../http";

class CarService {
  getAll() {
    return http.get("/car");
  }

  get(id) {
    return http.get(`/car/${id}`);
  }

  create(data) {
    return http.post("/car", data);
  }

  update(id, data) {
    return http.put(`/car/${id}`, data);
  }

  delete(id) {
    return http.delete(`/car/${id}`);
  }
}

export default new CarService();
