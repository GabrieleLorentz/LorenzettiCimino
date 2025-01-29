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
                <button @click="openResponse(internship)" class="popup-button" style="font-size: 15px;">Response Students </button>
              </div>
            </div>
          </div>

          <div v-if="showRequest" class="det">
            <div class="det-content">
              <h2>Students</h2>
              <div v-for="student in selecteInternship.applicants" style="padding: 5px; display: flex; gap: 10px">
                <div class="profile_cont">
                  <p> {{student.name}} </p>
                  <div class="profile">
                    <p> {{student.name}} </p>
                    <p> {{student.surname}} </p>
                    <p> {{student.email}} </p>
                    <textarea> {{student.description}} </textarea>
                    <ul style="width: 90%; padding-left: 1em;">
                      <li v-for="(row, index) in student.cv" :key="index">
                        {{ row }}
                      </li>
                    </ul>
                  </div>
                </div>
                <p> {{student.surname}}</p>
                <button class="yes" @click="accepted(student.email, selecteInternship.id)" >Yes</button>
              </div>
              <button @click="closeRequest" class="popup-button" style="font-size: 20px;">Close</button>
            </div>
          </div>

          <div v-if="showResponse" class="det">
            <div class="det-content internships-container">
              <h2>Forms</h2>
              <div v-for="(responses, studentEmail) in groupedForms" :key="studentEmail" style="border-bottom: 2px solid black;">
                <p><strong>Student Name:</strong> {{ responses[0].student.name }} {{ responses[0].student.surname }}</p>
                <ul>
                  <li v-for="(form, index) in responses" :key="index">
                    <strong>Request:</strong> {{ form.request }} <br>
                    <strong>Response:</strong> {{ form.response }}
                  </li>
                </ul>
                <button class="yes" @click="selected(responses[0].student.email, selecteInternship.id)" >Yes</button>
              </div>
              <button @click="closeResponse" class="popup-button" style="font-size: 20px;">Close</button>
            </div>
          </div>

        </div>
        <div v-else style="font-size: 30px">
          <p>No internships available.</p>
        </div>
      </div>

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
.profile_cont {
  position: relative;
}
.profile_cont:hover .profile{
  display: block;
}
.profile {
  display: none;
  position: absolute;
  left: 0;
  background-color: #f2a73b;
  border: 2px solid black;
  border-radius: 15px;
  padding: 4px;
}
.yes:hover {
  color: green;
  font-size: 15px;
}
</style>

<script setup lang="ts">
import UpperPart from '@/pages/Post_SignIn/Utils/upper_part.vue';
import {ref, onMounted, computed} from "vue";

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
        } else if (response.status === 409) {
          alert('Student already accepted');
        } else {
          alert('Error. Try again later');
        }
      })
      .then(data => {
        console.log(data)
        console.log("ciao")
      })
      .catch(error => {
        console.error('Errore:', error);
        alert('A connection error occurred');
      });
}

const showResponse = ref(false);
function openResponse(internship) {
  selecteInternship.value = internship;
  showResponse.value = true;
}
function closeResponse() {
  showResponse.value = false;
  selecteInternship.value = null;
}
const groupedForms = computed(() => {
  if (!selecteInternship.value || !selecteInternship.value.formWithStudents) return {};

  return selecteInternship.value.formWithStudents.reduce((acc, form) => {
    const email = form.student.email;
    if (!acc[email]) {
      acc[email] = [];
    }
    acc[email].push(form);
    return acc;
  }, {});
});
function selected(email, internshipId) {
  const token = localStorage.getItem('token');
  console.log(selecteInternship)

  fetch(`http://localhost:8080/api/company/studentSelected/${email}_${internshipId}`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`

    },
  })
      .then(response => {
        if (response.ok) {
          return;
        } else if (response.status === 409) {
          alert('Student already selected');
        } else {
          alert('Error. Try again later');
        }
      })
      .then(data => {
        console.log(data)
        console.log("ciao")
      })
      .catch(error => {
        console.error('Errore:', error);
        alert('A connection error occurred');
      });
}
</script>