<!-- Author: Yu Liang -->

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <link rel="stylesheet" href="/css/modes.css">
  <link rel="stylesheet" type="text/css" href="/css/style.css">
  <link rel="stylesheet" href="/css/rule.css">
</head>

<body>
<div class="page large">

  <h1>Web Checkers | ${title}  <#include "rule.ftl"></h1>

  <#include "nav-bar.ftl">
  <input class="hidden-trigger" id="toogle" type="checkbox"></input>
  <label class="circle" for="toogle" ><br>Modes</label>

  <div class="subs" >
  <form action="./modes" method="POST">
    <button class="sub-circle">
      <input class="hidden-sub-trigger" id="player" type="radio" name="sub-circle" value="player" ></input>
      <label class="mid" for="player" >Player</label>
    </button>
     <button class="sub-circle" formaction="/startaigame">
      <input class="hidden-sub-trigger" id="ai" type="radio" name="sub-circle"></input>
      <label for="ai" >AI</label>
    </button>
    <button class="sub-circle">
      <input class="hidden-sub-trigger" id="spectator" type="radio" name="sub-circle" value="spec"></input>
      <label class="large" for="spectator">Spectator</label>
    </button>
  </form>
 </div>
</div>

</body>
</html>

