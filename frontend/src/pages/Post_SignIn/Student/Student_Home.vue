<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <div style="width: calc(100% - 20px); display: flex; justify-content: space-between; align-items: center;  margin-left: 10px; margin-right: 10px;">
      <div style="display: flex; align-items: center;">
        <span class="orange">Student</span>
        <span class="black">&</span>
        <span class="orange">Company</span>
        <span class="black"> - Student</span>
      </div>
      <div style="display: flex; gap: 10px; align-items: center;">
        <img @click="goToPage('/student_rank_list.vue')" src="/src/assets/Rank_List.svg" alt="Rank_List" class="icon2 icon_hover"/>
        <img src="/src/assets/campanella.svg" alt="campanella" class="icon2 icon_hover" />
        <div class="profile-container">
          <img src="/src/assets/profilo.svg" alt="profilo" class="icon1 icon_hover" />
          <div class="popup">
            <p>PROFILE</p>
            <span @click="goToPage('/student_public.vue')" class="popup-link" style="margin-top: -25px;">Public</span>
            <span @click="goToPage('/student_private.vue')" class="popup-link">Private</span>
          </div>
        </div>
      </div>
    </div>

    <hr class="horizontal_line" />

    <div style="width: 100%; display: flex;">
      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY COLLABORATIONS IN PROGESS
        </div>
      </div>
      <div class="vertical_line"></div>
      <div style="flex: 1; padding: 20px;">

      </div>
    </div>
  </div>
</template>


<style>
.my_collaboration {
  background-color: #f2a73b;
  color: black;
  padding: 10px;
  border-radius: 40px;
  font-size: 30px;
  font-weight: bold;
  text-align: center;
}
</style>

<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();
const goToPage = (path: string) => {
  router.push(path);
};
</script>