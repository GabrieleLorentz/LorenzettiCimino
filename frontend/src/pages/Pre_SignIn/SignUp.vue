<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo.png" alt="Logo" />
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;" @keyup.enter="goToPage" tabindex="0">
      <button @click="selectOption('student')" :class="{'selected': selectedOption === 'student', 'button1': true}">STUDENT</button>
      <button @click="selectOption('company')" :class="{'selected': selectedOption === 'company', 'button1': true}">COMPANY</button>
      <button @click="goToPage" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedOption: null,
    };
  },
  methods: {
    selectOption(option) {
      this.selectedOption = option;
    },
    goToPage() {
      if (this.selectedOption === 'student') {
        this.$router.push('/student_signup');
      } else if (this.selectedOption === 'company') {
        this.$router.push('/company_signup');
      } else {
        alert('Please select an option before signing up!');
      }
    },
    handleKeyPress(event) {
      if (event.key === 'Enter') {
        this.goToPage();
      }
    }
  },
  mounted() {
    document.addEventListener('keydown', this.handleKeyPress);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.handleKeyPress);
  }
};
</script>

<style>
.button1 {
  background-color: #f2a73b;
  color: #232526;
  border: 4px solid #232526;
  width: 90%;
  font-size: 50px;
  padding: 15px;
  border-radius: 30px;
  transition: background-color 0.4s ease, color 0.4s ease;
  font-weight: bold;
  margin-bottom: 10px;
}
.button1:hover {
  background-color: #232526;
  color: #f2a73b;
  cursor: pointer;
}
.selected {
  background-color: #232526;
  color: #f2a73b;
}
</style>
