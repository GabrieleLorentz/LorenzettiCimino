<template>
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
      <div class="popupCom" style="min-width: 200px;">
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
</template>

<style>
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
import {ref, watch} from "vue";

const { internship, student } = defineProps<{
  internship: { id: number };
  student: { email: string };
}>();

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