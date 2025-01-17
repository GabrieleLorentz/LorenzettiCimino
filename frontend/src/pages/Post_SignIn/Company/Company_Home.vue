<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart subtitle="- Company"
               :showAddIcon="true"
               rank="/company_rank_list"
               publicProfile="/company_public"
               privateProfile="/company_private"

    />

    <hr class="horizontal_line" />

    <div style="width: 100%; display: flex;">
      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY INTERNSHIP
        </div>
      </div>
      <div class="vertical_line"></div>
      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY COLLABORATIONS IN PROGESS
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.horizontal_line{
  width: 100%;
  height: 2px;
  background-color: black;
  border: none;
}
.vertical_line {
  position: absolute;
  left: 50%;
  top: 55px;
  height: calc(100vh - 55px);
  width: 2px;
  background-color: black;
}
</style>

<script setup lang="ts">
import UpperPart from '@/pages/Post_SignIn/upper_part.vue';

import { useRouter } from 'vue-router';
const router = useRouter();
const goToPage = (path: string) => {
  router.push(path);
};
</script>