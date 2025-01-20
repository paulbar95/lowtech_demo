<script>
import axios from "axios";

export default {
  name: "ShoppingCart",
  data() {
    return {
      cart: [],
      customerName: "", // Field for customer name
      customerEmail: "", // Field for customer email
    };
  },
  computed: {
    total() {
      return this.cart.reduce(
          (sum, item) => sum + item.quantity * item.product.price,
          0
      );
    },
  },
  methods: {
    loadCart() {
      const savedCart = JSON.parse(localStorage.getItem("shoppingCart")) || [];
      this.cart = savedCart;
    },
    saveCart() {
      localStorage.setItem("shoppingCart", JSON.stringify(this.cart));
    },
    increaseQuantity(productId) {
      const item = this.cart.find((i) => i.productId === productId);
      if (item) {
        item.quantity++;
        this.saveCart();
      }
    },
    decreaseQuantity(productId) {
      const item = this.cart.find((i) => i.productId === productId);
      if (item && item.quantity > 1) {
        item.quantity--;
        this.saveCart();
      }
    },
    removeFromCart(productId) {
      this.cart = this.cart.filter((item) => item.productId !== productId);
      this.saveCart();
    },
    async checkout() {
      if (this.cart.length === 0) {
        alert("Your cart is empty!");
        return;
      }

      if (!this.customerName || !this.customerEmail) {
        alert("Please enter your name and email address.");
        return;
      }

      const order = {
        customerName: this.customerName,
        customerEmail: this.customerEmail,
        products: this.cart.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };

      try {
        await axios.post("http://localhost:8080/api/orders", order);
        alert("Order placed successfully!");
        this.cart = [];
        this.saveCart();
        this.customerName = ""; // Clear name field
        this.customerEmail = ""; // Clear email field
      } catch (error) {
        console.error("Error during checkout:", error);
        alert("Failed to place the order.");
      }
    },
  },
  created() {
    this.loadCart();
  },
};
</script>

<template>
  <div>
    <h1>Shopping Cart</h1>
    <div v-if="cart.length === 0">
      <p>Your cart is empty. Add some products from the catalog!</p>
    </div>
    <div v-else>
      <div
          v-for="item in cart"
          :key="item.productId"
          class="cart-item"
      >
        <h2>{{ item.product.name }}</h2>
        <p>Price: {{ item.product.price }} €</p>
        <p>Category: {{ item.product.category }}</p>
        <p>Total: {{ (item.quantity * item.product.price).toFixed(2) }} €</p>
        <div class="quantity-controls">
          <button @click="decreaseQuantity(item.productId)">-</button>
          <span>{{ item.quantity }}</span>
          <button @click="increaseQuantity(item.productId)">+</button>
        </div>
        <button @click="removeFromCart(item.productId)">Remove</button>
      </div>
      <div class="checkout">
        <h2>Total Price: {{ total.toFixed(2) }} €</h2>
        <div class="customer-info">
          <input
              type="text"
              v-model="customerName"
              placeholder="Enter your name"
          />
          <input
              type="email"
              v-model="customerEmail"
              placeholder="Enter your email"
          />
        </div>
        <button @click="checkout">Checkout</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cart-item {
  border: 1px solid #ccc;
  padding: 15px;
  margin-bottom: 10px;
}
.quantity-controls {
  display: flex;
  align-items: center;
  gap: 10px;
}
.checkout {
  margin-top: 20px;
  text-align: right;
}
.customer-info {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}
.customer-info input {
  margin-bottom: 10px;
  padding: 8px;
  font-size: 16px;
}
</style>
