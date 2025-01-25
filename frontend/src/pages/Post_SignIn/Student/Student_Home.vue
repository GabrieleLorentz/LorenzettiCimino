<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex;">

      <div style="flex: 1; padding: 20px;">
        <div class="my_collaboration">
          MY COLLABORATIONS IN PROGRESS
        </div>
        <div class="text">
          Accepted
        </div>
        <div class="text">
          Applied
        </div>
        <div class="text">
          Selected
        </div>
      </div>

      <div class="vertical_line"></div>

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
                  <label for="end">Max end Date:</label>
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
          <div v-for="internship in allInternships" :key="internship.id" style="padding: 5px">
            <div class="int">
              <p><strong>Name:</strong>{{ internship.name }}</p>
              <p><strong>Company:</strong>{{ internship.company_name }}</p>
              <p><strong>Start Date:</strong> {{ internship.start_date }}</p>
              <!--<p><strong>End Date:</strong> {{ internship.end_date }}</p>-->
              <p><strong>Salary:</strong> {{ internship.salary }}</p>
              <button @click="openPopup(internship)" class="popup-button">Details</button>
            </div>
          </div>

          <div v-if="showPopup" class="det">
            <div class="det-content">
              <h2>Internship Details</h2>
              <p><strong>Name:</strong> {{ selectedInternship.name }}</p>
              <p><strong>Company:</strong> {{ selectedInternship.company_name }}</p>
              <p><strong>Company email:</strong> {{ selectedInternship.company_email }}</p>
              <p><strong>Start Date:</strong> {{ selectedInternship.start_date }}</p>
              <p><strong>End Date:</strong> {{ selectedInternship.end_date }}</p>
              <p><strong>Salary: $</strong> {{ selectedInternship.salary }}</p>
              <div style="display: flex; gap: 5px">
                <p><strong>Qualification required:</strong></p>
                <textarea readonly style="width: 90%;"> {{ selectedInternship.qualification_required }}</textarea>
              </div>
              <div style="display: flex; gap: 5px">
                <p><strong>Description:</strong></p>
                <textarea readonly style="width: 90%;"> {{ selectedInternship.description }}</textarea>
              </div>
              <div style="display: flex; gap: 5px; margin-top: 5px">
                <button @click="closePopup" class="popup-button" style="font-size: 20px;">Close</button>
                <button @click="request" :disabled="!sendStatus[selectedInternship.internship_id]" class="popup-button" style="font-size: 20px;">Request!</button>
              </div>
            </div>
          </div>

        </div>
        <div v-else>
          <p>No internships available.</p>
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
.text {
  font-size: 24px;
  font-weight: bold;
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
  /*z-index: 1000;*/
}
.det-content {
  background: white;
  padding: 15px;
  border-radius: 10px;
  width: 700px;
  max-width: 90%;
}
.popup-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>

<script setup lang="ts">
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";
import {onMounted, ref} from "vue";

const allInternships = ref([]);
const sendStatus = ref<Record<number, boolean>>({});

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
        throw new Error("Errore nella richiesta al backend");
      })
      .then(data => {
        allInternships.value = data;
        data.forEach((internship: { internship_id: number }) => {
          sendStatus.value[internship.internship_id] = true; // Default: pulsante abilitato
        });
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

function search() {
  const token = localStorage.getItem('token');

  const key1 = {
    name: key.value.name,
    start: key.value.start,
    end: key.value.end,
    salary: key.value.salary
  }

  fetch('http://localhost:8080/api/student/search', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ key1 })
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
      .catch(error => {console.error('Errore errore', error);
      });
}
onMounted(() => {
  receiveAll();
});

const showPopup = ref(false);
const selectedInternship = ref(null);

function openPopup(internship) {
  selectedInternship.value = internship;
  showPopup.value = true;
}
function closePopup() {
  showPopup.value = false;
  selectedInternship.value = null;
}
function request() {
  const token = localStorage.getItem('token');

  const id = selectedInternship.value.internship_id;
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
        } else {
          console.log(response.status);
        }
      })
      .catch(error => {console.error('Errore errore', error);
      });
}
</script>