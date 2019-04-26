<!-- Contributors: Yu Liang -->

<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Web Checkers | ${title} </title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
  <link rel="stylesheet" href="/css/rule.css">

</head>
<body>

<div class="page">
  <h1>Web Checkers | ${title}  <#include "rule.ftl"> </h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl">

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl">

    <#if newSession>
        <p>${numPlayers}</p>
    <#else>
       <script type="text/javascript">
          var timer = setTimeout(function(){
          window.location = window.location},10000);
       </script>
       <ol>
         <h2>Players Online</h2>
         <p>${numMessage}</p>
           <#list names>
             <#items as user>
               <#if user.name != currentUser.getName()>
                 <p class="list">${user.name}
                 <#if mode == "player">
                   <#if user.isPlayerFree()>
                     <form style="text-align:center;margin-top:-25px;" action="./startgame" method="POST">
                       <button type="list" name="username" value="${user.name}"">
                         Start Game
                       </button>
                     </form>
                     <br>
                   <#else>
                     <button type="disable">
                       Currently in game.
                     </button>
                   </#if>
                 </#if>
                 <#if mode == "spectator">
                   <#if !user.isPlayerFree()>
                     <form style="text-align:center;margin-top:-25px;" action="./spectator/game" method="GET">
                       <button type="list" name="username" value="${user.name}">
                         Spectate Game
                       </button>
                     </form>
                   <#else>
                     <button type="disable">
                       Not in game yet.
                     </button>
                   </#if>
                 </#if>
                 </p>
               </#if>
             </#items>
           </#list>
       </ol>
    </#if>

  </div>

</div>
</body>

</html>
