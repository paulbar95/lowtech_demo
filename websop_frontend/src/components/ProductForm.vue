<script>
import axios from "axios";

export default {
  name: "ProductForm",
  data() {
    return {
      product: {
        name: "",
        description: "",
        price: null,
        category: "",
        imageUrl: "",
      },
    };
  },
  methods: {
    async submitForm() {
      try {
        const response = await axios.post("http://localhost:8080/api/products", this.product);
        alert("Product added successfully!");
        this.resetForm();
      } catch (error) {
        console.error("Error adding product:", error);
        alert("Failed to add product. Please try again.");
      }
    },
    resetForm() {
      this.product = {
        name: "",
        description: "",
        price: null,
        category: "",
        imageUrl: "",
      };
    },
  },
};
</script>

<template>
  <div class="product-form">
    <h2>Add a New Product</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="name">Product Name:</label>
        <input v-model="product.name" type="text" id="name" required />
      </div>

      <div class="form-group">
        <label for="description">Description:</label>
        <textarea v-model="product.description" id="description" required></textarea>
      </div>

      <div class="form-group">
        <label for="price">Price:</label>
        <input v-model.number="product.price" type="number" id="price" step="0.01" required />
      </div>

      <div class="form-group">
        <label for="category">Category:</label>
        <input v-model="product.category" type="text" id="category" required />
      </div>

      <div class="form-group">
        <label for="imageUrl">Image URL:</label>
        <input v-model="product.imageUrl" type="url" id="imageUrl" />
      </div>

      <button type="submit">Add Product</button>
    </form>
  </div>
</template>

<style scoped>
.product-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 1rem;
  background: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.product-form h2 {
  text-align: center;
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  display: block;
  width: 100%;
  padding: 0.75rem;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background: #0056b3;
}
</style>