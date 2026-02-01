<!--
  page: /login
-->
<script setup lang="ts">
import {onMounted} from "vue";
import auth from "../../services/oauth-2-client/user-manager.js";

onMounted(async () => {
  const userManager = auth.getCurrentUserManager()
  await userManager.signinRedirect()
})

</script>