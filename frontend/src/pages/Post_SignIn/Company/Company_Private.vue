<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex; justify-content: center;">
      <span class="orange">Private</span>
      <span style="margin-left: 7px;" class="black">page</span>
    </div>
    <!--personal data-->
    <div style="width: 100%; display: flex;">
      <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
        <div class="data">
          <input v-model="editedData.name" type="text" class="editable-input"/>
        </div>
        <div class="data">
          <input v-model="editedData.email" type="email" class="editable-input"/>
        </div>
        <div class="data">
          <input v-model="editedData.password" type="password" class="editable-input"/>
        </div>
        <div class="data">
          <textarea v-model="editedData.description" class="editable-textarea"></textarea>
        </div>
        <div class="data">
          <input v-model="editedData.vat_number" type="number" class="editable-input"/>
        </div>
        <button @click="saveAllChanges" class="save" :disabled="!hasChanges">SAVE</button>
      </div>

      <div style="flex: 1;">
        <!--shows reviews made by itself-->
        <div style="width: 100%; display: flex; justify-content: center;">
          <span class="black">My</span>
          <span style="margin-left: 7px;" class="orange">review</span>
        </div>
        <div v-if="Object.keys(groupedReviews).length > 0" style="overflow-y: auto; max-height: 260px;">
          <div v-for="(reviews, internshipKey) in groupedReviews" :key="internshipKey" style="padding: 10px; border-bottom: 2px solid #ccc;">
            <h3 style="margin-bottom: 5px;">{{ internshipKey }}</h3>
            <div v-for="(review, index) in reviews" :key="index" style="padding-left: 10px; margin-bottom: 10px;">
              <p><strong>Question:</strong> {{ review.request }}</p>
              <textarea readonly>Answer: {{ review.response }}</textarea>
            </div>
          </div>
        </div>
        <div v-else>
          <p style="font-size: 20px; text-align: center;">No reviews made</p>
        </div>
        <!--shows complaint made by students-->
        <div style="width: 100%; display: flex; justify-content: center;">
          <span class="orange">Complaints</span>
          <span style="margin-left: 7px;" class="black">received</span>
        </div>
        <div v-if="complaint.length > 0" style="overflow-y: auto; max-height: 225px;">
          <div v-for="complaint in complaint" style="padding: 10px; border-bottom: 2px solid #ccc;">
            <h3 style="margin-bottom: 5px;">{{ complaint.student.name }} - {{complaint.internship.internshipName }}</h3>
            <textarea readonly>{{ complaint.response }}</textarea>
          </div>
        </div>
        <div v-else>
          <p style="font-size: 20px; text-align: center;">No complaint received</p>
        </div>

      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed} from 'vue';
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";

interface UserData {
  name: string;
  email: string;
  password: string;
  description: string;
  vat_number: number;
}

const originalData = ref<UserData>({
  name: '',
  email: '',
  password: '',
  description: '',
  vat_number: 0
});

const editedData = ref<UserData>({
  name: '',
  email: '',
  password: '',
  description: '',
  vat_number: 0
});

const hasChanges = computed(() => {
  return originalData.value.name !== editedData.value.name ||
      originalData.value.email !== editedData.value.email ||
      originalData.value.password !== editedData.value.password ||
      originalData.value.description !== editedData.value.description ||
      originalData.value.vat_number !== editedData.value.vat_number ;
});
/**
 * receives all information about the company
 */
function receiveData() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/company/personalData', {
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
        originalData.value = {
          name: data.name,
          email: data.email,
          password: localStorage.getItem("password"),
          description: data.description,
          vat_number: data.vat_number
        };
        editedData.value = { ...originalData.value };
      })
      .catch(error => {
        console.error("Error while retrieving data:", error);
      });
}

/**
 * modify personal data
 */
function saveAllChanges() {
  const token = localStorage.getItem('token');
  const updateData = {
    name: editedData.value.name,
    email: editedData.value.email,
    password: editedData.value.password,
    description: editedData.value.description,
    vat_number: editedData.value.vat_number
  };

  fetch('http://localhost:8080/api/company/updateData', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(updateData)
  })
      .then(response => {
        if (response.ok) {
          originalData.value = {...editedData.value};
          return response.json().then(data => {
            if (editedData.value.email !== originalData.value.email ||
                editedData.value.password !== originalData.value.password) {
              localStorage.setItem("token", data.newToken);
              localStorage.setItem("email", data.email);
              localStorage.setItem("password", editedData.value.password);

              console.log(localStorage.getItem('token'))
            }
          });
        } else {
          console.log(response.status);
        }
      })
      .catch(error => {console.error('Error', error);});
}

const myReview = ref([]);
const complaint = ref([]);
/**
 * receives reviews made by itself and complaints from students
 */
function receiveMyReviewComplaint() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/company/myForms', {
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
        console.log("Dati ricevuti dal server:", data);
        myReview.value = data.filter(item => item.formType === "C_REVIEW");
        complaint.value = data.filter(item => item.formType === "S_COMPLAINT");
      })
      .catch(error => {
        console.error("Error while retrieving data:", error);
      });
}

onMounted(() => {
  receiveData();
  receiveMyReviewComplaint();
});

const groupedReviews = computed(() => {
  const grouped: Record<string, any> = {};

  myReview.value.forEach(review => {
    const internshipKey = `${review.internship.internshipName} - ${review.student.name}`;

    if (!grouped[internshipKey]) {
      grouped[internshipKey] = [];
    }
    grouped[internshipKey].push(review);
  });

  return grouped;
});
</script>