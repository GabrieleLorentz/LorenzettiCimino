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
          <span class="editable-input">{{ originalData.email  }}</span>
        </div>
        <div class="data">
          <span class="editable-textarea">{{originalData.description}}</span>
        </div>
        <div class="data">
          <span class="editable-input">{{ originalData.vat_number  }}</span>
        </div>
      </div>
      <div class="vertical_line2"></div>
      <div style="flex: 1; padding: 20px;">

      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted} from 'vue';
import UpperPart from "@/pages/Post_SignIn/Utils/upper_part.vue";

interface UserData {
  name: string;
  email: string;
  description: string;
  vat_number: number;
}

const originalData = ref<UserData>({
  name: '',
  email: '',
  description: '',
  vat_number: 0
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
          description: data.description,
          vat_number: data.vat_number
        };
      })
      .catch(error => {
        console.error("Errore durante il recupero dei dati:", error);
      });
}

onMounted(() => {
  receiveData();
});
</script>