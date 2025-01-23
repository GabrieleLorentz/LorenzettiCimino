<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex;">
      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY INTERNSHIP
        </div>

        <div v-if="internships.length > 0" class="internships-container">
          <div v-for="internship in internships" :key="internship.id" style="padding: 5px">
            <div class="int">
              <p><strong>Name:</strong>{{ internship.name }}</p>
              <p><strong>Company:</strong>{{  }}</p>
              <p><strong>Start Date:</strong> {{ internship.start_date }}</p>
              <p><strong>End Date:</strong> {{ internship.end_date }}</p>
              <p><strong>Salary:</strong> {{ internship.salary }}</p>
            </div>
          </div>
        </div>
        <div v-else>
          <p>No internships available.</p>
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
.int {
  border: 3px solid black;
  border-radius: 40px;
  padding: 10px;
  display: flex;
  gap: 20px;
}
.internships-container {
  max-height: 540px;
  overflow-y: auto;
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
import UpperPart from '@/pages/Post_SignIn/Utils/upper_part.vue';
import {ref, onMounted} from "vue";

const internships = ref([]);

function receiveData() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/company/myInternship', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Errore nella richiesta al backend");
      })
      .then(data => {
        internships.value = data;
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

onMounted(() => {
  receiveData();
});
</script>