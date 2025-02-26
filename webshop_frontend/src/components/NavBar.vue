<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const isMenuOpen = ref(false);
const isDropdownOpen = ref(false);
const router = useRouter();
const route = useRoute();

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const navigateTo = (path) => {
  if(isDropdownOpen.value) toggleDropdown();
  router.push(path);
  if (window.innerWidth <= 768) {
    isMenuOpen.value = false;
  }
};

const cartItemCount = computed(() => {
  const cart = JSON.parse(localStorage.getItem('shoppingCart')) || [];
  return cart.reduce((sum, item) => sum + item.quantity, 0);
});

onMounted(() => {
  window.addEventListener("resize", () => {
    if (window.innerWidth > 768) {
      isMenuOpen.value = false;
      isDropdownOpen.value = false;
    }
  });
});
</script>

<template>
  <nav class="navbar">
    <div class="navbar-container">
      <div class="logo" @click="navigateTo('/')">LowTech</div>

      <ul :class="['nav-links', { active: isMenuOpen }]">

        <li class="dropdown">
          <span @click="toggleDropdown">
            <i class="fas fa-box"></i> Products <i class="fas fa-caret-down"></i>
          </span>
          <ul v-if="isDropdownOpen" class="dropdown-menu">
            <li @click="navigateTo('/products')">All Products</li>
            <li @click="navigateTo('/inventory')">Inventory</li>
          </ul>
        </li>

        <li :class="{ active: route.path === '/orders' }" @click="navigateTo('/orders')">
          <i class="fas fa-receipt"></i> Orders
        </li>
        <li :class="{ active: route.path === '/cart' }" @click="navigateTo('/cart')">
          <i class="fas fa-shopping-cart"></i> Cart
          <span v-if="cartItemCount > 0" class="cart-badge">{{ cartItemCount }}</span>
        </li>
        <li :class="{ active: route.path === '/login' }" @click="navigateTo('/login')">
          <i class="fas fa-sign-in-alt"></i> Login
        </li>
      </ul>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  background-color: var(--primary-dark);
  color: var(--background-light);
  padding: 1px 0;
  position: sticky;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 1000;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1rem;
}

.logo {
  font-family: "Corinthia", serif;
  font-weight: 400;
  font-style: normal;
  font-size: 3rem;
  padding-top: 6px;
  cursor: pointer;
}

.nav-links {
  list-style: none;
  display: flex;
  gap: 1.5rem;
  margin: 0;
}

.nav-links li {
  cursor: pointer;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  transition: background 0.3s;
  display: flex;
  align-items: center;
}

.nav-links li:hover {
  background: var(--primary-color);
}

.nav-links li.active {
  background-color: var(--primary-color);
  font-weight: bold;
}

.cart-badge {
  background-color: #b074cc;
  padding: 2px 8px;
  border-radius: 50%;
  font-size: 0.8rem;
  margin-left: 5px;
}

.dropdown {
  position: relative;
}

.dropdown-menu {
  position: absolute;
  top: 40px;
  left: 0;
  background-color: #333;
  border-radius: 5px;
  list-style: none;
  padding: 10px 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.dropdown-menu li {
  padding: 10px 20px;
  white-space: nowrap;
  cursor: pointer;
}

.dropdown-menu li:hover {
  background: var(--primary-color);
}

@media (max-width: 768px) {
  .nav-links {
    position: absolute;
    top: 60px;
    left: 0;
    width: 100%;
    background-color: #333;
    flex-direction: column;
    align-items: center;
    display: none;
  }

  .nav-links.active {
    display: flex;
  }

  .nav-links li {
    padding: 1rem;
    width: 100%;
    text-align: center;
  }
}
</style>
