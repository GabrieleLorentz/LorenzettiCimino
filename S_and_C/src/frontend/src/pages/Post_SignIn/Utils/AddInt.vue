<template>
  <div class="profile-container">
    <img @click="openPopup" src="/src/assets/+.svg" alt="+" class="icon3 icon_hover"/>
      <div v-if="showPopup" class="det1">
        <div class="det-content1">
          <p>ADD INTERNSHIP</p>

          <div class="form">
            <div class="form-row">
              <label for="name">Name:</label>
              <input id="name" v-model="Data.name" type="text" class="form-input" />
            </div>
            <div class="form-row">
              <label for="Start Date">Start Date:</label>
              <input id="Start Date" v-model="Data.startDate" type="date" class="form-input" />
            </div>
            <div class="form-row">
              <label for="End Date">End Date:</label>
              <input id="End Date" v-model="Data.endDate" type="date" class="form-input" />
            </div>
            <div class="form-row">
              <label for="end Form Compiling Date">End Form Compiling Date:</label>
              <input id="end Form Compiling Date" v-model="Data.endFormCompilingDate" type="date" class="form-input" />
            </div>
            <div class="form-row">
              <label for="end Selection Acceptance Date">End Selection Acceptance Date:</label>
              <input id="end Selection Acceptance Date" v-model="Data.endSelectionAcceptanceDate" type="date" class="form-input" />
            </div>
            <div class="form-row">
              <label for="Salary">Salary: $</label>
              <input id="Salary" v-model="Data.salary" type="number" class="form-input" />
            </div>
            <div class="form-row">
              <label for="Qualification">Qualification required:</label>
              <textarea id="Qualification" v-model="qualificationInput" @change="updateQualifications" placeholder="Separate with /" type="text" class="form-input" />
            </div>
            <div class="form-row">
              <label for="Description">Description:</label>
              <textarea id="Description" v-model="Data.description" type="text" class="form-input" />
            </div>
            <div class="form-row">
              <label for="Question">Questions:</label>
              <textarea id="Question" v-model="updateQuestion" @change="addQuestion" placeholder="Separate with /" type="text" class="form-input" />
            </div>
          </div>
          <div style="display: flex; gap: 5px; margin-top: 5px">
            <button @click="closePopup" class="popup-button" style="font-size: 20px;">Close</button>
            <button @click="addInternship" class="popup-button" :disabled="!hasChanges">ADD</button>
          </div>
        </div>
      </div>
  </div>
</template>

<style>
.icon3 {
  width: 45px;
  height: 45px;
  cursor: pointer;
}
.det-content1 {
  background: white;
  padding: 15px;
  border-radius: 10px;
  width: 50%;
}
.det-content1 p {
  font-size: 25px;
  color: black;
  font-weight: bold;
  text-align: center;
  text-decoration: underline;
  margin-top: -3px;
}
.det1 {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.form-row {
  display: flex;
  align-items: center;
}
.form-row label {
  font-size: 18px;
  font-weight: bold;
  color: black;
}
.form-input {
  width: 60%;
  padding: 5px;
  border: 1px solid black;
  border-radius: 4px;
  font-size: 16px;
  margin-left: 5px;
}
</style>

<script setup lang="ts">
import {ref, computed} from 'vue';

interface data {
  name: string;
  startDate: string;
  endDate: string;
  endFormCompilingDate: string;
  endSelectionAcceptanceDate: string;
  salary: number;
  qualification: string[];
  description: string;
  questions: string[];
}

const Data = ref<data>({
  name: '',
  startDate: '',
  endDate: '',
  endFormCompilingDate: '',
  endSelectionAcceptanceDate: '',
  salary: 0,
  qualification: [],
  description: '',
  questions: []
})

const hasChanges = computed(() =>{
  return Data.value.name.trim() !== '' &&
      Data.value.startDate !== '' &&
      Data.value.endDate !== '' &&
      Data.value.endFormCompilingDate !== '' &&
      Data.value.endSelectionAcceptanceDate !== '' &&
      Data.value.qualification.length > 0 &&
      Data.value.description.trim() !== '' &&
      Data.value.questions.length > 0;
})

/**
 * it helps the company to add an internship
 */
function addInternship() {
  const token = localStorage.getItem('token');

  const formData = {
    name: Data.value.name,
    startDate: Data.value.startDate,
    endDate: Data.value.endDate,
    endFormCompilingDate: Data.value.endFormCompilingDate,
    endSelectionAcceptanceDate: Data.value.endSelectionAcceptanceDate,
    salary: Data.value.salary,
    qualificationRequired: Data.value.qualification,
    description: Data.value.description,
    questions: Data.value.questions
  };

  fetch('http://localhost:8080/api/company/insertInternship', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  })
      .then(response => {
        if (response.ok) {
          closePopup()
          return response.json()
        } else if (response.status === 400){
          alert('Control the dates')
        } else {
          console.log(response.status);
        }
      })
      .catch(error => {console.error('Error', error);});
}

const qualificationInput = ref('');
function updateQualifications() {
  Data.value.qualification = qualificationInput.value
      .split('/')
      .map(q => q.trim())
      .filter(q => q);
}

const updateQuestion = ref('');
function addQuestion() {
  Data.value.questions = updateQuestion.value
      .split('/')
      .map(q => q.trim())
      .filter(q => q);
}

const showPopup = ref(false);
function openPopup() {
  showPopup.value = true;
}
function closePopup() {
  showPopup.value = false;
}
</script>