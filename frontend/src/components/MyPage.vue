<template>

<v-data-table
    :headers="headers"
    :items="myPage"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MyPage',
    props: {
      value: Object,
      editMode: Boolean,
      isNew: Boolean
    },
    data: () => ({
        headers: [
            { text: "id", value: "id" },
            { text: "orderId", value: "orderId" },
            { text: "customer", value: "customer" },
            { text: "deviceName", value: "deviceName" },
            { text: "quantity", value: "quantity" },
            { text: "paymentStatus", value: "paymentStatus" },
            { text: "subscriptionStatus", value: "subscriptionStatus" },
        ],
        myPage : [],
    }),
    async created() {
      var temp = await axios.get(axios.backend + '/mypages')

      this.myPage = temp.data._embedded.mypages;

    },
    methods: {
    }
  }
</script>

