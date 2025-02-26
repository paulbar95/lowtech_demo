<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import config from "@/config.js";

const API_BASE_URL = config.API_BASE_URL;

const orders = ref([]);

const fetchOrders = async () => {
  try {
    const response = await axios.get(API_BASE_URL+"/orders");
    orders.value = response.data;
  } catch (error) {
    console.error("Error fetching orders:", error);
  }
};

const updateOrderStatus = async (orderId, newStatus) => {
  try {
    await axios.put(
        API_BASE_URL+`/orders/${orderId}/status?status=${newStatus}`
    );
    // Finde die Bestellung im orders Array und aktualisiere den Status
    const orderToUpdate = orders.value.find(order => order.id === orderId);
    if (orderToUpdate) {
      orderToUpdate.status = newStatus;
    }
    // Es wird keine Benachrichtigung mehr angezeigt.
  } catch (error) {
    console.error("Error updating order status:", error);
    // Optional kannst du hier einen Fehlerhinweis einbauen.
  }
};

onMounted(fetchOrders);
</script>

<template>
  <div>
    <h1>Order Management</h1>
    <div v-if="orders.length === 0">
      <p>No orders available.</p>
    </div>
    <div v-for="order in orders" :key="order.id" class="order-card">
      <h2>Order #{{ order.id }}</h2>
      <p>Customer Name: {{ order.customerName }}</p>
      <p>Customer E-mail: {{order.customerEmail}}</p>
      <p>Payment Method: {{ order.paymentMethod }}</p>
      <p>Status: {{ order.status }}</p>
      <ul>
        <li v-for="item in order.products" :key="item.id">
          {{ item.product.name }} - {{ item.quantity }} pcs
        </li>
      </ul>
      <div class="status-controls">
        <button @click="updateOrderStatus(order.id, 'PENDING')">Set to Pending</button>
        <button @click="updateOrderStatus(order.id, 'COMPLETED')">Set to Completed</button>
        <button @click="updateOrderStatus(order.id, 'CANCELLED')">Set to Cancelled</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
h2, h1 {
  margin-top: 0;
}

.order-card {
  background: var(--background-white);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}
.order-card:not(:last-child) {
  margin-bottom: 20px;
}

.order-card h3 {
  margin-bottom: 10px;
}

.status-controls {
  display: flex;
  gap: 10px;
}
</style>
