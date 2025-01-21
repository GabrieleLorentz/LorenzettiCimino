import { createRouter, createWebHistory } from 'vue-router';

// Componenti delle pagine
import Access from "@/pages/Pre_SignIn/Access.vue";
import SignIn from "@/pages/Pre_SignIn/SignIn.vue";
import SignUp from "@/pages/Pre_SignIn/SignUp.vue";
import Student_SignUp from "@/pages/Pre_SignIn/Student_SignUp.vue";
import Company_SignUp from "@/pages/Pre_SignIn/Company_SignUp.vue";
import Student_Home from "@/pages/Post_SignIn/Student/Student_Home.vue";
import Company_Home from "@/pages/Post_SignIn/Company/Company_Home.vue";
import Company_Public from "@/pages/Post_SignIn/Company/Company_Public.vue";
import Company_Private from "@/pages/Post_SignIn/Company/Company_Private.vue";
import Student_Public from "@/pages/Post_SignIn/Student/Student_Public.vue";
import Student_Private from "@/pages/Post_SignIn/Student/Student_Private.vue";
import Rank_List from "@/pages/Post_SignIn/Rank_List.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: Access},
        {path: '/signin', component: SignIn},
        {path: '/signup', component: SignUp},
        {path: '/student_signup', component: Student_SignUp},
        {path: '/company_signup', component: Company_SignUp},
        {path: '/student_home', component: Student_Home},
        {path: '/company_home', component: Company_Home},
        {path: '/company_public', component: Company_Public},
        {path: '/company_private', component: Company_Private},
        {path: '/student_public', component: Student_Public},
        {path: '/student_private', component: Student_Private},
        {path: '/rank_list', component: Rank_List}
    ]
})

export default router;
