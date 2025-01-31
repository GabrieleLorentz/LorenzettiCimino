<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex;">

      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY INTERNSHIP
        </div>
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
                <button @click="openSelected(internship)" class="popup-button" style="font-size: 15px;">Selected Students </button>
              </div>
            </div>
          </div>

          <div v-if="showRequest" class="det">
            <div class="det-content">
              <h2>Students</h2>
              <div v-for="student in selectedInternship.applicants" style="padding: 5px; display: flex; gap: 10px">
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
                <button class="yes" @click="accepted(student.email, selectedInternship.id)" >Yes</button>
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
                <button class="yes" @click="selected(responses[0].student.email, selectedInternship.id)" >Yes</button>
              </div>
              <button @click="closeResponse" class="popup-button" style="font-size: 20px;">Close</button>
            </div>
          </div>

          <div v-if="showSelected" class="det">
            <div class="det-content">
              <h2>Students</h2>
              <div v-for="student in selectedInternship.selected" style="padding: 5px; display: flex; gap: 10px">
                <div class="profile_cont" style="display: flex; gap: 5px">
                  <p> {{student.name}} </p>
                  <p> {{student.surname}} </p>
                </div>
              </div>
              <button @click="closeSelected" class="popup-button" style="font-size: 20px;">Close</button>
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
              <button @click="openStudent(internship)" class="popup-button" style="font-size: 15px;">Selected Students </button>
              <div v-if="showStudent" class="det">
                <div class="det-content">
                  <h2>Students</h2>
                  <div v-for="student in selectedInternship.selected" style="padding: 5px; display: flex; gap: 10px">
                    <div class="profile_cont" style="display: flex; gap: 50px">
                      <div style="display: flex; gap: 5px">
                        <p style="font-size: 20px"> {{student.name}} </p>
                        <p style="font-size: 20px"> {{student.surname}} </p>
                      </div>
                      <div style="display: flex; gap: 10px">
                        <div class="profile-container">
                          <button class="popup-button" style="font-size: 15px;">Complaint</button>
                          <div class="popupCom">
                            <textarea v-model="complaint_" placeholder="Write a complaint"></textarea>
                            <button @click="RecComplaint(internship.id)" class="popup-button">Send</button>
                          </div>
                        </div>
                        <div class="profile-container">
                          <button class="popup-button" style="font-size: 15px;">Feedback</button>
                          <div class="popupCom" style="min-width: 350px; left: -100px;">
                            <div v-for="(question, index) in questionsFeed" :key="index">
                              <label>{{ question }}</label>
                              <select v-model="feedback_[index]">
                                <option disabled value="">Please select one</option>
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                              </select>
                            </div>
                            <button @click="RecFeedback(internship.id, student.email)" class="popup-button">Send</button>
                          </div>
                        </div>
                        <div class="profile-container">
                          <button class="popup-button" style="font-size: 15px;">Review</button>
                          <div class="popupCom" style="min-width: 350px;">
                            <p>{{questionsRev[0]}}</p>
                            <select v-model="review_[0]">
                              <option disabled value="">Please select one</option>
                              <option value="0">0</option>
                              <option value="1">1</option>
                              <option value="2">2</option>
                              <option value="3">3</option>
                              <option value="4">4</option>
                              <option value="5">5</option>
                            </select>
                            <p>{{questionsRev[1]}}</p>
                            <textarea v-model="review_[1]"></textarea>
                            <button @click="RecReview(internship.id, student.email)" class="popup-button">Send</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <button @click="closeStudent" class="popup-button" style="font-size: 20px;">Close</button>
                </div>
              </div>
            </div>
          </div>
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
.profile-container:hover .popupCom {
  display: block;
}
.popupCom {
  display: none;
  position: absolute;
  left: 0;
  top: 22px;
  background-color: #f2a73b;
  border: 2px solid black;
  border-radius: 15px;
  padding: 4px;
}
</style>

<script setup lang="ts">
import UpperPart from '@/pages/Post_SignIn/Utils/upper_part.vue';
import {ref, onMounted, computed, watch} from "vue";

const internships = ref([]);
const ongoingInternships = computed(() => {
  return internships.value.filter(internship => new Date(internship.startDate) <= new Date());
});
const upcomingInternships = computed(() => {
  return internships.value.filter(internship => new Date(internship.startDate) > new Date());
});

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
function accepted(email, internshipId) {
  const token = localStorage.getItem('token');
  console.log(selectedInternship)

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
function selected(email, intId) {
  const token = localStorage.getItem('token');
  console.log(selectedInternship)

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

const complaint_ = ref('');
function RecComplaint(intId) {
  const token = localStorage.getItem('token');

  const formComp = {
    internshipId: intId,
    complaint: complaint_.value
  }

  fetch(`http://localhost:8080/api/company/sendComplaints`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formComp)
  })
      .then(response => {
        if (response.ok) {
          return;
        } else if (response.status === 409) {
          alert('Student not selected for this internship');
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
const questionsFeed = ref<string[]>([
  "The service/product met my expectations:",
  "I found the experience user-friendly and intuitive.",
  "The staff/team was helpful and professional.",
  "I would recommend this service/product to others.",
  "I am satisfied with the overall quality."
]);
const feedback_ = ref<string[]>([]);
watch(questionsFeed, (newQuestions) => {
  feedback_.value = newQuestions.map(() => "");
}, { immediate: true });
function RecFeedback(intId, email) {
  const token = localStorage.getItem('token');

  const formFeed = {
    internshipId: intId,
    studEmailForCompanyOnly: email,
    feedbacks: feedback_.value
  }

  fetch(`http://localhost:8080/api/company/sendFeedback`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formFeed)
  })
      .then(response => {
        if (response.ok) {
          return;
        } else if (response.status === 409) {
          alert('Student not selected for this internship');
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
const questionsRev = ref<string[]>([
  "How do you rate this experience?",
  "What are your suggestions?"
]);
const review_ = ref<string[]>([]);
function RecReview(intId, email) {
  const token = localStorage.getItem('token');

  const formRev = {
    internshipId: intId,
    studEmailForCompanyOnly: email,
    review: review_.value
  }

  fetch(`http://localhost:8080/api/company/sendReview`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formRev)
  })
      .then(response => {
        if (response.ok) {
          return;
        } else if (response.status === 409) {
          alert('Student not selected for this internship');
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