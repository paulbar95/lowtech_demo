<template>
  <div>
    <h1>Shopping Cart</h1>
    <div v-if="cart.length === 0">
      <p>Your cart is empty. Add some products from the catalog!</p>
    </div>
    <div v-else>
      <!-- Anzeige der Produkte im Warenkorb -->
      <div v-for="item in cart" :key="item.productId" class="cart-item">
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

      <!-- Checkout-Bereich -->
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
        <!-- Auswahl der Zahlungsmethode -->
        <div class="payment-method">
          <h3>Select Payment Method</h3>
          <label>
            <input type="radio" value="PAYPAL" v-model="paymentMethod" />
            PayPal
          </label>
          <label>
            <input type="radio" value="KLARNA" v-model="paymentMethod" />
            Klarna
          </label>
          <label>
            <input type="radio" value="CREDITCARD" v-model="paymentMethod" />
            Credit Card
          </label>
        </div>
        <button @click="checkout">Checkout</button>
      </div>
    </div>

    <!-- Login Popup Modal (simuliert den Login, bevor die Bestellung abgeschickt wird) -->
    <div v-if="showLogin" class="modal-overlay">
      <div class="modal">
        <h2>Login</h2>
        <p>Please login to confirm your order</p>
        <input type="text" v-model="loginUsername" placeholder="Username" />
        <input type="password" v-model="loginPassword" placeholder="Password" />
        <button @click="performLogin">Login</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref, onMounted } from "vue";

export default {
  name: "ShoppingCart",
  data() {
    return {
      cart: [],
      customerName: "",
      customerEmail: "",
      paymentMethod: "", // Hier wird der ausgewählte Wert (z.B. "PAYPAL") gespeichert
      showLogin: false,
      loginUsername: "",
      loginPassword: "",
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
    checkout() {
      if (this.cart.length === 0) {
        alert("Your cart is empty!");
        return;
      }
      if (!this.customerName || !this.customerEmail) {
        alert("Please enter your name and email address.");
        return;
      }
      if (!this.paymentMethod) {
        alert("Please select a payment method.");
        return;
      }
      // Zeige das Login-Popup an
      this.showLogin = true;
    },
    performLogin() {
      // Simuliere einen erfolgreichen Login (Eingaben werden ignoriert)
      this.showLogin = false;
      this.submitOrder();
    },
    async submitOrder() {
      // Erstelle das Order-Objekt inklusive des Zahlungsmethoden-Werts
      const order = {
        customerName: this.customerName,
        customerEmail: this.customerEmail,
        paymentMethod: this.paymentMethod, // Dieser Wert wird an das Backend übergeben
        products: this.cart.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };
      console.log("order:", order.products[0])

      try {
        await axios.post("http://localhost:8080/api/orders", order);
        alert("Order placed successfully!");
        // Leere den Warenkorb und die Eingabefelder
        this.cart = [];
        this.saveCart();
        this.customerName = "";
        this.customerEmail = "";
        this.paymentMethod = "";
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

// onMounted(this.loadCart);
</script>

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
.customer-info input {
  margin-bottom: 10px;
  padding: 8px;
  font-size: 16px;
}
.payment-method {
  margin-bottom: 20px;
}
.payment-method label {
  margin-right: 15px;
  font-weight: bold;
}

/* Modal styles for login popup */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
  text-align: center;
}
.modal input {
  width: 90%;
  padding: 8px;
  margin-bottom: 10px;
  font-size: 16px;
}
.modal button {
  padding: 8px 16px;
  font-size: 16px;
}
</style>
