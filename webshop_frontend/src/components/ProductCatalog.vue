<script>
import axios from "axios";

export default {
  name: "ProductCatalog",
  data() {
    return {
      products: [],
      categories: [],
      searchTerm: "",
      selectedCategory: "",
    };
  },
  computed: {
    filteredProducts() {
      return this.products.filter((product) => {
        const matchesCategory =
            !this.selectedCategory || product.category === this.selectedCategory;
        const matchesSearch =
            !this.searchTerm ||
            product.name.toLowerCase().includes(this.searchTerm.toLowerCase());
        return matchesCategory && matchesSearch;
      });
    },
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await axios.get("http://localhost:8080/api/products");
        this.products = response.data;

        // Extract unique categories from products
        this.categories = [
          ...new Set(response.data.map((product) => product.category)),
        ];
      } catch (error) {
        console.error("Error fetching products:", error);
      }
    },
    addToCart(product) {
      const cart = JSON.parse(localStorage.getItem("shoppingCart")) || [];
      const existingItem = cart.find((item) => item.productId === product.id);

      if (existingItem) {
        existingItem.quantity++;
      } else {
        cart.push({
          productId: product.id,
          product,
          quantity: 1,
        });
      }

      localStorage.setItem("shoppingCart", JSON.stringify(cart));
      alert("Product added to cart!");
    },
  },
  created() {
    this.fetchProducts();
  },
};
</script>

<template>
  <div>
    <h1>Product Catalog</h1>
    <!-- Filter Section -->
    <div class="filter">
      <input
          v-model="searchTerm"
          type="text"
          placeholder="Search products..."
      />
      <select v-model="selectedCategory">
        <option value="">All Categories</option>
        <option v-for="category in categories" :key="category" :value="category">
          {{ category }}
        </option>
      </select>
    </div>

    <!-- Product List -->
    <div class="product-list">
      <div
          v-for="product in filteredProducts"
          :key="product.id"
          class="product-card"
      >
        <h2>{{ product.name }}</h2>
        <img :src="`http://localhost:8080/images/${product.imageUrl}`" :alt="product.name" />
        <p>{{ product.description }}</p>
        <p>Price: {{ product.price }}</p>
        <button @click="addToCart(product)">Add to Cart</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.filter {
  margin-bottom: 20px;
}
.product-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
.product-card {
  border: 1px solid #ccc;
  padding: 15px;
  width: 200px;
}

.product-card img {
  max-width: 100%;
  max-height: 150px;
  height: auto;
  object-fit: contain;
  margin-bottom: 10px;
}

</style>