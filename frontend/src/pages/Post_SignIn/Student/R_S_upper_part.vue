<template>
  <div style="width: calc(100% - 20px); display: flex; justify-content: space-between; align-items: center;  margin-left: 10px; margin-right: 10px;">
    <div style="display: flex; align-items: center;">
      <span class="orange">Student</span>
      <span class="black">&</span>
      <span class="orange">Company</span>
      <span style="margin-left: 7px" class="black">- Student</span>
    </div>
    <div style="display: flex; gap: 10px; align-items: center;">
      <img @click="goToPage('/company_rank_list.vue')" src="/src/assets/Rank_List.svg" alt="Rank_List" class="icon2 icon_hover"/>
      <img src="/src/assets/campanella.svg" alt="campanella" class="icon2 icon_hover"/>
      <div class="profile-container">
        <img src="/src/assets/profilo.svg" alt="profilo" class="icon1 icon_hover"/>
        <div class="popup">
          <p>PROFILE</p>
          <span @click="goToPage('/company_public.vue')" class="popup-link" style="margin-top: -25px;">Public</span>
          <span @click="goToPage('/company_private.vue')" class="popup-link">Private</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import R_C_upper_part from "@/pages/Post_SignIn/Company/R_C_upper_part.vue";
const router = useRouter();
const goToPage = (path: string) => {
  router.push(path);
};
</script>

<!--gli stili stanno in <R_C_upper_part-->