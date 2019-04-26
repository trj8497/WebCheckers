<!-- Contributor: Yu Liang -->

<link rel="stylesheet" href="/css/theme.css">
 <div class="navigation">
  <#if currentUser??>
    <a href="/modes">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <a href="/login">log in</a>
  </#if>
   <input type="checkbox" id="switch" name="theme" /><label type="switch" for="switch"></label>
   <script>
           var checkbox = document.querySelector('input[name=theme]');
           if(typeof(Storage) !== "undefined"){
               if(sessionStorage.getItem('theme') === 'dark'){
                   document.documentElement.setAttribute('data-theme', 'dark');
                   document.getElementById("switch").checked = true;
               }
               else if(sessionStorage.getItem('theme') === 'light'){
                   document.documentElement.setAttribute('data-theme', 'light')
              }
           }
           checkbox.addEventListener('change', function() {
               if(this.checked) {
                   trans()
                   document.documentElement.setAttribute('data-theme', 'dark')
                   sessionStorage.setItem('theme', 'dark')
               } else {
                   trans()
                   document.documentElement.setAttribute('data-theme', 'light')
                   sessionStorage.setItem('theme', 'light')
               }
           })
           let trans = () => {
               document.documentElement.classList.add('transition');
               window.setTimeout(() => {
                   document.documentElement.classList.remove('transition')
               }, 1000)
           }
   </script>
 </div>