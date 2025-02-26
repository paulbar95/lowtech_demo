<template>
  <div class="container-box">
    <h2>Shopping Cart</h2>
    <div v-if="cart.length === 0">
      <p>Your cart is empty. Add some products from the catalog!</p>
    </div>
    <div v-else>
      <!-- Anzeige der Produkte im Warenkorb -->
      <div
          v-for="item in cart"
          :key="item.productId"
          class="cart-item"
      >
        <div class="cart-item-content">
          <div>
            <h3>{{ item.product.name }}</h3>
            <p>Price: {{ (item.product.price / 100).toFixed(2) }}€</p>
            <p>Category: {{ item.product.category }}</p>
          </div>
          <img :src="`${item.image}`" :alt="item.product.name" />
        </div>
        <div>
          <div class="quantity-controls">
            <button class="button-primary" @click="decreaseQuantity(item.productId)">-</button>
            <span>{{ item.quantity }}</span>
            <button class="button-primary" @click="increaseQuantity(item.productId)">+</button>
            <button class="button-primary" @click="removeFromCart(item.productId)">Remove</button>
          </div>
          <p>Total: {{ ((item.quantity * item.product.price) / 100).toFixed(2) }}€</p>
        </div>
      </div>

      <!-- Kundendaten auf der Hauptseite -->
      <div class="customer-info-main">
        <h2>Customer Information</h2>
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

      <!-- Auswahl der Zahlungsmethode auf der Hauptseite -->
      <div class="payment-selection">
        <h2>Payment Information</h2>
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

      <!-- Checkout-Button -->
      <button class="trigger-checkout button-primary" @click="openCheckoutModal">
        Checkout
      </button>
    </div>

    <!-- Checkout-Popup Modal -->
    <div v-if="showCheckoutModal" class="modal-overlay">
      <div class="modal checkout-modal">

        <!-- Gemeinsamer Titel und Gesamtbetrag -->
        <h2>Total Price: {{ (total / 100).toFixed(2) }}€</h2>

        <!-- PayPal-Block: ähnliches Layout wie bei PayPal -->
        <div v-if="paymentMethod === 'PAYPAL'" class="paypal-login">
          <!-- PayPal-Logo (Platzhalter-Link) -->
          <img
              class="paypal-logo"
              src="https://www.paypalobjects.com/webstatic/icon/pp258.png"
              alt="PayPal Logo"
          />
          <h3 class="paypal-header">Pay with PayPal</h3>
          <p class="paypal-subtext">Enter your PayPal email and password</p>

          <input
              type="email"
              v-model="paypalEmail"
              placeholder="Email or mobile number"
          />
          <input
              type="password"
              v-model="paypalPassword"
              placeholder="Password"
          />

          <a class="forgot-link" href="#">
            Forgot password?
          </a>

          <button class="paypal-login-button" @click="confirmCheckout">
            Log In
          </button>

          <div class="or-separator">
            <span>or</span>
          </div>

          <button class="paypal-create-account-button">
            Create an account
          </button>
        </div>

        <!-- Klarna-Block -->
        <div
            v-else-if="paymentMethod === 'KLARNA'"
            class="klarna-container"
        >
          <!-- Titel und Untertitel wie im Screenshot -->
          <h2 class="klarna-bank-title">
            LowTech GmbH
          </h2>
          <p class="klarna-subheading">
            Sign in with your online banking details.
          </p>

          <!-- Login & Passwort Felder -->
          <div class="klarna-login-fields">
            <label for="klarnaLogin">Login</label>
            <input
                id="klarnaLogin"
                type="text"
                v-model="klarnaUsername"
                placeholder="Login"
            />

            <label for="klarnaPassword">Passwort</label>
            <input
                id="klarnaPassword"
                type="password"
                v-model="klarnaPassword"
                placeholder="Passwort"
            />
          </div>

          <!-- Hinweistexte analog zum Screenshot -->
          <p class="klarna-note">
            Enter your banking PIN, not the PIN of your debit/credit card.
          </p>
          <p class="klarna-note">
            After entering your login details, we check whether your account covers the amount
            to be transferred (verification of sufficient funds) and whether any transactions
            with Sofort you issued from your account in the last 30 days are currently open.
          </p>
          <a href="#" class="klarna-privacy">
            Our Privacy policy applies
          </a>

          <!-- "Next"-Button -->
          <button class="klarna-next-button" @click="confirmCheckout">
            Next
          </button>
        </div>

        <!-- Credit-Card-Block -->
        <div v-else-if="paymentMethod === 'CREDITCARD'">
          <p>You have selected <strong>Credit Card</strong>.</p>
          <div class="creditcard-info">
            <input
                type="text"
                v-model="cardNumber"
                placeholder="Card Number"
            />
            <input
                type="text"
                v-model="cardExpiry"
                placeholder="Expiry Date (MM/YY)"
            />
            <input
                type="text"
                v-model="cardCVV"
                placeholder="CVV"
            />
          </div>
          <button class="checkout-button" @click="confirmCheckout">
            Pay with Credit Card
          </button>
        </div>

        <!-- Cancel-Button (schließt das Modal) -->
        <button class="cancel-button" @click="cancelCheckout">
          Cancel
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import config from "@/config.js";

