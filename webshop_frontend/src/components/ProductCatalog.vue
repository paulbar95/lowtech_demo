<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import { formatter } from "./PriceFormatter.js"

const products = ref([]);
const productImageUrl = ref("");
const searchQuery = ref("");
const selectedCategory = ref("");

const fetchProducts = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/products");
    const imageUrl = await axios.get("http://localhost:8080/api/products/imageUrl");
    products.value = response.data;
    productImageUrl.value = imageUrl.data;
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
    cart.push({ productId: product.id, product, quantity: 1, image: `${productImageUrl.value}${product.imageUrl}` });
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
        <div>
          <img :src="`${productImageUrl}${product.imageUrl}`" :alt="product.name" />
          <div>
            <p>{{ product.description }}</p>
            <div>
              <div>
                <p>Category: {{ product.category }}</p>
                <p>Price: {{ formatter.format(product.price / 100) }}</p>
              </div>
              <button class="button-primary" @click="addToCart(product)">Add to Cart</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

h2 {
  margin-top: 0;
}

/* Filter Section */
.filter-section {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

/* Product Card */
.product-card {
  background: var(--background-white);
  padding: 20px;
  border-radius: var(--border-radius);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
}

.product-card:hover {
  transform: scale(1.02);
}

.product-card h3 {
  margin-bottom: 10px;
}

.product-card img {
  max-width: 100%;
  max-height: 150px;
  height: auto;
  object-fit: contain;
  border-radius: 6px;
}

.product-card > div p:first-child {
  margin: 0;
}

.product-card > div {
  display: grid;
  grid-template-columns: max-content auto;
  gap: 20px;
}

.product-card > div > div > div {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.product-list > div:not(:last-child) {
  margin-bottom: 20px;
}

</style>