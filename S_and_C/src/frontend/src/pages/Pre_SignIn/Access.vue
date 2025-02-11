<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div>
      <img src="/src/assets/logo.png"  alt="Logo"/>
    </div>
    <div style="display: flex; flex-direction: column; align-items: center; margin-left: 150px;">
      <img src="/src/assets/logo2.png"  alt="Logo2"/>
      <button @click="goToPage('/signin')" style="margin-bottom: 10px;" class="button">SIGN IN</button>
      <button @click="goToPage('/signup')" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<style>
.button {
  background-color: #232526;
  color: #f2a73b;
  border: 4px solid #f2a73b;
  width: 90%;
  font-size: 50px;
  padding: 15px;
  border-radius: 30px;
  transition: background-color 0.4s ease, color 0.4s ease;
  font-weight: bold;
}
.button:hover {
  background-color: #f2a73b;
  color: #232526;
  cursor: pointer;
}
</style>
<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();
const goToPage = (path: string) => {
  router.push(path);
};
</script>
