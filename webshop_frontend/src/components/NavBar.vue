<script>
export default {
  name: "NavBar",
};
</script>

<template>
  <nav class="navbar">
    <ul>
      <li><router-link to="/">Product Catalog</router-link></li>
      <li><router-link to="/cart">Shopping Cart</router-link></li>
      <li><router-link to="/products">Add Product</router-link></li>
      <li><router-link to="/inventory">Inventory Management</router-link></li>
      <li><router-link to="/orders">Order Management</router-link></li>
    </ul>
  </nav>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: #333;
  color: white;
  padding: 1rem;
}

.navbar ul {
  list-style: none;
  display: flex;
  gap: 2rem;
  padding: 0;
  margin: 0;
}

.navbar li {
  font-size: 1rem;
}

.navbar a {
  text-decoration: none;
  color: white;
  transition: color 0.3s;
}

.navbar a:hover {
  color: #00bcd4;
}
.navbar a.router-link-active {
  font-weight: bold;
  color: #00bcd4;
}
</style>