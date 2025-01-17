<template>
    <div style="width: calc(100% - 20px); display: flex; justify-content: space-between; align-items: center;  margin-left: 10px; margin-right: 10px;">
      <div style="display: flex; align-items: center;">
        <span class="orange">Student</span>
        <span class="black">&</span>
        <span class="orange">Company</span>
        <span style="margin-left: 7px" class="black">- Company</span>
      </div>
      <div style="display: flex; gap: 10px; align-items: center;">
        <img src="/src/assets/+.svg" alt="+" class="icon3 icon_hover"/>
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
const router = useRouter();
const goToPage = (path: string) => {
  router.push(path);
};
</script>

<style>
.orange {
  color: #f2a73b;
  font-size: 40px;
  font-weight: bold;
}
.black {
  color: black;
  font-size: 40px;
  font-weight: bold;
}
.icon1 {
  width: 35px;
  height: 35px;
}
.icon2 {
  width: 40px;
  height: 40px;
  cursor: pointer;
}
.icon3 {
  width: 45px;
  height: 45px;
  cursor: pointer;
}
.icon_hover:hover{
  cursor: pointer;
  transition: filter 0.2s ease;
  filter: invert(71%) sepia(45%) saturate(669%) hue-rotate(346deg) brightness(96%) contrast(92%);
}
.profile-container {
  position: relative;
  display: inline-block;
}
.popup {
  display: none;
  position: absolute;
  right: 0;
  /*top: 100%;*/
  background-color: #f2a73b;
  border: 2px solid black;
  border-radius: 15px;
  padding: 4px;
  /*min-width: 120px;*/
  /*z-index: 1000;*/
}
.profile-container:hover .popup {
  display: block;
}
.popup-link {
  display: block;
  /*padding: 8px;*/
  font-size: 25px;
  color: black;
  text-decoration: none;
  text-align: center;
}
.popup-link:hover {
  /*background-color: #f2a73b;*/
  color: white;
  cursor: pointer;
}
.popup p {
  font-size: 25px;
  color: black;
  font-weight: bold;
  text-align: center;
  text-decoration: underline;
  margin-top: -3px;
}
</style>