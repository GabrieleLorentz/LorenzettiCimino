<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo_c.png"  alt="Logo_c"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <input v-model="formData.name" type="text" placeholder="name" class="text-input-signin" />
      <input v-model="formData.email" type="email" placeholder="e-mail" class="text-input-signin" />
      <input v-model="formData.password" type="password" placeholder="password" class="text-input-signin" />
      <textarea v-model="formData.description" placeholder="description" class="description-input-signin"></textarea>
      <input v-model="formData.vat_number" type="number" placeholder="VAT" class="text-input-signin" />

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
      }
    };
  },
  methods: {
    submitForm() {
      // Convert formData to JSON
      const jsonData = JSON.stringify(this.formData);
      console.log(jsonData);
      // Esempio di invio al backend usando fetch
      fetch('http://localhost:8080/api/auth/registerCompany', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: jsonData
      })
          .then(response => {
            if (response.ok) {
              return response.json().then(data => {
                sessionStorage.setItem("token", data.token);
                sessionStorage.setItem("email", data.email);
                sessionStorage.setItem("role", data.role);

                this.$router.push('/company_home');
              });
            } else {
              throw new Error('Errore durante l\'invio dei dati');
            }
          })
          .then(data => {console.log('Risposta dal server:',data);})
          .catch(error => {console.error('Errore:', error);});
    }
  }
};
</script>