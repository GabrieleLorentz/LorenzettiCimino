<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100vh;">
    <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
      <img src="/src/assets/logo.png"  alt="Logo"/>
    </div>
    <div style="flex: 1; display: flex; flex-direction: column; align-items: center;">
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
      selectedOption: null, // Memorizza quale bottone è stato selezionato
    };
  },
  methods: {
    selectOption(option) {
      this.selectedOption = option; // Aggiorna l'opzione selezionata
    },
    goToPage() {
      if (this.selectedOption === 'student') {
        this.$router.push('/student_signup'); // Naviga alla pagina di signup dello studente
      } else if (this.selectedOption === 'company') {
        this.$router.push('/company_signup'); // Naviga alla pagina di signup dell'azienda
      } else {
        alert('Please select an option before signing up!'); // Avvisa l'utente se nessuna opzione è selezionata
      }
    },
  },
};
</script>

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

.selected {
  background-color: #232526; /* Colore di sfondo per il bottone selezionato */
  color: #f2a73b;
}
</style>