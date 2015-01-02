var id = null;
var name = null;

window.onload = function() {

  var url = document.URL;
  var base = url.substring(0, url.indexOf('JSP'));
  console.log("base url: " + base);

  if( (url.indexOf("?") > -1 )) {
    tokens = url.split('?').slice(1);
    console.log(tokens);
    id = parseInt(tokens[0]);
    name = tokens[1];

    var mm_but = document.getElementById('main_menu_but');

    mm_but.href = "#";
    mm_but.onclick = function() {
      console.log('main menu button clicked'); 
      window.location.replace( base + '?' + id + '?' + name );
    };

  }
  else 
    window.location.replace( base );
};
