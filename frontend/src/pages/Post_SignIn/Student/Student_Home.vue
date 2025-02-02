<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex;">

      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY COLLABORATIONS IN PROGRESS
        </div>
        <div v-if="myInternships.length > 0" class="internships-container">
          <div v-for="internship in myInternships" style="padding: 5px">
            <div style="border: 3px solid black; border-radius: 40px; padding: 10px; display: flex; flex-direction: column; gap: 5px">
              <div style="display: flex; align-items: center; gap: 10px">
                <p><strong>Name:</strong> {{ internship.name }}</p>
                <router-link :to="`/company_public/${internship.companyEmail}`"> <strong>Company: </strong>{{internship.company_name}} </router-link>
                <p><strong>Start Date:</strong> {{ internship.startDate }}</p>
                <p><strong>End Date:</strong> {{ internship.endDate }}</p>
              </div>
              <div style="display: flex; gap: 10px">
                <p><strong>End Form CompilingDate:</strong> {{ internship.endFormCompilingDate }}</p>
                <p><strong>End Selection AcceptanceDate:</strong> {{ internship.endSelectionAcceptanceDate }}</p>
                <p><strong>Salary: $</strong> {{ internship.salary }}</p>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Qualification required:</strong></p>
                <ul style="width: 90%; padding-left: 1em;">
                  <li v-for="(qualification, index) in internship.qualificationRequired" :key="index">
                    {{ qualification }}
                  </li>
                </ul>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Description:</strong></p>
                <textarea readonly style="width: 90%;"> {{ internship.description }}</textarea>
              </div>
              <div v-if="internship.isAccepted===true && internship.formToCompile && isBeforeDeadline(internship.endFormCompilingDate)" style="display: flex; gap: 5px">
                <button @click="openForm(internship)" class="popup-button">Form</button>
              </div>
              <div v-if="internship.isSelected===true && isBeforeDeadline(internship.startDate)" style="display: flex; gap: 5px">
                <button @click="renounce(internship)" class="popup-button">Renounce!</button>
              </div>
              <div v-if="!isBeforeDeadline(internship.startDate)">
                <buttons :internship="internship" :student="null" />
              </div>
            </div>
          </div>

          <div v-if="showForm" class="det">
            <div class="det-content">
              <h2>Questions</h2>
              <div v-for="form in selectedForm.formToCompile" :key="form.formId">
                <p>{{form.request}}</p>
                <textarea v-model="form.response" type="text" id="response" style="width: 90%; height: 50px" placeholder="Enter your response..."/>
              </div>
              <div style=" display:flex; gap: 5px">
                <button @click="closeForm" class="popup-button" style="font-size: 20px;">Close</button>
                <button @click="send" class="popup-button" style="font-size: 20px;">Send</button>
              </div>
            </div>
          </div>

        </div>
        <div v-else style="font-size: 30px">
          <p>No internships available.</p>
        </div>
      </div>

      <div style="flex: 1; padding: 20px; display: flex; flex-direction: column; align-items: center;">
        <div style="display: flex; gap: 10px;">
          <img @click="search" src="/src/assets/lente.svg" alt="lente" class="icon2 icon_hover"/>
          <input v-model="key.name" type="text" class="search-bar" placeholder="Search..." />
          <div class="profile-container">
            <img src="/src/assets/filtro.svg" alt="filtro" class="icon4 icon_hover"/>
            <div class="popup" style="min-width: 300px;">
              <div class="form">
                <div class="form-row">
                  <label for="start">Min Start Date:</label>
                  <input id="start" v-model="key.start" type="date" class="form-input" />
                </div>
                <div class="form-row">
                  <label for="end">Max End Date:</label>
                  <input id="end" v-model="key.end" type="date" class="form-input" />
                </div>
                <div class="form-row">
                  <label for="salary">Min Salary:</label>
                  <input id="salary" v-model="key.salary" type="number" class="form-input" />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div v-if="allInternships.length > 0" class="internships-container">
          <div v-for="internship in allInternships" style="padding: 5px">
            <div class="int" style="display: flex; align-items: center;">
              <p><strong>Name:</strong>{{ internship.name }}</p>
              <router-link :to="`/company_public/${internship.companyEmail}`"> <strong>Company: </strong>{{internship.company_name}} </router-link>
              <p><strong>Start Date:</strong> {{ internship.startDate }}</p>
              <p><strong>End Date:</strong> {{ internship.endDate }}</p>
              <p><strong>Salary:</strong> {{ internship.salary }}</p>
              <button @click="openDetails(internship)" class="popup-button">Details</button>
            </div>
          </div>

          <div v-if="showDetails" class="det">
            <div class="det-content">
              <h2>Internship Details</h2>
              <p><strong>Name:</strong> {{ selectedInternship.name }}</p>
              <p><strong>Company:</strong> {{ selectedInternship.company_name }}</p>
              <p><strong>Start Date:</strong> {{ selectedInternship.startDate }}</p>
              <p><strong>End Date:</strong> {{ selectedInternship.endDate }}</p>
              <p><strong>End Selection AcceptanceDate:</strong> {{ selectedInternship.endSelectionAcceptanceDate }}</p>
              <p><strong>End Form CompilingDate:</strong> {{ selectedInternship.endFormCompilingDate }}</p>
              <p><strong>Salary: $</strong> {{ selectedInternship.salary }}</p>
              <div style="display: flex; gap: 5px">
                <p><strong>Qualification required:</strong></p>
                <ul style="width: 90%; padding-left: 1em;">
                  <li v-for="(qualification, index) in selectedInternship.qualificationRequired" :key="index">
                    {{ qualification }}
                  </li>
                </ul>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Description:</strong></p>
                <textarea readonly style="width: 90%;"> {{ selectedInternship.description }}</textarea>
              </div>
              <div style="display: flex; gap: 5px; margin-top: 5px">
                <button @click="closeDetails" class="popup-button" style="font-size: 20px;">Close</button>
                <button @click="request" :disabled="!sendStatus[selectedInternship.internshipId]" class="popup-button" style="font-size: 20px;">Request!</button>
              </div>
            </div>
          </div>

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
.int {
  border: 3px solid black;
  border-radius: 40px;
  padding: 10px;
  display: flex;
  gap: 20px;
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
.popup-button {
  background-color: #f2a73b;
  color: black;
  border: 2px solid black;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease, border 0.3s ease;
}
.popup-button:hover {
  background-color: #232526;
  color: #f2a73b;
  border: 2px solid #f2a73b;
}
.det {
  position: fixed;
  top: 0;
  left: 0;
  width: 50vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.det-content {
  background: white;
  padding: 15px;
  border-radius: 10px;
  width: 90%;
}
.popup-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>

<script setup lang="ts">
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";
import {onMounted, ref} from "vue";
import Buttons from "@/pages/Post_SignIn/Utils/buttons.vue";

const myInternships = ref([]);
function receiveMy() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/student/myInternships', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Error in request to backend");
      })
      .then(data => {
        console.log(data);
        myInternships.value = data;
      })
      .catch(error => {
        console.error(error);
      });
}

