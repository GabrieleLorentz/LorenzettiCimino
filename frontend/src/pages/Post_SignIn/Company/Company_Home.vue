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
            <div style="border: 3px solid black; border-radius: 40px; padding: 10px; display: flex; flex-direction: column; gap: 5px">
              <div style="display: flex; gap: 10px">
                <p><strong>Name:</strong> {{ internship.name }}</p>
                <p><strong>Company:</strong> {{ internship.companyName }}</p>
                <p><strong>Start Date:</strong> {{ internship.startDate }}</p>
                <p><strong>End Date:</strong> {{ internship.endDate }}</p>
              </div>
              <div style="display: flex; gap: 10px">
                <p><strong>End Selection AcceptanceDate:</strong> {{ internship.endSelectionAcceptanceDate }}</p>
                <p><strong>End Form CompilingDate:</strong> {{ internship.endFormCompilingDate }}</p>
                <p><strong>Salary: $</strong> {{ internship.salary }}</p>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Qualification required:</strong></p>
                <ul style="width: 90%; padding-left: 1em;">
                  <li v-for="(qualification, index) in internship.qualification_required" :key="index">
                    {{ qualification }}
                  </li>
                </ul>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Description:</strong></p>
                <textarea readonly style="width: 90%;"> {{ internship.description }}</textarea>
              </div>
              <div style="display: flex; gap: 5px">
                <button @click="openRequest(internship)" class="popup-button" style="font-size: 15px;">Request Students </button>
                <button @click="" class="popup-button" style="font-size: 15px;">Response Students </button>
              </div>
            </div>
          </div>

          <div v-if="showRequest" class="det">
            <div class="det-content">
              <h2>Students</h2>
              <div v-for="student in selecteInternship.applicants" style="padding: 5px; display: flex; gap: 10px">
                <p> {{student.name}} </p>
                <p> {{student.surname}}</p>
                <button @click="accepted(student.email, selecteInternship.id)" >Yes</button>
              </div>
              <button @click="closeRequest" class="popup-button" style="font-size: 20px;">Close</button>
            </div>
          </div>
        </div>
        <div v-else style="font-size: 30px">
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
.internships-container {
  max-height: 540px;
  overflow-y: auto;
}
.vertical_line {
  position: absolute;
  left: 50%;
  top: 58px;
  height: calc(100vh - 58px);
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
        console.log(data)
        internships.value = data;
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

onMounted(() => {
  receiveData();
});

const showRequest = ref(false);
const selecteInternship = ref(null);
function openRequest(internship) {
  selecteInternship.value = internship;
  showRequest.value = true;
}
function closeRequest() {
  showRequest.value = false;
  selecteInternship.value = null;
}
function accepted(email, internshipId) {
  const token = localStorage.getItem('token');
  console.log(selecteInternship)

  fetch(`http://localhost:8080/api/company/studentAccepted/${email}_${internshipId}`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`

    },
  })
      .then(response => {
        if (response.ok) {
          return;
        }
        throw new Error("Errore nella richiesta al backend");
      })
      .then(data => {
        console.log("ciao")
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}
</script>