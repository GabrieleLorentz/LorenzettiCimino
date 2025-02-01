<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex; justify-content: center;">
      <span class="orange">Public</span>
      <span style="margin-left: 7px;" class="black">page</span>
    </div>

    <div style="width: 100%; display: flex;">

      <div style="flex: 1; padding: 20px; display: flex; flex-direction: column; align-items: center;">
        <div class="data">
          <span class="editable-input">{{ originalData.name }}</span>
        </div>
        <div class="data">
          <span class="editable-input">{{ originalData.surname }}</span>
        </div>
        <div class="data">
          <span class="editable-input">{{ originalData.email }}</span>
        </div>
        <div class="data">
          <textarea readonly class="editable-textarea">{{originalData.description}}</textarea>
        </div>
        <div class="data">
          <ul style="max-height: 70px; overflow-y: auto;">
            <li v-for="(qualification, index) in originalData.cv" :key="index">
              {{ qualification }}
            </li>
          </ul>
        </div>
      </div>

      <div style="flex: 1;">
        <div style="width: 100%; display: flex; justify-content: center;">
          <span class="orange">Reviews</span>
          <span style="margin-left: 7px;" class="black">received</span>
        </div>
        <div>
          <div v-if="Object.keys(groupedReviews).length > 0" style="overflow-y: auto; max-height: 260px;">
            <div v-for="(reviews, internshipKey) in groupedReviews" :key="internshipKey" style="padding: 10px; border-bottom: 2px solid #ccc;">
              <h3 style="margin-bottom: 5px;">{{ internshipKey }}</h3>
              <div v-for="(review, index) in reviews" :key="index" style="padding-left: 10px; margin-bottom: 10px;">
                <p> {{ review.request }}</p>
                <textarea readonly>{{ review.response }}</textarea>
              </div>
            </div>
          </div>
          <div v-else>
            <p style="font-size: 20px; text-align: center;">No reviews received</p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted, computed} from 'vue';
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";
import { useRoute } from 'vue-router';

const route = useRoute();
const studentEmail = route.params.email;

interface UserData {
  name: string;
  surname: string;
  email: string;
  description: string;
  cv: string[]
}

const originalData = ref<UserData>({
  name: '',
  surname: '',
  email: '',
  description: '',
  cv: []
});

function receiveData() {
  const token = localStorage.getItem('token');

  fetch(`http://localhost:8080/api/publicProfile/getDataFromStudent/${studentEmail}`, {
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
        originalData.value = {
          name: data.name,
          surname: data.surname,
          email: data.email,
          description: data.description,
          cv: data.cv
        };
        console.log(data);
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

const Review = ref([]);
function receiveMyReview() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/student/myForms', {
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
        console.log("Dati ricevuti dal server:", data);
        Review.value = data.filter(item => item.formType === "C_REVIEW");
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

onMounted(() => {
  receiveData();
  receiveMyReview();
});

const groupedReviews = computed(() => {
  const grouped: Record<string, any> = {};

  Review.value.forEach(review => {
    const internshipKey = `${review.internship.internshipName} - ${review.internship.companyName}`;

    if (!grouped[internshipKey]) {
      grouped[internshipKey] = [];
    }
    grouped[internshipKey].push(review);
  });

  return grouped;
});
</script>