<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const cart = ref([]);
const customer = ref({ name: "", email: "" });

const loadCart = () => {
  cart.value = JSON.parse(localStorage.getItem("shoppingCart")) || [];
};

const saveCart = () => {
  localStorage.setItem("shoppingCart", JSON.stringify(cart.value));
};

const modifyQuantity = (productId, change) => {
  const item = cart.value.find((i) => i.productId === productId);
  if (item && (item.quantity + change) >= 1) {
    item.quantity += change;
    saveCart();
  }
};

const removeFromCart = (productId) => {
  cart.value = cart.value.filter((item) => item.productId !== productId);
  saveCart();
};

const total = () => {
  return cart.value.reduce((sum, item) => sum + item.quantity * item.product.price, 0);
};

const checkout = async () => {
  if (cart.value.length === 0) {
    alert("Your cart is empty!");
    return;
  }
  if (!customer.value.name || !customer.value.email) {
    alert("Please enter your name and email.");
    return;
  }
  const order = {
    customerName: customer.value.name,
    customerEmail: customer.value.email,
    products: cart.value.map((item) => ({ productId: item.productId, quantity: item.quantity })),
  };
  try {
    await axios.post("http://localhost:5000/api/orders", order);
    alert("Order placed successfully!");
    cart.value = [];
    saveCart();
    customer.value = { name: "", email: "" };
  } catch (error) {
    console.error("Error during checkout:", error);
    alert("Failed to place the order.");
  }
};

onMounted(loadCart);
</script>

<template>
  <div class="container-box">
    <h2>Your Cart</h2>

    <div v-if="cart.length === 0">
      <p>Your cart is empty. Start shopping now!</p>
    </div>

    <div v-else class="cart-list">
      <div v-for="item in cart" :key="item.productId" class="cart-item">
        <div>
          <h3>{{ item.product.name }}</h3>
          <p>Price: ${{ item.product.price }}</p>
          <p>Category: {{ item.product.category }}</p>
          <p>Total: ${{ (item.quantity * item.product.price).toFixed(2) }}</p>
        </div>
        <div class="button-group">
          <button class="button-secondary" @click="modifyQuantity(item.productId, -1)">-</button>
          <span>{{ item.quantity }}</span>
          <button class="button-secondary" @click="modifyQuantity(item.productId, 1)">+</button>
        </div>
        <button class="button-primary" @click="removeFromCart(item.productId)">Remove</button>
      </div>

      <div class="checkout-section">
        <h3>Total: ${{ total().toFixed(2) }}</h3>
        <input v-model="customer.name" type="text" placeholder="Your Name" />
        <input v-model="customer.email" type="email" placeholder="Your Email" />
        <button class="button-primary" @click="checkout">Place Order</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cart-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: var(--background-light);
  padding: 15px;
  border-radius: var(--border-radius);
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}

.button-group {
  display: flex;
  align-items: center;
  gap: 10px;
}

.checkout-section {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.checkout-section input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: var(--border-radius);
}
</style>
