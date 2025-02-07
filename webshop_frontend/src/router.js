import { createRouter, createWebHistory } from 'vue-router';

// Import your components here
import ProductCatalog from './components/ProductCatalog.vue';
import ShoppingCart from './components/ShoppingCart.vue';
import ProductForm from './components/ProductForm.vue';
import InventoryManagement from './components/InventoryManagement.vue';
import OrderManagement from "@/components/OrderManagement.vue";

const routes = [
    { path: '/', name: 'ProductCatalog', component: ProductCatalog },
    { path: '/cart', name: 'ShoppingCart', component: ShoppingCart },
    { path: '/products', name: 'ProductForm', component: ProductForm },
    { path: '/inventory', name: 'InventoryManagement', component: InventoryManagement },
    { path: '/orders', name: 'OrderManagement', component: OrderManagement },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
