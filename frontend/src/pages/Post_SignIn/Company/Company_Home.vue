<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

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
</script>