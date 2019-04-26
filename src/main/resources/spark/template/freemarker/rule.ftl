<!-- Author: Yu Liang -->

<!DOCTYPE html>
<html>
<body>

<#if title != "Spectator Mode">
<a class="rule" href="#ruleText">Rules</a>
</#if>

<div id="ruleText" class = "back" style = "z-index:1;">
  <div class = "box">
    <h3> Web Checkers Rules!</h3>
    <#if title != "Choose a Mode to play">
    <a class = "close" href="/">&times;</a>
    <#else>
    <a class = "close" href="/modes">&times;</a>
    </#if>
    <div class = "file">
      <#if title != "Welcome!" && title != "Welcome ${currentUser.getName()}">
        <p>
          <#if currentUser.getName() == redPlayer>
            Hi ${currentUser.getName()}! You are the Red Player.
            <td><img src="../img/single-piece-red.svg" /></td>
          </#if>
          <#if currentUser.getName() == whitePlayer>
            Hi ${currentUser.getName()}! You are the White Player.
            <td><img src="../img/single-piece-white.svg" /></td>
          </#if>
        </p>
      </#if>
      <p class="main"> Welcome to the Web Checkers World!!.</p>
      <p class="main"> This game is a TWO Player game.</p>
      <#if title == "Welcome!">
        <p class="main">
          Click the Log-in button on the top-left corner, <br>
          enter the username and press "Login" to get to the Modes and choose a mode to play.<br>
          You can see the number of players currently in the Lobby on this page.<br>
        </p>
      </#if>

      <#if title != "Welcome!" && title == "Welcome ${currentUser.getName()}">
        <p class="main">
          You can click "my home" on the top left corner to go back and choose another mode.<br>
          Now, if you are not the only player, you can see a list of players' name with "Start game"/"Spectate Game" button under them.<br>
          If there button is gray it means they are already in the game, please select the one not in the game to start a game.<br>
          If you are in the spectator mode, then it means other players are not in the game.<br>
          For Player mode, if you are the one who select the player, you will become the Red Player.<br>
          If you are the one who selected to play, you will become the White Player.<br>
          You will be direct to the game after been selected.<br>
        </p>
      </#if>
      <#if title == "Choose a Mode to play">
        <p class="main">
          You can now choose a mode to play.<br>
          Click on Modes button and you will see three modes.<br>
          The Player mode allows you to play with a real player.<br>
          The AI mode allows you to play on your own with "Siri".<br>
          The Spactator mode allows you to spectate other player's game.<br>
          However, you are not allowed to modify the game as a spectator.<br>
        </p>
      </#if>

      <#if title == "Game">
        <p class="main">
           You are now in the game.<br>
           You can drag your pieces and play the game.<br>
           There is RESIGN button in the Controls block.<br>
           You can resign if you don't want to play with this opponent, you can only do this at your start state.<br>
           You also have BACKUP button for you to "regret", press it when you want to go back to previous turn.<br>
        </p>
      </#if>
      <p class="paraStyle">
        1.You will get a 8x8 grid of light and dark squares board in checkerboard pattern after start a game.<br>
        <td><img src="../img/chess_board.svg" height="150" width="150" align="middle"/></td><br>
        You will have a dark square on the far left and a light square on his far right.<br>
        The checkers/pieces you will use is round and red or white in color.<br>
        For <font color="red">RED:</font> <td><img src="../img/single-piece-red.svg" /></td><br>
        For <font color="white">WHITE:</font> <td><img src="../img/single-piece-white.svg" /></td>
      </p>

      <p class="paraStyle">
        2.The pieces shall be placed on the dark squares.<br>
        At the starting position you will have twelve same color pieces,
        on the twelve dark squares closest to your edge of the board.<br>
      </p>

      <p class="paraStyle">
        3.The red player moves first.<br>
      </p>

      <p class="paraStyle">
        4.You must move each turn.<br>
        If you cannot move, you lose the game.<br>
      </p>

      <p class="paraStyle">
        5.In each turn, You can make a simple move, a single jump,
         or a multiple jump move.<br>
      </p>
      <p class="main">- Simple move:<br></p>
      <p style="text-align:center; line-height:1.7;">
        Single pieces can move one adjacent square diagonally forward away from the player.<br>
        A piece can only move to a vacant dark square.<br>
      </p>
      <p class="main">-Single jump move:<br></p>
      <p style="text-align:center; line-height:1.7;">
        A player captures an opponent's piece by jumping over it, diagonally,
        to an adjacent vacant dark square.<br>
        The opponent's captured piece is removed from the board.<br>
        You can never jump over, even without capturing, one of the your own pieces.<br>
        You cannot jump the same piece twice.<br>
      </p>
      <p class="main">-Multiple jump move: <br></p>
      <p style="text-align:center; line-height:1.7;">
        Within one turn, you can make a multiple jump move with the same piece by jumping from vacant dark square to vacant dark square.<br>
        You must capture one of the opponent's pieces with each jump.<br>
        You can capture several pieces with a move of several jumps.<br>
      </p>

      <p class="paraStyle">
        6.If a jump move is possible, you must make that jump move.<br>
        A multiple jump move must be completed. You cannot stop part way through a multiple jump.<br>
        If you has a choice of jumps, you can choose among them,
        regardless of whether some of them are multiple, or not.<br>
      </p>

      <p class="paraStyle">
        7.When a single piece reaches the row of the board furthest from you, i.e the king-row,
        by reason of a simple move, or as the completion of a jump, it becomes a king.<br>
        For <font color="red">RED:</font> <td><img src="../img/king-piece-red.svg" /></td>
        For <font color="white">WHITE:</font> <td><img src="../img/king-piece-white.svg" /></td>
        This ends your turn.<br>
        Your opponent crowns the piece by placing a second piece on top of it.<br>
      </p>

      <p class="paraStyle">
        8.A king follows the same move rules as a single piece,
        except that a king can move and jump diagonally forward away from you or diagonally backward toward you.<br>
        Within one multiple jump move, the jumps can be any combination of forward or backward jumps.<br>
        At any point, if multiple jumps are available to a king, you can choose among them.<br>
      </p>

      <p class="paraStyle">
        9.If you lose all of your pieces to captures, you lose the game.<br>
      </p>
    </div>
  </div>
</div>

</body>
</html>
