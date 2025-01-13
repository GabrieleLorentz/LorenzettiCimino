<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo_c.png"  alt="Logo_c"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
      <input type="text" placeholder="name" class="text-input-student-signup"/>
      <input type="email" placeholder="e-mail" class="text-input-student-signup"/>
      <input type="password" placeholder="password" class="text-input-student-signup"/>
      <input type="text" placeholder="description" class="text-input-student-signup"/>
      <input type="text" placeholder="VAT" class="text-input-student-signup"/>
      <button @click="" class="button">SIGN UP</button>
    </div>
  </div>
</template>

<style>
.text-input-student-signup {
  background-color: white; /* Colore di sfondo iniziale */
  color: #f2a73b; /* Colore del testo */
  border: 4px solid #232526; /* Rimuove il bordo */
  width: 85%;
  font-size: 45px;
  padding: 10px;
  border-radius: 30px; /* Angoli arrotondati */
  transition: background-color 0.4s ease, color 0.4s ease; /* Transizione morbida per il cambiamento del colore */
  font-weight: bold;
  margin-bottom: 10px;
}

/* Effetto hover */
.text-input-student-signup:hover {
  background-color: #f2a73b; /* Colore di sfondo al passaggio del mouse */
  color: #232526;
  cursor: pointer; /* Cambia il cursore quando ci passi sopra */
}
</style>