function isBeforeDeadline(dateString) {
  if (!dateString) return false;
  const deadline = new Date(dateString);
  const today = new Date();
  return today <= deadline;
}

const showForm = ref(false);
const selectedForm = ref(null);
function openForm(internship){
  selectedForm.value = internship;
  showForm.value = true;
  console.log(selectedForm.value)
}
function closeForm() {
  showForm.value = false;
  selectedForm.value = null;
}

function send() {
  const token = localStorage.getItem('token');

  const formData = {
    internshipId: selectedForm.value.internshipId,
    formToCompile: selectedForm.value.formToCompile.map(form => ({
      formId: form.formId,
      request: form.request,
      response: form.response
    }))
  };

  fetch('http://localhost:8080/api/student/formResponses', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  })
      .then(response => {
        if (response.ok) {
          closeForm();
          receiveMy();
        } else {
          console.error('Form submission failed:', response.status);
        }
      })
      .catch(error => {
        console.error('Error submitting form:', error);
      });
}

function renounce(intId) {
  const token = localStorage.getItem('token');

  fetch(`http://localhost:8080/api/student/renounce/${intId}`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  })
      .then(response => {
        if (response.ok) {
          receiveMy();
        } else {
          console.error('Form submission failed:', response.status);
        }
      })
      .catch(error => {
        console.error('Error submitting form:', error);
      });
}


const allInternships = ref([]);
const sendStatus = ref<Record<number, boolean>>({});
function receiveAll() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/student/allInternships', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Error in request to backend");
      })
      .then(data => {
        allInternships.value = data;
        data.forEach((internship: { internshipId: number }) => {
          sendStatus.value[internship.internshipId] = true;
        });
        console.log(data);
      })
      .catch(error => {
        console.error(error);
      });
}

interface filters {
  name: string;
  start: string;
  end: string;
  salary: number;
}
const key = ref<filters>({
  name: '',
  start: '',
  end: '',
  salary: 0
})
function search() {
  const token = localStorage.getItem('token');

  const key1 = {
    keyword: key.value.name,
    minStart: key.value.start,
    maxEnd: key.value.end,
    minSalary: key.value.salary
  }

  fetch('http://localhost:8080/api/student/search', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(key1 )
  })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.log(response.status);
        }
      })
      .then(data => {
        allInternships.value = data;
      })
      .catch(error => {console.error(error);
      });
}
onMounted(() => {
  receiveAll();
  receiveMy();
});

const showDetails = ref(false);
const selectedInternship = ref(null);
function openDetails(internship) {
  selectedInternship.value = internship;
  showDetails.value = true;
}
function closeDetails() {
  showDetails.value = false;
  selectedInternship.value = null;
}
function request() {
  const token = localStorage.getItem('token');

  const id = selectedInternship.value.internshipId;
  console.log(id)

  fetch('http://localhost:8080/api/student/requestInternship', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ id })
  })
      .then(response => {
        if (response.ok) {
          sendStatus.value[id] = false;
          receiveMy();
        } else {
          console.log(response.status);
        }
      })
      .catch(error => {console.error(error);
      });
}
</script>