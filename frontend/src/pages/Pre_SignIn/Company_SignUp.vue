<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo_c.png"  alt="Logo_c"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <input v-model="formData.name" type="text" placeholder="name" class="text-input-signin" />
      <input v-model="formData.email" type="email" placeholder="e-mail" class="text-input-signin" />
      <input v-model="formData.password" type="password" placeholder="password" class="text-input-signin" />
      <textarea v-model="formData.description" placeholder="description" class="input-signup"></textarea>
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

      const jsonData = JSON.stringify(this.formData);

      fetch('http://localhost:8080/api/auth/registerCompany', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: jsonData
      })
          .then(response => {
            if (response.ok) {
              return response.json().then(data => {
                localStorage.setItem("token", data.token);
                localStorage.setItem("email", data.email);
                localStorage.setItem("role", data.role);

                this.$router.push('/company_home');
              });
            } else if (response.status === 401) {
              alert('User already exists or credential not valid');
            }else {
              alert('Error. Try again later')
            }
          })
          .catch(error => {
            console.error(error);
            alert('A connection error occurred');
          });
    }
  }
};
</script>