import Axios from "axios";

const http = Axios.create({});
//TODO to env variable
http.defaults.baseURL = "http://localhost:8080/api";

export default http;
