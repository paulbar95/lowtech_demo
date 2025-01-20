<script>
import axios from "axios";

export default {
  name: "OrderManagement",
  data() {
    return {
      orders: [], // Stores the list of orders
    };
  },
  methods: {
    // Fetch all orders from the backend
    async fetchOrders() {
      try {
        const response = await axios.get("http://localhost:8080/api/orders");
        this.orders = response.data;
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    },

    // Update the status of an order
    async updateOrderStatus(orderId, newStatus) {
      try {
        await axios.put(`http://localhost:8080/api/orders/${orderId}/status`, null, {
          params: { status: newStatus },
        });
        alert("Order status updated successfully!");
        this.fetchOrders(); // Refresh orders after update
      } catch (error) {
        console.error("Error updating order status:", error);
      }
    },
  },
  created() {
    this.fetchOrders(); // Fetch orders when the component is created
  },
};
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
.order-card {
  border: 1px solid #ccc;
  margin-bottom: 20px;
  padding: 15px;
  border-radius: 5px;
}
.status-controls button {
  margin-right: 10px;
}
</style>