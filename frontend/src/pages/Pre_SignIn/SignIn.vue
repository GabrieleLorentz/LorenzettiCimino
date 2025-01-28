<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo.png"  alt="Logo"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <input v-model="formData.email" type="email" placeholder="e-mail" class="text-input-signin" />
      <input v-model="formData.password" type="password" placeholder="password" class="text-input-signin" />
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <button @click="submitForm" class="button">SIGN IN</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        email: '',
        password: ''
      },
      errorMessage: ''
    };
  },
  methods: {
    submitForm() {
      // Convert formData to JSON
      const jsonData = JSON.stringify(this.formData);
      console.log(jsonData);
      // Esempio di invio al backend usando fetch
      fetch('http://localhost:8080/api/auth/authenticate', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: jsonData
      })
          .then(response => {
            if (response.ok) {
              return response.json().then(data => {
                console.log("Dati ricevuti:", data);
                localStorage.setItem("token", data.token);
                localStorage.setItem("email", data.email);
                localStorage.setItem("role", data.role);
                localStorage.setItem("password", this.formData.password);

                if (data.role === '[STUDENT]') {
                  window.location.href = '/student_home';  //funziona
                }
                else if (data.role === '[COMPANY]') {
                  window.location.href = '/company_home';
                }
              });
            } else if (response.status === 401) {
              console.log(response.status);
              this.errorMessage = 'Incorrect email or password';
            }else {
              this.errorMessage = 'Error. Try again later'
            }
          })
          .then(data => {console.log('Risposta dal server:', data);})
          .catch(error => {
            console.error('Errore:', error);
            this.errorMessage = 'A connection error occurred';
          });
    }
  }
};
</script>

<style>
.text-input-signin {
  background-color: white; /* Colore di sfondo iniziale */
  color: #f2a73b; /* Colore del testo */
  border: 4px solid #232526;
  width: 85%;
  font-size: 40px;
  padding: 15px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease, border 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
  margin-bottom: 10px;
}

/* Effetto hover */
.text-input-signin:hover {
  background-color: #f2a73b; /* Colore di sfondo al passaggio del mouse */
  color: #232526;
  cursor: pointer; /* Cambia il cursore quando ci passi sopra */
}
.error-message {
  color: red;
  margin: 10px 0;
  text-align: center;
  font-size: 25px;
}
</style>
<script setup lang="ts">
</script>