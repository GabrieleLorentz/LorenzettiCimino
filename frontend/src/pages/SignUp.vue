<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo.png"  alt="Logo"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <button @click="" class="button1">STUDENT</button>
      <button @click="" class="button1">COMPANY</button>
      <button @click="" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<style>
.button1 {
  background-color: #f2a73b; /* Colore di sfondo iniziale */
  color: #232526; /* Colore del testo */
  border: 4px solid #232526; /* Rimuove il bordo */
  width: 90%;
  font-size: 50px;
  padding: 15px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
  margin-bottom: 10px;
}

/* Effetto hover */
.button1:hover {
  background-color: #232526; /* Colore di sfondo al passaggio del mouse */
  color: #f2a73b;
  cursor: pointer; /* Cambia il cursore quando ci passi sopra */
}
</style>
<script setup lang="ts">
</script>