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
body {
  background-color: white; /* Imposta lo sfondo bianco */
  margin: 0; /* Rimuove i margini predefiniti */
  padding: 0; /* Rimuove i padding predefiniti */
}

.button {
  background-color: #232526; /* Colore di sfondo iniziale */
  color: #f2a73b; /* Colore del testo */
  border: 4px solid #f2a73b; /* Rimuove il bordo */
  width: 90%;
  font-size: 50px;
  padding: 15px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
}

/* Effetto hover */
.button:hover {
  background-color: #f2a73b; /* Colore di sfondo al passaggio del mouse */
  color: #232526;
  cursor: pointer; /* Cambia il cursore quando ci passi sopra */
}
</style>
<script setup lang="ts">
import { useRouter } from 'vue-router';
// Inizializziamo il router
const router = useRouter();
// Funzione per navigare verso una pagina specifica
const goToPage = (path: string) => {
  router.push(path);
};
</script>
