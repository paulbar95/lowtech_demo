let API_BASE_URL = "http://localhost:8080/api"; // Default value

const isNullOrEmpty = (str) => !str || str.trim() === "";

if (!isNullOrEmpty(import.meta.env.VITE_API_BASE_URL)) {
  API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
} else if (!isNullOrEmpty(import.meta.env.VUE_APP_API_BASE_URL)) {
  API_BASE_URL = import.meta.env.VUE_APP_API_BASE_URL;
}

export default {
  API_BASE_URL,
};
