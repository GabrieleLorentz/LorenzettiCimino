<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex;">

      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY INTERNSHIP
        </div>
        <!--shows the internship of the company not started yet-->
        <div v-if="upcomingInternships.length > 0" class="internships-container">
          <div v-for="internship in upcomingInternships" :key="internship.id" style="padding: 5px">
            <div style="border: 3px solid black; border-radius: 40px; padding: 10px; display: flex; flex-direction: column; gap: 5px">
              <div style="display: flex; gap: 10px">
                <p><strong>Name:</strong> {{ internship.name }}</p>
                <p><strong>Company:</strong> {{ internship.companyName }}</p>
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
                <button @click="openSelected(internship)" class="popup-button" style="font-size: 15px;">Selected Students </button>
              </div>
            </div>
          </div>
          <!--list of students that have applied to participate-->
          <div v-if="showRequest" class="det">
            <div class="det-content internships-container">
              <h2>Students</h2>
              <div v-for="student in selectedInternship.applicants" style="padding: 5px; display: flex; align-items: center; gap: 10px">
                <router-link :to="`/student_public/${student.email}`" style="font-size: 20px"> {{student.name}} </router-link>
                <p style="font-size: 20px"> {{student.surname}}</p>
                <button class="yes" @click="accepted(student.email, selectedInternship.internshipId)">Yes</button>
              </div>
              <button @click="closeRequest" class="popup-button" style="font-size: 20px;">Close</button>
            </div>
          </div>
          <!--list of students who submitted the form with the interview answers-->
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
                <button class="yes" @click="selected(responses[0].student.email, selectedInternship.internshipId)" >Yes</button>
              </div>
              <button @click="closeResponse" class="popup-button" style="font-size: 20px;">Close</button>
            </div>
          </div>
          <!--list of students who have been selected for the internship-->
          <div v-if="showSelected" class="det">
            <div class="det-content internships-container">
              <h2>Students</h2>
              <div v-for="student in selectedInternship.selected" style="padding: 5px; display: flex; align-items: center; gap: 10px">
                <router-link :to="`/student_public/${student.email}`" style="font-size: 20px"> {{student.name}} </router-link>
                <p style="font-size: 20px"> {{student.surname}} </p>
              </div>
              <div>
                <button @click="closeSelected" class="popup-button" style="font-size: 20px;">Close</button>
              </div>
            </div>
          </div>

        </div>
        <div v-else style="font-size: 30px; text-align: center">
          <p>You haven't added any internships yet</p>
        </div>
      </div>

      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY COLLABORATIONS IN PROGESS
        </div>
        <!--shows the internship of the company already started-->
        <div v-if="ongoingInternships.length > 0" class="internships-container">
          <div v-for="internship in ongoingInternships" :key="internship.id" style="padding: 5px">
            <div style="border: 3px solid black; border-radius: 40px; padding: 10px; display: flex; flex-direction: column; gap: 5px">
              <div style="display: flex; gap: 10px">
                <p><strong>Name:</strong> {{ internship.name }}</p>
                <p><strong>Company:</strong> {{ internship.companyName }}</p>
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
                  <li v-for="(qualification, index) in internship.qualification_required" :key="index">
                    {{ qualification }}
                  </li>
                </ul>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Description:</strong></p>
                <textarea readonly style="width: 90%;"> {{ internship.description }}</textarea>
              </div>
              <button @click="openStudent(internship)" class="popup-button" style="font-size: 15px;">Selected Students </button>
              <div v-if="showStudent" class="det">
                <div class="det-content">
                  <h2>Students</h2>
                  <div v-for="student in selectedInternship.selected" >
                    <div style="display: flex; align-items: center; gap: 50px">
                      <div style="display: flex; align-items: center; gap: 10px">
                        <router-link :to="`/student_public/${student.email}`" style="font-size: 20px"> {{student.name}} </router-link>
                        <p style="font-size: 20px"> {{student.surname}} </p>
                      </div>
                      <!--buttons to send complant, feedbacks and review-->
                      <buttons :internship="internship" :student="student"/>
                    </div>
                  </div>
                  <button @click="closeStudent" class="popup-button" style="font-size: 20px;">Close</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else style="font-size: 30px; text-align: center">
          <p>You haven't added any internships yet or started</p>
        </div>
      </div>

    </div>
  </div>
</template>

<style>
.internships-container {
  max-height: 540px;
  min-height: 540px;
  overflow-y: auto;
}
.yes:hover {
  color: green;
  font-size: 15px;
}
</style>

<script setup lang="ts">
import UpperPart from '@/pages/Post_SignIn/Utils/upper_part.vue';
import {ref, onMounted, computed} from "vue";
import buttons from '@/pages/Post_SignIn/Utils/buttons.vue';

const internships = ref([]);
const ongoingInternships = computed(() => {
  return internships.value.filter(internship => new Date(internship.startDate) <= new Date());
});
const upcomingInternships = computed(() => {
  return internships.value.filter(internship => new Date(internship.startDate) > new Date());
});

/**
 * returns the company's internship data
 */
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
        throw new Error("Error in request to backend");
      })
      .then(data => {
        internships.value = data;
      })
      .catch(error => {
        console.error("Error while retrieving data:", error);
      });
}

onMounted(() => {
  receiveData();
});

const selectedInternship = ref(null);

const showRequest = ref(false);
function openRequest(internship) {
  selectedInternship.value = internship;
  showRequest.value = true;
}
function closeRequest() {
  showRequest.value = false;
  selectedInternship.value = null;
}

/**
 * it is used to accept a student, the form to fill out will be automatically sent to him/her
 * @param email
 * @param internshipId
 */
function accepted(email, internshipId) {
  const token = localStorage.getItem('token');

  fetch(`http://localhost:8080/api/company/studentAccepted/${email}_${internshipId}`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  })
      .then(response => {
        if (response.ok) {
          receiveData();
          return;
        } else if (response.status === 409) {
          alert('Student already accepted');
        } else {
          alert('Error. Try again later');
        }
      })
      .catch(error => {
        console.error(error);
        alert('A connection error occurred');
      });
}

const showResponse = ref(false);
function openResponse(internship) {
  selectedInternship.value = internship;
  showResponse.value = true;
}
function closeResponse() {
  showResponse.value = false;
  selectedInternship.value = null;
}
const groupedForms = computed(() => {
  if (!selectedInternship.value || !selectedInternship.value.formWithStudents) return {};

  return selectedInternship.value.formWithStudents.reduce((acc, form) => {
    const email = form.student.email;
    if (!acc[email]) {
      acc[email] = [];
    }
    acc[email].push(form);
    return acc;
  }, {});
});

/**
 * used to select a student the company wants in the internship
 * @param email
 * @param intId
 */
function selected(email, intId) {
  const token = localStorage.getItem('token');

  fetch(`http://localhost:8080/api/company/studentSelected/${email}_${intId}`, {
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

const showSelected = ref(false);
function openSelected(internship) {
  selectedInternship.value = internship;
  showSelected.value = true;
}
function closeSelected() {
  showSelected.value = false;
  selectedInternship.value = null;
}

const showStudent = ref(false);
function openStudent(internship) {
  selectedInternship.value = internship;
  showStudent.value = true;
}
function closeStudent() {
  showStudent.value = false;
  selectedInternship.value = null;
}
</script>