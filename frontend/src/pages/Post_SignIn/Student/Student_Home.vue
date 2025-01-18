<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart subtitle="- Student"
               :showAddIcon="false"
               rank="/student_rank_list"
               publicProfile="/student_public"
               privateProfile="/student_private"
    />

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
import UpperPart from "@/pages/Post_SignIn/upper_part.vue";
</script>