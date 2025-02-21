<script setup>
import NavBar from "./components/NavBar.vue";
</script>

<template>
  <div class="app-container">
    <header>
      <NavBar />
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<style>
/* 🔹 Global Styles for Full Responsiveness */
:root {
  --primary-color: #2c3e50;
  --primary-dark: #1a252f;
  --secondary-color: #007bff;
  --secondary-dark: #0056b3;
  --background-light: #f8f9fa;
  --background-white: #ffffff;
  --text-color: #333;
  --border-radius: 5px;
}

/* 🔹 General Page Layout */
body {
  font-family: "Arial", sans-serif;
  background-color: var(--background-light);
  color: var(--text-color);
  margin: 0;
  padding: 0;
}

/* 🔹 Main App Container */
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* 🔹 Header */
header {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--background-white);
  padding: 1rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* 🔹 Main Content */
main {
  flex: 1;
  padding: 20px;
  background-color: var(--background-white);
}

/* 🔹 Mobile Layout */
@media (max-width: 768px) {
  main {
    padding: 10px;
  }

  header {
    flex-direction: column;
  }
}

/* 🔹 Buttons */
button {
  padding: 10px;
  border: none;
  border-radius: var(--border-radius);
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

/* Primary Button */
.button-primary {
  background: var(--primary-color);
  color: white;
}

.button-primary:hover {
  background: var(--primary-dark);
}

/* Secondary Button */
.button-secondary {
  background: var(--secondary-color);
  color: white;
}

.button-secondary:hover {
  background: var(--secondary-dark);
}

/* 🔹 Input Fields */
input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: var(--border-radius);
  width: 100%;
  max-width: 200px;
}

/* 🔹 Container Box */
.container-box {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background: var(--background-white);
  border-radius: 10px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
