<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
  <script type="text/javascript">
      var timer = setTimeout(function(){
        window.location = window.location},10000);
  </script>
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl">

  <#include "message.ftl">

  <div class="body">

    <form name = "input" method = "post">
        User name: <br>
        <input type="text" name="username" id="username"/>
        <br/>
    <button type="submit">Login</button>
    </form>

   </div>

</div>
</body>

</html>