<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex;">

      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY COLLABORATIONS IN PROGESS
        </div>
      </div>

      <div class="vertical_line"></div>

      <div style="flex: 1; padding: 20px; display: flex; flex-direction: column; align-items: center;">
        <div style="display: flex; gap: 10px;">
          <img src="/src/assets/lente.svg" alt="lente" class="icon2 icon_hover"/>
          <input v-model="keyWords" type="text" class="search-bar" placeholder="Search..." />
          <img @click="search" src="/src/assets/filtro.svg" alt="filtro" class="icon4 icon_hover"/>
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
.search-bar {
  font-size: 20px;
  border-radius: 10px;
  border: 2px solid black;
  padding: 5px;
}
.icon4 {
  width: 25px;
  height: 25px;
  cursor: pointer;
  margin-top: 5px;
}
</style>

<script setup lang="ts">
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";
import {onMounted, ref} from "vue";

const internships = ref([]);
const keyWords = ref('');

function receiveData() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/student/AllInternship', {
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

function search() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/student/search', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ query: keyWords.value })
  })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.log(response.status);
        }
      })
      .then(data => {
        internships.value = data;
      })
      .catch(error => {console.error('Errore errore', error);
      });
}
onMounted(() => {
  receiveData();
});
</script>