<template>
  <div style="width: 100%; display: flex; flex-direction: column; align-items: flex-start">

    <UpperPart></UpperPart>

    <div style="width: 100%; display: flex; justify-content: center;">
      <span class="orange">Private</span>
      <span style="margin-left: 7px;" class="black">page</span>
    </div>

    <div style="width: 100%; display: flex;">
      <div style="flex: 1; padding: 20px; display: flex; flex-direction: column; align-items: center;">
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
      <div class="vertical_line2"></div>
      <div style="flex: 1; padding: 20px;">

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
        throw new Error("Errore nella richiesta al backend");
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
        console.error("Errore durante il recupero dei dati:", error);
      });
}

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
          return response.json().then(data => {
            console.log("Dati ricevuti:", data);
            localStorage.setItem("token", data.token);
            localStorage.setItem("email", data.email);
            localStorage.setItem("password", editedData.value.password);

            //originalData.value = {...editedData.value};
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