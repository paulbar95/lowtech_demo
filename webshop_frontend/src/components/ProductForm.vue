<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import config from "@/config.js"; 

const API_BASE_URL = config.API_BASE_URL;

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
    await axios.post(API_BASE_URL+"/products", product.value);
    alert("Product added successfully!");
    resetForm();
  } catch (error) {
    console.error("Error adding product:", error);
    alert("Failed to add product. Please try again.");
  }
};

const productImageBucketUrl = ref("");
const fetchProductInfo = async () => {
  try {
    const imageUrl = await axios.get(API_BASE_URL + "/products/imageBucketUrl");
    productImageBucketUrl.value = imageUrl.data;
  } catch (error) {
    console.error("Error fetching product info:", error);
  }
};

onMounted(fetchProductInfo);

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
        <label for="name">Product Name</label>
        <input v-model="product.name" type="text" id="name" placeholder="Enter product name" required />
      </div>

      <div class="form-group">
        <label for="description">Description</label>
        <textarea v-model="product.description" id="description" placeholder="Enter product description" required></textarea>
      </div>

      <div class="form-group">
        <label for="price">Price (Cents)</label>
        <input v-model.number="product.price" type="number" id="price" placeholder="Enter price" min="0" step="0.01" required />
      </div>

      <div class="form-group">
        <label for="category">Category</label>
        <input v-model="product.category" type="text" id="category" placeholder="Enter category" required />
      </div>

      <div class="form-group">
        <label for="imageUrl">Image</label>
        <div>
          <input v-model="product.imageUrl" type="text" id="imageUrl" placeholder="Enter image" />
          <a :href="productImageBucketUrl" target="_blank">AWS Image Bucket</a>
        </div>
      </div>
      <button type="submit" class="button-primary">Add Product</button>
    </form>
  </div>
</template>

<style scoped>
h2 {
  margin-top: 0;
}
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
.form-group input {
  max-width: 300px;
}
.form-group textarea {
  resize: vertical;
  min-height: 100px;
}
.form-group > div {
  display: grid;
  grid-template-columns: auto max-content;
}
.form-group > div > a {
  align-self: center;
  justify-self: self-end;
  width: max-content;
  text-decoration: none;
}
</style>