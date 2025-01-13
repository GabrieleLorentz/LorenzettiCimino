import { createRouter, createWebHistory } from 'vue-router';

// Componenti delle pagine
import Access from "@/pages/Access.vue";
import SignIn from "@/pages/SignIn.vue";
import SignUp from "@/pages/SignUp.vue";
import Student_SignUp from "@/pages/Student_SignUp.vue";
import Company_SignUp from "@/pages/Company_SignUp.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: Access},
        {path: '/signin', component: SignIn},
        {path: '/signup', component: SignUp},
        {path: '/student_signup', component: Student_SignUp},
        {path: '/company_signup', component: Company_SignUp}
    ]
})

export default router;
