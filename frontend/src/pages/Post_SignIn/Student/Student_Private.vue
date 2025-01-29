<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex; justify-content: center;">
      <span class="orange">Private</span>
      <span style="margin-left: 7px;" class="black">page</span>
    </div>

    <div style="width: 100%; display: flex;">
      <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
        <div class="data">
          <input v-model="editedData.name" type="text" class="editable-input"/>
        </div>
        <div class="data">
          <input v-model="editedData.surname" type="text" class="editable-input"/>
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
          <ul style="max-height: 70px; overflow-y: auto;">
            <li v-for="(qualification, index) in editedData.cv" :key="index">
              {{ qualification }}
            </li>
          </ul>
        </div>
        <button @click="saveAllChanges" class="save" :disabled="!hasChanges">SAVE</button>
      </div>
      <div class="vertical_line2"></div>
      <div style="flex: 1;">
        <div style="width: 100%; display: flex; justify-content: center;">
          <span class="black">My</span>
          <span style="margin-left: 7px;" class="orange">review</span>
        </div>

        <div style="width: 100%; display: flex; justify-content: center;">
          <span class="orange">Complaints</span>
          <span style="margin-left: 7px;" class="black">received</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.vertical_line2 {
  position: absolute;
  left: 50%;
  top: 120px;
  height: calc(100vh - 120px);
  width: 2px;
  background-color: black;
}
.data {
  background-color: white;
  color: black;
  padding: 3px;
  border: 3px solid black;
  border-radius: 40px;
  font-size: 25px;
  text-align: center;
  width: 450px;
  margin-bottom: 20px;
}
.editable-input {
  width: 90%;
  background: transparent;
  border: none;
  font-size: 25px;
  text-align: center;
  outline: none;
  padding: 5px;
}
.editable-textarea {
  width: 90%;
  background: transparent;
  border: none;
  font-size: 20px;
  outline: none;
  resize: vertical;
  min-height: 45px;
}
.save {
  background-color: #232526;
  color: #f2a73b;
  cursor: pointer;
  padding: 10px 50px;
  border: 3px solid #f2a73b;
  border-radius: 40px;
  font-size: 25px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  transition: background-color 0.4s ease, color 0.4s ease, border 0.4s ease;
}
.save:hover {
  background-color: #f2a73b;
  color: black;
  border: 3px solid black;
  cursor: pointer;
}
.save:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>

<script setup lang="ts">
import { ref, onMounted, computed} from 'vue';
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";

interface UserData {
  name: string;
  surname: string;
  email: string;
  password: string;
  description: string;
  cv: string[];
}

const originalData = ref<UserData>({
  name: '',
  surname: '',
  email: '',
  password: '',
  description: '',
  cv: []
});

const editedData = ref<UserData>({
  name: '',
  surname: '',
  email: '',
  password: '',
  description: '',
  cv: []
});

const hasChanges = computed(() => {
  return originalData.value.name !== editedData.value.name ||
      originalData.value.surname !== editedData.value.surname ||
      originalData.value.email !== editedData.value.email ||
      originalData.value.password !== editedData.value.password ||
      originalData.value.description !== editedData.value.description ||
      originalData.value.cv !== editedData.value.description
});

function receiveData() {
  const token = localStorage.getItem('token');

  fetch('http://localhost:8080/api/student/personalData', {
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
          password: localStorage.getItem("password"),
          description: data.description,
          cv: data.cv
        };
        editedData.value = { ...originalData.value };
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

function saveAllChanges() {
  const token = localStorage.getItem('token');

  const updateData = {
    name: editedData.value.name,
    surname: editedData.value.surname,
    email: editedData.value.email,
    password: editedData.value.password,
    description: editedData.value.description,
    cv: editedData.value.cv
  };

  fetch('http://localhost:8080/api/student/updateData', {
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
            console.log("Dati ricevuti:", data);
            localStorage.setItem("token", data.token);
            localStorage.setItem("email", data.email);
            localStorage.setItem("password", editedData.value.password);

          });
        } else {
          console.log(response.status);
        }
      })
      .catch(error => {console.error('Errore errore', error);});
}

onMounted(() => {
  receiveData();
});
</script>