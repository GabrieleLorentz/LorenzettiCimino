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
      <textarea v-model="formData.description" placeholder="description" class="input-signup"></textarea>
      <textarea v-model="formData.cv" @blur="insertCV" type="text" placeholder="CV, separate with one / " class="input-signup" />

      <button @click="submitForm" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<style>
.input-signup {
  background-color: white;
  color: #f2a73b;
  border: 4px solid #232526;
  width: 85%;
  font-size: 25px;
  padding: 15px;
  border-radius: 30px;
  transition: background-color 0.4s ease, color 0.4s ease, border 0.4s ease;
  font-weight: bold;
  margin-bottom: 10px;
}
.input-signup:hover {
  background-color: #f2a73b;
  color: #232526;
  cursor: pointer;
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
        vat_number: '',
        cv: []
      }
    };
  },
  methods: {
    submitForm() {
      const jsonData = JSON.stringify(this.formData);

      fetch('http://localhost:8080/api/auth/registerStudent', {
        method: 'POST',
        headers: {'Content-Type': 'application/json',
          'Accept': 'application/json'},
        body: jsonData
      })
          .then(response => {
            if (response.ok) {
              return response.json().then(data => {
                localStorage.setItem("token", data.token);
                localStorage.setItem("email", data.email);
                localStorage.setItem("role", data.role);

                this.$router.push('/student_home');
              });
            } else if (response.status === 401) {
              alert('User already exists or credential not valid');
            }else {
              alert('Error. Try again later');
            }
          })
          .then(data => {console.log('Risposta dal server:', data);})
          .catch(error => {
            console.error(error);
            alert('A connection error occurred');
          });
      },
    insertCV() {
    this.formData.cv = this.formData.cv
      .split('/')
      .map(q => q.trim())
      .filter(q => q);
    }
  }
};
</script>