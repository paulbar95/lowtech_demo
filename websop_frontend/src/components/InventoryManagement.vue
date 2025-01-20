<script>
import axios from "axios";

export default {
  name: "InventoryManagement",
  data() {
    return {
      inventory: [],
      products: [],
      updatedQuantities: {},
      newProductId: "",
      newProductQuantity: 0,
    };
  },
  methods: {
    async fetchInventory() {
      try {
        const response = await axios.get("http://localhost:8080/api/inventory");
        this.inventory = response.data;
        // Populate updatedQuantities for all inventory items
        this.inventory.forEach(
            (item) => (this.updatedQuantities[item.id] = item.quantity)
        );
      } catch (error) {
        console.error("Error fetching inventory:", error);
      }
    },
    async fetchProducts() {
      try {
        const response = await axios.get("http://localhost:8080/api/products");
        this.products = response.data;
      } catch (error) {
        console.error("Error fetching products:", error);
      }
    },
    async updateQuantity(id) {
      try {
        const quantity = this.updatedQuantities[id];
        await axios.put(`http://localhost:8080/api/inventory/${id}`, null, {
          params: { quantity },
        });
        alert("Quantity updated successfully!");
        this.fetchInventory(); // Refresh inventory after update
      } catch (error) {
        console.error("Error updating quantity:", error);
      }
    },
    async addInventoryEntry() {
      if (!this.newProductId || this.newProductQuantity <= 0) {
        alert("Please select a product and enter a valid quantity.");
        return;
      }
      try {
        await axios.post("http://localhost:8080/api/inventory", null, {
          params: {
            productId: this.newProductId,
            quantity: this.newProductQuantity,
          },
        });
        alert("New inventory entry added successfully!");
        this.fetchInventory(); // Refresh inventory after adding
      } catch (error) {
        console.error("Error adding inventory entry:", error);
      }
    }
  },
  created() {
    this.fetchInventory();
    this.fetchProducts();
  },
};
</script>

<template>
  <div>
    <h1>Inventory Management</h1>
    <!-- Inventory List -->
    <div class="inventory-list">
      <div
          v-for="item in inventory"
          :key="item.id"
          class="inventory-item"
      >
        <h2>{{ item.product.name }}</h2>
        <p>Category: {{ item.product.category }}</p>
        <p>Description: {{ item.product.description }}</p>
        <p>Price: {{ item.product.price }}</p>
        <p>Stock: {{ item.quantity }}</p>
        <!-- Update Quantity -->
        <div class="quantity-controls">
          <input
              v-model.number="updatedQuantities[item.id]"
              type="number"
              min="0"
          />
          <button @click="updateQuantity(item.id)">
            Update Quantity
          </button>
        </div>
      </div>
    </div>

    <!-- Add New Inventory Entry -->
    <div class="add-inventory">
      <h2>Add New Inventory Entry</h2>
      <select v-model="newProductId">
        <option value="">Select a Product</option>
        <option
            v-for="product in products"
            :key="product.id"
            :value="product.id"
        >
          {{ product.name }}
        </option>
      </select>
      <input
          v-model.number="newProductQuantity"
          type="number"
          placeholder="Enter Quantity"
          min="1"
      />
      <button @click="addInventoryEntry">
        Add to Inventory
      </button>
    </div>
  </div>
</template>

<style scoped>
.inventory-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
.inventory-item {
  border: 1px solid #ccc;
  padding: 15px;
  width: 300px;
}
.quantity-controls {
  margin-top: 10px;
}
.add-inventory {
  margin-top: 20px;
}
</style>