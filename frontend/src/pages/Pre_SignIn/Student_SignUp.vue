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
      <input v-model="formData.description" type="text" placeholder="description" class="text-input-signin" />
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