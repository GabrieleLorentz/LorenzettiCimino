<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex; justify-content: center;">
      <span class="orange">Private</span>
      <span style="margin-left: 7px;" class="black">page</span>
    </div>

    <div style="width: 100%; display: flex;">
      <div style="flex: 1; padding: 20px; display: flex; flex-direction: column; align-items: center;">
        <div class="data">
          {{ name }}
        </div>
        <div class="data">
          E-Mail
        </div>
        <div class="data">
          Password
        </div>
        <div class="data">
          Description
        </div>
        <div class="data">
          Vat number
        </div>
      </div>
      <div class="vertical_line2"></div>
      <div style="flex: 1; padding: 20px;">

      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import UpperPart from "@/pages/Post_SignIn/upper_part.vue";
</script>