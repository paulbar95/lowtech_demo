<script setup>
import { ref, onMounted } from "vue";
import { formatter } from "./PriceFormatter.js"
import axios from "axios";
import config from "@/config.js";

const API_BASE_URL = config.API_BASE_URL;

const inventory = ref([]);
const products = ref([]);
const updatedQuantities = ref({});
const newProductId = ref("");
const newProductQuantity = ref(0);

const fetchInventory = async () => {
  try {
    const response = await axios.get(API_BASE_URL+"/inventory");
    inventory.value = response.data;
    inventory.value.forEach(item => {
      updatedQuantities.value[item.id] = item.quantity;
    });
  } catch (error) {
    console.error("Error fetching inventory:", error);
  }
};

const fetchProducts = async () => {
  try {
    const response = await axios.get(API_BASE_URL+"/products");
    products.value = response.data;
  } catch (error) {
    console.error("Error fetching products:", error);
  }
};

const updateQuantity = async (id) => {
  console.log("MY ID:", id);
  try {
    const quantity = updatedQuantities.value[id];
    await axios.put(API_BASE_URL+`/inventory/${id}?quantity=${quantity}`);
    fetchInventory();
  } catch (error) {
    console.error("Error updating quantity:", error);
  }
};

const addInventoryEntry = async () => {
  console.log("Add ID, before:", newProductId.value, "Quantity:", newProductQuantity.value);
  if (!newProductId.value || newProductQuantity.value <= 0) {
    alert("Please select a product and enter a valid quantity.");
    return;
  }
  try {
    await axios.post(API_BASE_URL+"/inventory",
        new URLSearchParams({
          productId: newProductId.value,
          quantity: newProductQuantity.value
        })
    );
    newProductId.value = "";
    newProductQuantity.value = 0;
    fetchInventory();
  } catch (error) {
    console.error("Error adding inventory entry:", error);
  }
};

onMounted(() => {
  fetchInventory();
  fetchProducts();
});
</script>

<template>
  <div class="container-box">
    <h2>Inventory Management</h2>

    <!-- Add New Inventory Entry -->
    <div class="add-item-form">
      <select v-model="newProductId">
        <option value="">Select a Product</option>
        <option v-for="product in products" :key="product.id" :value="product.id">
          {{ product.name }}
        </option>
      </select>
      <input v-model.number="newProductQuantity" type="number" min="1" placeholder="Quantity" />
      <button class="button-primary" @click="addInventoryEntry">Add to Inventory</button>
    </div>

    <!-- Inventory List -->
    <div class="inventory-list">
      <div v-for="item in inventory" :key="item.id" class="inventory-item">
        <h3>{{ item.product.name }}</h3>
        <p>{{ item.product.description }}</p>
        <p>Category: {{ item.product.category }}</p>
        <p>Price: {{ formatter.format(item.product.price / 100) }}</p>
        <p>Stock: {{ item.quantity }}</p>
        <div class="button-group">
          <input v-model.number="updatedQuantities[item.id]" type="number" min="0" />
          <button class="button-primary" @click="updateQuantity(item.id)">Update Quantity</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
h1, h2, h3 {
  margin-top: 0;
}

.inventory-item {
  background: var(--background-white);
  padding: 15px;
  border-radius: var(--border-radius);
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}

.inventory-item:not(:last-child) {
  margin-bottom: 20px;
}

.button-group {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}

.add-item-form {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.add-item-form select,
.add-item-form input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: var(--border-radius);
}
</style>