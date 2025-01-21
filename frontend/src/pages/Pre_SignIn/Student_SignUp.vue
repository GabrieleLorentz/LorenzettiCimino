<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo_s.png"  alt="Logo_s"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <input v-model="formData.name" type="text" placeholder="name" class="text-input-signin" />
      <input v-model="formData.surname" type="text" placeholder="surname" class="text-input-signin" />
      <input v-model="formData.email" type="email" placeholder="e-mail" class="text-input-signin" />
      <input v-model="formData.password" type="password" placeholder="password" class="text-input-signin" />
      <textarea v-model="formData.description" placeholder="description" class="description-input-signin"></textarea>
      <!--<input v-model="formData.cv" type="text" placeholder="CV" class="text-input-student-signup" /> -->

      <button @click="submitForm" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<style>
.description-input-signin {
  background-color: white; /* Colore di sfondo iniziale */
  color: #f2a73b; /* Colore del testo */
  border: 4px solid #232526;
  width: 90%;
  font-size: 25px;
  padding: 15px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
  margin-bottom: 10px;
}
</style>

<script>
export default {
  data() {
    return {
      formData: {
        email: '',
        password: '',
        name: '',
        surname: '',
        description: '',
        vat_number: ''
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
      fetch('http://localhost:8080/api/auth/registerStudent', {
        method: 'POST',
        headers: {'Content-Type': 'application/json',
          'Accept': 'application/json'},
        body: jsonData
      })
          .then(response => {
            if (response.ok) {
              return response.json().then(data => {
                sessionStorage.setItem("token", data.token);
                sessionStorage.setItem("email", data.email);
                sessionStorage.setItem("role", data.role);

                this.$router.push('/student_home');
              });
            } else {
              throw new Error('Errore durante l\'invio dei dati');
            }
          })
          .then(data => {console.log('Risposta dal server:', data);})
          .catch(error => {console.error('Errore:', error);});
    }
  }
};
</script>