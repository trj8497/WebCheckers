<!-- Contributors: Himani Munshi and Tejaswini Jagtap and Yu Liang -->

<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <link rel="stylesheet" href="/css/rule.css">
  <link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
  window.gameData = {
    "currentUser" : "${currentUser.name}",
    "viewMode" : "${viewMode}",
    "modeOptions" : ${modeOptionsAsJSON!'{}'},
    "redPlayer" : "${redPlayer.name}",
    "whitePlayer" : "${whitePlayer.name}",
    "activeColor" : "${activeColor}"
  };
  </script>
</head>

<body>
  <#if title != "Spectator Mode">
  <a class="scrBtn" href="#bottom" ><i class = "icon-chevron-down"></i></a>
  </#if>
  <div class="page">
    <h1>Web Checkers | ${title} <#include "rule.ftl"> </h1>

    <#include "nav-bar.ftl">

    <div class="body">

      <div id="help_text" class="INFO"></div>

      <div>
        <div id="game-controls">

          <fieldset id="game-info">
            <legend>Info</legend>

            <#if message??>
            <div id="message" class="${message.type}">${message.text}</div>
            <#else>
            <div id="message" class="INFO" style="display:none">
              <!-- keep here for client-side messages -->
            </div>
            </#if>

            <div>
              <table data-color='RED'>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td class="name">Red</td>
                </tr>
              </table>
              <table data-color='WHITE'>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td class="name">White</td>
                </tr>
              </table>
            </div>
          </fieldset>

          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div class="toolbar"></div>
          </fieldset>
        </div>

        <#if currentUser.getName() == redPlayer>
          <div class="game-board">
        <#else>
          <div class="game-white">
        </#if>
            <table id="game-board">
              <tbody>
                <#list board.iterator() as row>
                  <tr data-row="${row.getIndex()}">
                    <!-- Replaced row.index by the function implemented in Row class -->
                    <#list row.iterator() as space>
                      <td data-cell="${space.getCellIdx()}"
                        <#if space.isValid() >
                          class="Space"
                        </#if>
                      >
                        <!-- Replaced space.CellIdx by the function implemented in Space class -->
                        <#if space.piece??>
                          <#if currentUser.getName() == redPlayer>
                            <div class="Piece"
                              id="piece-${row.getIndex()}-${space.getCellIdx()}"
                              data-type="${space.piece.type}"
                              data-color="${space.piece.color}">
                          <!-- Made calls to the relevant functions from classes Row and Space -->
                          <#else>
                            <div class ="white-piece Piece"
                              id="piece-${row.getIndex()}-${space.getCellIdx()}"
                              data-type="${space.piece.type}"
                              data-color="${space.piece.color}">
                          </#if>
                            </div>
                        </#if>
                      </td>
                    </#list>
                  </tr>
                </#list>
              </tbody>
            </table>

          </div>

        </div>
      </div>
    </div>
  </div>
  <em id = "bottom"></em>
  <#if title != "Spectator Mode">
  <a class = "scrBtn top" href="#top"><i class = "icon-chevron-up"></i></a>
  </#if>
  <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>

  <script data-main="/js/game/index" src="/js/require.js"></script>
</body>
</html>
