<template>
  <div style="width: 100%">
    <span class="orange">Student</span>
    <span class="black">&</span>
    <span class="orange">Company</span>
    <span class="black"> - Company</span>
    <hr class="horizontal-line"/>
    <div class="vertical-line"></div>
  </div>
</template>

<style>
.orange {
  color: #f2a73b;
  font-size: 40px;
  font-weight: bold;
}
.black {
  color: black;
  font-size: 40px;
  font-weight: bold;
}
.horizontal-line {
  width: 100%;
  border: none;
  height: 2px;
  background-color: black;
}
.vertical-line {
  position: absolute; /* Posizionamento assoluto */
  top: 55px; /* Posiziona sotto la riga orizzontale (2px è lo spessore della riga orizzontale) */
  left: 50%; /* Centra la riga verticale */
  width: 2px; /* Larghezza della riga verticale */
  height: 90%; /* Altezza totale dello schermo meno un margine */
  background-color: black; /* Colore della riga */
}
</style>
<script setup lang="ts">
</script>