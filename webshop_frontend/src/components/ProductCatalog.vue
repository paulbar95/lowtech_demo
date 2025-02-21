<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";

const products = ref([]);
const searchQuery = ref("");
const selectedCategory = ref("");

const fetchProducts = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/products");
    products.value = response.data;
  } catch (error) {
    console.error("Error fetching products:", error);
  }
};

onMounted(fetchProducts);

const filteredProducts = computed(() => {
  return products.value.filter((product) => {
    const matchesCategory = selectedCategory.value
        ? product.category === selectedCategory.value
        : true;
    const matchesSearch = product.name
        .toLowerCase()
        .includes(searchQuery.value.toLowerCase());
    return matchesCategory && matchesSearch;
  });
});

const addToCart = (product) => {
  const cart = JSON.parse(localStorage.getItem("shoppingCart")) || [];
  const existingItem = cart.find((item) => item.productId === product.id);

  if (existingItem) {
    existingItem.quantity++;
  } else {
    cart.push({ productId: product.id, product, quantity: 1 });
  }

  localStorage.setItem("shoppingCart", JSON.stringify(cart));
  alert("Product added to cart!");
};
</script>

<template>
  <div class="container-box">
    <h2>Product Catalog</h2>

    <!-- Filter Section -->
    <div class="filter-section">
      <input v-model="searchQuery" type="text" placeholder="Search Products..." />
      <select v-model="selectedCategory">
        <option value="">All Categories</option>
        <option v-for="category in [...new Set(products.map(p => p.category))]" :key="category">
          {{ category }}
        </option>
      </select>
    </div>

    <!-- Product List -->
    <div v-if="filteredProducts.length === 0">
      <p>No products found. Try adjusting your filters.</p>
    </div>

    <div v-else class="product-list">
      <div
          v-for="product in filteredProducts"
          :key="product.id"
          class="product-card"
      >
        <h2>{{ product.name }}</h2>
        <img :src="`http://localhost:8080/images/${product.imageUrl}`" :alt="product.name" />
        <p>{{ product.description }}</p>
        <p>Category: {{ product.category }}</p>
        <p>Price: {{ product.price }}</p>
        <button @click="addToCart(product)">Add to Cart</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Filter Section */
.filter-section {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

/* Product Grid */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

/* Product Card */
.product-card {
  background: var(--background-light);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
}

.product-card:hover {
  transform: scale(1.05);
}

.product-card h3 {
  margin-bottom: 10px;
}

.product-card img {
  max-width: 100%;
  max-height: 150px;
  height: auto;
  object-fit: contain;
  margin-bottom: 10px;
}

</style>