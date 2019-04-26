<!-- Author: Yu Liang -->

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/welcome.css">
        <title>Welcome!</title>
        <#include "home.ftl">
    </head>
    <body >
        <div class="welcome-section content-hidden" >
            <div class="content-wrap">
                <ul class="fly-in-text">
                    <li>Welcome</li>
                    <li>To</li>
                    <li>WebCheckers</li>
                </ul>
                <br>
                <br>
                <a href="#" class="enter-button">Enter to play</a>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script type="text/javascript">

            $(function() {
                var welcomeSection = $('.welcome-section'),
                    enterButton = welcomeSection.find('.enter-button');
                setTimeout(function() {
                    welcomeSection.removeClass('content-hidden');
                }, 500);
                enterButton.on('click', function(e) {
                    e.preventDefault();
                    welcomeSection.addClass('content-hidden').fadeOut();
                });
            })();

        </script>

    </body>
</html>
