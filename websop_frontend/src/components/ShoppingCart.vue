<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const cart = ref([]);
const customerName = ref("");
const customerEmail = ref("");

const loadCart = () => {
  const savedCart = JSON.parse(localStorage.getItem("shoppingCart")) || [];
  cart.value = savedCart;
};

const saveCart = () => {
  localStorage.setItem("shoppingCart", JSON.stringify(cart.value));
};

const increaseQuantity = (productId) => {
  const item = cart.value.find((i) => i.productId === productId);
  if (item) {
    item.quantity++;
    saveCart();
  }
};

const decreaseQuantity = (productId) => {
  const item = cart.value.find((i) => i.productId === productId);
  if (item && item.quantity > 1) {
    item.quantity--;
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
  if (!customerName.value || !customerEmail.value) {
    alert("Please enter your name and email.");
    return;
  }
  const order = {
    customerName: customerName.value,
    customerEmail: customerEmail.value,
    products: cart.value.map((item) => ({ productId: item.productId, quantity: item.quantity })),
  };
  try {
    await axios.post("http://localhost:5000/api/orders", order);
    alert("Order placed successfully!");
    cart.value = [];
    saveCart();
    customerName.value = "";
    customerEmail.value = "";
  } catch (error) {
    console.error("Error during checkout:", error);
    alert("Failed to place the order.");
  }
};

const addProductToCart = (product) => {
  const existingItem = cart.value.find((item) => item.productId === product._id);
  if (existingItem) {
    existingItem.quantity++;
  } else {
    cart.value.push({ productId: product._id, product, quantity: 1 });
  }
  saveCart();
  alert("Product added to cart!");
};

onMounted(loadCart);
</script>

<template>
  <div class="container-box">
    <h2>Shopping Cart</h2>

    <div v-if="cart.length === 0">
      <p>Your cart is empty. Add some products from the catalog!</p>
    </div>

    <div v-else class="cart-list">
      <div v-for="item in cart" :key="item.productId" class="cart-item">
        <h3>{{ item.product.name }}</h3>
        <p>Price: ${{ item.product.price }}</p>
        <p>Category: {{ item.product.category }}</p>
        <p>Total: ${{ (item.quantity * item.product.price).toFixed(2) }}</p>
        <div class="button-group">
          <button class="button-secondary" @click="decreaseQuantity(item.productId)">-</button>
          <span>{{ item.quantity }}</span>
          <button class="button-secondary" @click="increaseQuantity(item.productId)">+</button>
        </div>
        <button class="button-primary" @click="removeFromCart(item.productId)">Remove</button>
      </div>

      <div class="checkout-section">
        <h3>Total Price: ${{ total().toFixed(2) }}</h3>
        <input v-model="customerName" type="text" placeholder="Enter your name" />
        <input v-model="customerEmail" type="email" placeholder="Enter your email" />
        <button class="button-primary" @click="checkout">Checkout</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cart-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.cart-item {
  background: var(--background-light);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
}

.cart-item:hover {
  transform: scale(1.05);
}

.button-group {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 10px 0;
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
