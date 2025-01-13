<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo_s.png"  alt="Logo_s"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <input v-model="formData.name" type="text" placeholder="name" class="text-input-student-signup" />
      <input v-model="formData.surname" type="text" placeholder="surname" class="text-input-student-signup" />
      <input v-model="formData.email" type="email" placeholder="e-mail" class="text-input-student-signup" />
      <input v-model="formData.password" type="password" placeholder="password" class="text-input-student-signup" />
      <input v-model="formData.description" type="text" placeholder="description" class="text-input-student-signup" />
      <!--<input v-model="formData.cv" type="text" placeholder="CV" class="text-input-student-signup" /> -->

      <button @click="submitForm" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        name: '',
        surname: '',
        email: '',
        password: '',
        description: '',
        //cv: ''
      }
    };
  },
  methods: {
    submitForm() {
      // Convert formData to JSON
      const jsonData = JSON.stringify(this.formData);
      console.log(jsonData);
      // Esempio di invio al backend usando fetch
      fetch('http://localhost:8080/api/student', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: jsonData
      })
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error('Errore durante l\'invio dei dati');
            }
          })
          .then(data => {
            console.log('Risposta dal server:', data);
          })
          .catch(error => {
            console.error('Errore:', error);
          });
    }
  }
};
</script>

<style>
.text-input-student-signup {
  background-color: white; /* Colore di sfondo iniziale */
  color: #f2a73b; /* Colore del testo */
  border: 4px solid #232526; /* Rimuove il bordo */
  width: 85%;
  font-size: 45px;
  padding: 10px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
  margin-bottom: 10px;
}

/* Effetto hover */
.text-input-student-signup:hover {
  background-color: #f2a73b; /* Colore di sfondo al passaggio del mouse */
  color: #232526;
  cursor: pointer; /* Cambia il cursore quando ci passi sopra */
}
</style>