const API_BASE_URL = config.API_BASE_URL;

export default {
  name: "ShoppingCart",
  data() {
    return {
      cart: [],
      customerName: "",
      customerEmail: "",
      paymentMethod: "",
      showCheckoutModal: false,

      // PayPal-Felder
      paypalEmail: "",
      paypalPassword: "",

      // Klarna-Felder
      klarnaUsername: "",
      klarnaPassword: "",

      // Kreditkarten-Felder
      cardNumber: "",
      cardExpiry: "",
      cardCVV: "",
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
      this.cart = JSON.parse(localStorage.getItem("shoppingCart")) || [];
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
    openCheckoutModal() {
      if (this.cart.length === 0) {
        alert("Your cart is empty!");
        return;
      }
      if (!this.paymentMethod) {
        alert("Please select a payment method.");
        return;
      }
      if (!this.customerName || !this.customerEmail) {
        alert("Please enter your name and email address.");
        return;
      }
      // Öffnet das Modal mit dem jeweiligen Zahlungs-Block
      this.showCheckoutModal = true;
    },
    cancelCheckout() {
      this.showCheckoutModal = false;
    },
    confirmCheckout() {
      // Zusätzliche Validierung pro Zahlungsmethode
      if (this.paymentMethod === "PAYPAL") {
        if (!this.paypalEmail || !this.paypalPassword) {
          alert("Please enter your PayPal email and password.");
          return;
        }
      } else if (this.paymentMethod === "KLARNA") {
        if (!this.klarnaUsername || !this.klarnaPassword) {
          alert("Please enter your Klarna login details.");
          return;
        }
      } else if (this.paymentMethod === "CREDITCARD") {
        if (!this.cardNumber || !this.cardExpiry || !this.cardCVV) {
          alert("Please enter your credit card details.");
          return;
        }
      }

      this.submitOrder();
      this.showCheckoutModal = false;
    },
    async submitOrder() {
      const order = {
        customerName: this.customerName,
        customerEmail: this.customerEmail,
        paymentMethod: this.paymentMethod,
        products: this.cart.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };

      // Anhängen zusätzlicher Daten je nach Zahlungsmethode
      if (this.paymentMethod === "PAYPAL") {
        order.paypalEmail = this.paypalEmail;
        order.paypalPassword = this.paypalPassword;
      } else if (this.paymentMethod === "KLARNA") {
        order.klarnaUsername = this.klarnaUsername;
        order.klarnaPassword = this.klarnaPassword;
      } else if (this.paymentMethod === "CREDITCARD") {
        order.cardNumber = this.cardNumber;
        order.cardExpiry = this.cardExpiry;
        order.cardCVV = this.cardCVV;
      }

      try {
        await axios.post(API_BASE_URL+"/orders", order);
        alert("Order placed successfully!");
        // Alles zurücksetzen
        this.cart = [];
        this.saveCart();
        this.customerName = "";
        this.customerEmail = "";
        this.paymentMethod = "";
        this.paypalEmail = "";
        this.paypalPassword = "";
        this.klarnaUsername = "";
        this.klarnaPassword = "";
        this.cardNumber = "";
        this.cardExpiry = "";
        this.cardCVV = "";
      } catch (error) {
        console.error("Error during checkout:", error);
        alert("Failed to place the order.");
      }
    },
    addProductToCart(product) {
      const existingItem = this.cart.find(
          (item) => item.productId === product._id
      );
      if (existingItem) {
        existingItem.quantity++;
      } else {
        this.cart.push({ productId: product._id, product, quantity: 1 });
      }
      this.saveCart();
      alert("Product added to cart!");
    },
  },
  created() {
    this.loadCart();
  },
};
</script>

<style scoped>
h2, h1, h3 {
  margin-top: 0;
}
/* Warenkorb-Items */
.cart-item {
  background: var(--background-white);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  transition: box-shadow 0.3s ease;
}
.cart-item:hover {
  /* Dezentere Hover-Wirkung */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
.cart-item-content {
  display: grid;
  grid-template-columns: auto max-content;
}
.cart-item-content img {
  max-width: 100%;
  max-height: 100px;
  height: auto;
  object-fit: contain;
  border-radius: 6px;
}
.quantity-controls {
  display: flex;
  align-items: center;
  gap: 10px;
}
div:has(>.quantity-controls) {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
div:has(>.quantity-controls) > p {
  margin: 0;
}

/* Kundendaten auf der Hauptseite */
.customer-info-main {
  margin: 20px 0;
  background: var(--background-white);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.customer-info-main input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin: 5px;
  width: 80%;
  max-width: 300px;
}

/* Zahlungsmethoden-Auswahl */
.payment-selection {
  margin: 20px 0;
  background: var(--background-white);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.payment-selection label {
  margin-right: 15px;
  font-weight: bold;
}
.payment-selection input {
  width: min-content;
}

/* Checkout-Button auf Hauptseite */
.trigger-checkout {
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
  display: block;
  margin: 20px auto;
}

/* Modal Styles */
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
.modal.checkout-modal {
  background-color: #fff;
  padding: 20px;
  border-radius: 6px;
  width: 90%;
  max-width: 400px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}
.modal.checkout-modal h2 {
  font-size: 24px;
  margin-bottom: 20px;
}

/* PayPal-Look */
.paypal-login {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.paypal-logo {
  width: 100px;
  margin-bottom: 10px;
}
.paypal-header {
  font-size: 20px;
  margin: 10px 0;
}
.paypal-subtext {
  color: #666;
  margin-bottom: 20px;
}
.paypal-login input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
  width: 100%;
  max-width: 350px;
}
.forgot-link {
  align-self: flex-start;
  margin-bottom: 20px;
  color: #0070ba;
  text-decoration: none;
  font-size: 14px;
}
.forgot-link:hover {
  text-decoration: underline;
}
.paypal-login-button {
  background-color: #0070ba;
  color: #fff;
  border: none;
  padding: 12px;
  border-radius: 4px;
  width: 100%;
  max-width: 350px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 10px;
  transition: background-color 0.3s ease;
}
.paypal-login-button:hover {
  background-color: #005c9c;
}
.or-separator {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 10px 0;
}
.or-separator span {
  background: #fff;
  padding: 0 10px;
  color: #999;
}
.paypal-create-account-button {
  background-color: #fff;
  border: 1px solid #0070ba;
  color: #0070ba;
  padding: 12px;
  border-radius: 4px;
  width: 100%;
  max-width: 350px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.paypal-create-account-button:hover {
  background-color: #f5f5f5;
}

/* Klarna-Login und Credit-Card-Felder */
.klarna-container {
  text-align: left;
}
.klarna-bank-title {
  font-size: 1.1rem;
  font-weight: bold;
  margin-bottom: 0.5em;
  text-transform: uppercase;
}
.klarna-subheading {
  font-size: 1rem;
  margin-bottom: 1.2em;
  color: #333;
}
.klarna-login-fields {
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;
}
.klarna-login-fields label {
  margin-bottom: 0.3em;
  font-weight: bold;
}
.klarna-login-fields input {
  margin-bottom: 0.8em;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.klarna-note {
  font-size: 0.9rem;
  color: #555;
  margin-bottom: 0.5em;
  line-height: 1.4;
}
.klarna-privacy {
  display: inline-block;
  margin-bottom: 1em;
  font-size: 0.9rem;
  color: #0070ba;
  text-decoration: none;
}
.klarna-privacy:hover {
  text-decoration: underline;
}
.klarna-next-button {
  background-color: #da356f;
  color: #fff;
  border: none;
  padding: 12px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
  display: inline-block;
  margin-top: 1em;
  transition: background-color 0.3s ease;
  width: 100%;
  max-width: 350px;
  margin-left: 1.59rem;

}
.klarna-next-button:hover {
  background-color: #bb3e6a;
}

.creditcard-info input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
  width: 100%;
}

/* Gemeinsame Checkout-/Cancel-Buttons (für Klarna & CC) */
.checkout-button {
  background-color: #ffc439;
  border: none;
  color: #111;
  padding: 12px;
  border-radius: 4px;
  width: 100%;
  max-width: 350px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-bottom: 10px;
}
.checkout-button:hover {
  background-color: #ffb347;
}
.cancel-button {
  background-color: #ccc;
  border: none;
  color: #111;
  padding: 10px;
  border-radius: 4px;
  width: 100%;
  max-width: 350px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.cancel-button:hover {
  background-color: #bbb;
}
</style>
