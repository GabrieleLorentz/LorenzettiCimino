<template>
  <!--displays and manages the top of the screen depending on whether it is a student or a company-->
  <div style="width: calc(100% - 20px); display: flex; justify-content: space-between; align-items: center;  margin-left: 10px; margin-right: 10px;">
    <div style="display: flex; align-items: center;">
      <span class="orange">Student</span>
      <span class="black">&</span>
      <span class="orange">Company</span>
      <span v-if="subtitle" style="margin-left: 7px" class="black">{{ subtitle }}</span>
    </div>
    <div style="display: flex; gap: 10px; align-items: center;">
      <AddInt v-if="showAddIcon" ></AddInt>
      <img @click="goToPage('/rank_list')" src="/src/assets/Rank_List.svg" alt="Rank_List" class="icon2 icon_hover"/>
      <div class="profile-container">
        <img src="/src/assets/profilo.svg" alt="profilo" class="icon1 icon_hover"/>
        <div class="popup">
          <p>PROFILE</p>
          <router-link :to="publicProfile" class="popup-link" style="margin-top: -25px;"> Public </router-link>
          <span @click="goToPage(privateProfile)" class="popup-link">Private</span>
        </div>
      </div>
      <img @click="goToPage(home)" src="/src/assets/home.svg" alt="home" class="icon1 icon_hover"/>
    </div>
  </div>
  <hr class="horizontal_line" />
</template>

<script>
import AddInt from "@/pages/Post_SignIn/Utils/AddInt.vue";

/**
 * manages the top of the screen depending on whether it is a student or a company
 */
export default {
  components: {AddInt},
  data() {
    return {
      role: localStorage.getItem("role"),
      emailP: localStorage.getItem("email")
    };
  },
  computed: {
    subtitle() {
      console.log(localStorage.getItem("role"));
      return this.role === "[STUDENT]" ? "- Student" : "- Company";
    },
    showAddIcon() {
      return this.role !== "[STUDENT]";
    },
    home(){
      return this.role === "[STUDENT]" ? "/student_home" : "/company_home";
    },
    publicProfile() {
      return this.role === "[STUDENT]" ? `/student_public/${this.emailP}` : `/company_public/${this.emailP}`;
    },
    privateProfile() {
      return this.role === "[STUDENT]" ? "/student_private" : "/company_private";
    },
  },
  methods: {
    goToPage(path) {
      this.$router.push(path);
    },
  },
};
</script>


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
.icon1 {
  width: 35px;
  height: 35px;
}
.icon2 {
  width: 40px;
  height: 40px;
  cursor: pointer;
}
.icon_hover:hover{
  cursor: pointer;
  transition: filter 0.2s ease;
  filter: invert(71%) sepia(45%) saturate(669%) hue-rotate(346deg) brightness(96%) contrast(92%);
  transform: scale(1.2);
}
.profile-container {
  position: relative;
  display: inline-block;
}
.profile-container:hover .popup {
  display: block;
}
.popup {
  display: none;
  position: absolute;
  right: 0;
  background-color: #f2a73b;
  border: 2px solid black;
  border-radius: 15px;
  padding: 4px;
  z-index: 10;
}
.popup-link {
  display: block;
  font-size: 25px;
  color: black;
  text-decoration: none;
  text-align: center;
}
.popup-link:hover {
  color: white;
  cursor: pointer;
}
.popup p {
  font-size: 25px;
  color: black;
  font-weight: bold;
  text-align: center;
  text-decoration: underline;
  margin-top: -3px;
}
.horizontal_line{
  width: 100%;
  height: 2px;
  background-color: black;
  border: none;
}
</style>