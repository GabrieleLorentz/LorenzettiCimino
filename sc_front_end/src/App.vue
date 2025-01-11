<template>
  <div>
    <img src="/logo.png" />
  </div>
  <div style="display: flex; flex-direction: column; align-items: center;">
    <img src="/logo2.png" style="margin-top: -150px" />
    <button @click = '' style="margin-bottom: 10px;">SIGN IN</button>
    <button @click = ''>SIGN UP</button>
  </div>

</template>

<style>
body {
  background-color: white; /* Imposta lo sfondo bianco */
  margin: 0; /* Rimuove i margini predefiniti */
  padding: 0; /* Rimuove i padding predefiniti */
}

button {
  background-color: #232526; /* Colore di sfondo iniziale */
  color: #f2a73b; /* Colore del testo */
  border: none; /* Rimuove il bordo */
  width: 90%;
  font-size: 45px;
  padding: 15px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
}

/* Effetto hover */
button:hover {
  background-color: #f2a73b; /* Colore di sfondo al passaggio del mouse */
  color: #232526;
  cursor: pointer; /* Cambia il cursore quando ci passi sopra */
}
</style>