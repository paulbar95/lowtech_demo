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
    await axios.patch(API_BASE_URL+`/orders/${orderId}`, { status: newStatus });
    alert("Order status updated successfully!");
    fetchOrders();
  } catch (error) {
    console.error("Error updating order status:", error);
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
      <p>Customer: {{ order.customerName }} ({{ order.customerEmail }})</p>
      <p>Payment Method: {{ order.paymentMethod}}</p>
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
.order-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.order-card {
  background: var(--background-light);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}

.order-card h3 {
  margin-bottom: 10px;
}

.button-group {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}
</style>
