<script setup>
import { ref } from "vue";
import axios from "axios";

const product = ref({
  name: "",
  description: "",
  price: null,
  category: "",
  imageUrl: ""
});

const submitForm = async () => {
  if (!product.value.name || !product.value.description || !product.value.price || !product.value.category) {
    alert("Please fill in all required fields.");
    return;
  }
  try {
    await axios.post("http://localhost:5000/api/products", product.value);
    alert("Product added successfully!");
    resetForm();
  } catch (error) {
    console.error("Error adding product:", error);
    alert("Failed to add product. Please try again.");
  }
};

const resetForm = () => {
  product.value = {
    name: "",
    description: "",
    price: null,
    category: "",
    imageUrl: ""
  };
};
</script>

<template>
  <div class="container-box">
    <h2>Add a New Product</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="name">Product Name:</label>
        <input v-model="product.name" type="text" id="name" placeholder="Enter product name" required />
      </div>

      <div class="form-group">
        <label for="description">Description:</label>
        <textarea v-model="product.description" id="description" placeholder="Enter product description" required></textarea>
      </div>

      <div class="form-group">
        <label for="price">Price:</label>
        <input v-model.number="product.price" type="number" id="price" placeholder="Enter price" min="0" step="0.01" required />
      </div>

      <div class="form-group">
        <label for="category">Category:</label>
        <input v-model="product.category" type="text" id="category" placeholder="Enter category" required />
      </div>

      <div class="form-group">
        <label for="imageUrl">Image URL:</label>
        <input v-model="product.imageUrl" type="url" id="imageUrl" placeholder="Enter image URL (optional)" />
      </div>

      <button type="submit" class="button-primary">Add Product</button>
    </form>
  </div>
</template>

<style scoped>
.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group textarea {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: var(--border-radius);
}

.form-group textarea {
  resize: vertical;
  min-height: 100px;
}
</style>