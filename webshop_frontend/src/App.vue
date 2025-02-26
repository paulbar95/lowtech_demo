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
@import url('https://fonts.googleapis.com/css2?family=Corinthia:wght@400;700&display=swap');

/* 🔹 Global Styles for Full Responsiveness */
:root {
  --primary-color: #92759c;
  --primary-dark: #6e5875;
  --secondary-color: #007bff;
  --secondary-dark: #0056b3;
  --background-light: #f3ddff;
  --background-white: #ffffff;
  --background-body: #f8f3ff;
  --text-color: #49444a;
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
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* 🔹 Main Content */
main {
  flex: 1;
  padding: 20px;
  background-color: var(--background-body);
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

/* 🔹 Input Fields */
input, select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: var(--border-radius);
  width: 100%;
  max-width: 200px;
}

select option:first-child {
  color: red;
}

/* 🔹 Container Box */
.container-box {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background: var(--background-white);
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
