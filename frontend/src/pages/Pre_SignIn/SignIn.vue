<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo.png"  alt="Logo"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <form @submit.prevent="submitForm">
        <input v-model="formData.email" type="email" placeholder="e-mail" class="text-input-signin" />
        <input v-model="formData.password" type="password" placeholder="password" class="text-input-signin" />
        <button type="submit" class="button">SIGN IN</button>
      </form>
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
      }
    };
  },
  methods: {
    submitForm() {

      const jsonData = JSON.stringify(this.formData);

      fetch('http://localhost:8080/api/auth/authenticate', {
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
                localStorage.setItem("password", this.formData.password);

                if (data.role === '[STUDENT]') {
                  window.location.href = '/student_home';
                }
                else if (data.role === '[COMPANY]') {
                  window.location.href = '/company_home';
                }
              });
            } else if (response.status === 401) {
              alert('Incorrect email or password');
            }else {
              alert('Error. Try again later')
            }
          })
          .catch(error => {
            console.error('Error:', error);
            alert('A connection error occurred')
          });
    }
  }
};
</script>

<style>
.text-input-signin {
  background-color: white;
  color: #f2a73b;
  border: 4px solid #232526;
  width: 85%;
  font-size: 30px;
  padding: 15px;
  border-radius: 30px;
  transition: background-color 0.4s ease, color 0.4s ease, border 0.4s ease;
  font-weight: bold;
  margin-bottom: 10px;
}
.text-input-signin:hover {
  background-color: #f2a73b;
  color: #232526;
  cursor: pointer;
}
</style>
<script setup lang="ts">
</script>