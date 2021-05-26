<template>
  <div class="container">
    <div class="row">
      <div class="col col-md-6 offset-md-3 text-center">
        <ion-icon class="bigicon" name="mail-outline"></ion-icon>
        <h2>Bald ist es soweit</h2>
        Du hast dich erfolgreich registriert.<br/>
        Bitte überprüfe dein E-Mail Postfach und bestätige deine Registrierung mit dem enthaltenen Link.
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AfterRegisterView"
}
</script>

<style scoped lang="scss">
.bigicon {
  display: inline-block;
  float: left;
  height: 100%;
  width: 100%;
  font-size: 10.5rem;
  color: #c9c9c9;
  padding-top: 10px;
}
</style>