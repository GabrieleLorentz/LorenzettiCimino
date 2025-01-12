import { createRouter, createWebHistory } from 'vue-router';

// Componenti delle pagine
import SignIn from "@/pages/SignIn.vue";
import SignUp from "@/pages/SignUp.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/signin', name: 'SignIn', component: SignIn},
        {path: '/signup', name: 'SignUp', component: SignUp},
    ]
})

export default router;
