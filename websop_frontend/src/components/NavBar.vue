<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const isMenuOpen = ref(false);
const isDropdownOpen = ref(false);
const router = useRouter();
const route = useRoute();
const searchQuery = ref("");

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const navigateTo = (path) => {
  router.push(path);
  if (window.innerWidth <= 768) {
    isMenuOpen.value = false;
  }
};

const navigateToSearch = () => {
  if (searchQuery.value.trim()) {
    router.push(`/search?q=${searchQuery.value.trim()}`);
    isMenuOpen.value = false;
  }
};

const cartItemCount = computed(() => {
  const cart = JSON.parse(localStorage.getItem('shoppingCart')) || [];
  return cart.reduce((sum, item) => sum + item.quantity, 0);
});

const toggleDarkMode = () => {
  document.body.classList.toggle('dark-mode');
};

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
      <div class="logo" @click="navigateTo('/')">Product Catalog</div>

      <div class="nav-extras">
        <input v-model="searchQuery" type="text" placeholder="Search Products..." @keyup.enter="navigateToSearch" />
        <button class="menu-toggle" @click="toggleMenu">
          <span class="hamburger"></span>
        </button>
      </div>

      <ul :class="['nav-links', { active: isMenuOpen }]">
        <li :class="{ active: route.path === '/' }" @click="navigateTo('/')">
          <i class="fas fa-home"></i> Home
        </li>

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
        <li @click="toggleDarkMode">
          <i class="fas fa-moon"></i> Dark Mode
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
  background-color: #333;
  color: white;
  padding: 1rem 0;
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
  font-size: 1.5rem;
  font-weight: bold;
  cursor: pointer;
}

.nav-extras {
  display: flex;
  align-items: center;
  gap: 15px;
}

.nav-extras input {
  padding: 5px 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.menu-toggle {
  display: none;
  background: none;
  border: none;
  cursor: pointer;
  color: white;
}

.hamburger {
  width: 30px;
  height: 3px;
  background-color: white;
  position: relative;
}

.hamburger::before,
.hamburger::after {
  content: '';
  position: absolute;
  width: 30px;
  height: 3px;
  background-color: white;
  transition: all 0.3s ease-in-out;
}

.hamburger::before {
  top: -8px;
}

.hamburger::after {
  top: 8px;
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
  gap: 10px;
}

.nav-links li:hover {
  background: #00bcd4;
}

.nav-links li.active {
  background-color: #007bff;
  font-weight: bold;
}

.cart-badge {
  background-color: #ff3b3f;
  color: white;
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
  cursor: pointer;
}

.dropdown-menu li:hover {
  background: #00bcd4;
}

@media (max-width: 768px) {
  .menu-toggle {
    display: block;
  }

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

body.dark-mode {
  background-color: #121212;
  color: #ffffff;
}
</style>
