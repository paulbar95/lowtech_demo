<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const orders = ref([]);

const fetchOrders = async () => {
  try {
    const response = await axios.get("http://localhost:5000/api/orders");
    orders.value = response.data;
  } catch (error) {
    console.error("Error fetching orders:", error);
  }
};

const updateOrderStatus = async (orderId, newStatus) => {
  try {
    await axios.patch(`http://localhost:5000/api/orders/${orderId}`, { status: newStatus });
    alert("Order status updated successfully!");
    fetchOrders();
  } catch (error) {
    console.error("Error updating order status:", error);
  }
};

onMounted(fetchOrders);
</script>

<template>
  <div class="container-box">
    <h2>Order Management</h2>

    <div v-if="orders.length === 0">
      <p>No orders available.</p>
    </div>

    <div v-else class="order-list">
      <div v-for="order in orders" :key="order._id" class="order-card">
        <h3>Order #{{ order._id }}</h3>
        <p><strong>Customer:</strong> {{ order.customerName }} ({{ order.customerEmail }})</p>
        <p><strong>Status:</strong> {{ order.status }}</p>

        <ul>
          <li v-for="item in order.products" :key="item.productId">
            {{ item.productName }} - {{ item.quantity }} pcs
          </li>
        </ul>

        <div class="button-group">
          <button class="button-secondary" @click="updateOrderStatus(order._id, 'PENDING')">Set to Pending</button>
          <button class="button-primary" @click="updateOrderStatus(order._id, 'COMPLETED')">Set to Completed</button>
          <button class="button-secondary" @click="updateOrderStatus(order._id, 'CANCELLED')">Set to Cancelled</button>
        </div>
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
