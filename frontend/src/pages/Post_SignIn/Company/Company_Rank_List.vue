<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">
    <UpperPart subtitle="- Company"
               :showAddIcon="true"
               rank="/company_rank_list"
               publicProfile="/company_public"
               privateProfile="/company_private"

    />

    <hr class="horizontal_line" />

    <div style="width: 100%; display: flex; justify-content: center;">
      <span class="black">RANKED LIST OF</span>
      <span style="margin-left: 7px;" class="orange">COMPANY</span>
    </div>

  </div>
</template>

<script setup>
import UpperPart from "@/pages/Post_SignIn/upper_part.vue";
</script>