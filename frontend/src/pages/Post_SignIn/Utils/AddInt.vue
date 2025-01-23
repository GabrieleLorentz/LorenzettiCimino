<template>
  <div class="profile-container1">
    <img src="/src/assets/+.svg" alt="+" class="icon3 icon_hover"/>
      <div class='popup1'>
        <p>ADD INTERNSHIP</p>

        <div class="form">
          <div class="form-row">
            <label for="name">Name:</label>
            <input id="name" v-model="Data.name" type="text" class="form-input" />
          </div>
          <div class="form-row">
            <label for="Start Date">Start Date:</label>
            <input id="Start Date" v-model="Data.start" type="date" class="form-input" />
          </div>
          <div class="form-row">
            <label for="End Date">End Date:</label>
            <input id="End Date" v-model="Data.end" type="date" class="form-input" />
          </div>
          <div class="form-row">
            <label for="Salary">Salary: $</label>
            <input id="Salary" v-model="Data.salary" type="number" class="form-input" />
          </div>
          <div class="form-row">
            <label for="Qualification">Qualification required:</label>
            <input id="Qualification" v-model="qualificationInput" @change="updateQualifications" placeholder="Separate with commas" type="text" class="form-input" />
          </div>
          <div class="form-row">
            <label for="Description">Description:</label>
            <textarea id="Description" v-model="Data.description" type="text" class="form-input" />
          </div>
        </div>
        <button @click="addInternship" class="add" :disabled="!hasChanges">ADD</button>
      </div>
  </div>
</template>

<style>
.icon3 {
  width: 45px;
  height: 45px;
  cursor: pointer;
  /*margin-top: 5px;
  margin-right: -5px;*/
}
.popup1 p {
  font-size: 25px;
  color: black;
  font-weight: bold;
  text-align: center;
  text-decoration: underline;
  margin-top: -3px;
}
.profile-container1 {
  position: relative;
  display: inline-block;
}
.profile-container1:hover .popup1 {
  display: block;
}
.popup1 {
  display: none;
  position: absolute;
  right: 0;
  background-color: #f2a73b;
  border: 2px solid black;
  border-radius: 15px;
  padding: 4px;
  min-width: 500px;
}
.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.form-row {
  display: flex;
  /*justify-content: space-between;*/
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
.add {
  background-color: #f2a73b;
  color: black;
  cursor: pointer;
  padding: 10px 50px;
  border: 3px solid black;
  border-radius: 40px;
  margin-top: 20px;
  margin-left: 170px;
  font-size: 25px;
  font-weight: bold;
  text-align: center;
  transition: background-color 0.4s ease, color 0.4s ease, border 0.4s ease;
}
.add:hover {
  background-color: #232526;
  color: #f2a73b;
  cursor: pointer;
}
.add:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>

<script setup lang="ts">
import {ref, computed} from 'vue';

interface data {
  name: string;
  start: string;
  end: string;
  salary: number;
  qualification: string[];
  description: string;
}

const Data = ref<data>({
  name: '',
  start: '',
  end: '',
  salary: 0,
  qualification: [],
  description: ''
})

const hasChanges = computed(() =>{
  return Data.value.name.trim() !== '' &&
      Data.value.start !== '' &&
      Data.value.end !== '' &&
      Data.value.qualification.length > 0 &&
      Data.value.description.trim() !== '';
})

function addInternship() {
  const token = localStorage.getItem('token');

  const formData = {
    name: Data.value.name,
    start: Data.value.start,
    end: Data.value.end,
    salary: Data.value.salary,
    qualification: Data.value.qualification,
    description: Data.value.description
  };
  console.log(formData.start);

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
          return response.json().then(data => {
            console.log("Dati ricevuti:", data);
          });
        } else {
          console.log(response.status);
        }
      })
      .catch(error => {console.error('Errore errore', error);});
}

const qualificationInput = ref('');

function updateQualifications() {
  Data.value.qualification = qualificationInput.value
      .split(',')
      .map(q => q.trim())
      .filter(q => q);
}
</script>