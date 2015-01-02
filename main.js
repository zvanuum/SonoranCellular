var server = "http://localhost:3868";
var url = "/SonoranCellular";
var id = null;
var name = null;


// called on login/add_acct success
function login_callb( res ) {

  console.log(res);
  if( res['status'] ) {
    console.log( "id: " + this.id );
    console.log( "name: " + this.name );
    draw_main_menu( this.id, this.name );
  }
  else {
    window.alert( res['err'] );
    this.id = null;
    this.name = null;
    window.location.replace( server + url );
  }
}


// called on login/add_acct success
function add_acct_callb( res ) {

  var i = document.getElementById('account_id');
  var n = document.getElementById('account_name');

  console.log(res);
  if( res['status'] ) {
    console.log( "id: " + this.id );
    console.log( "name: " + this.name );
    this.id = i.value;
    this.name = n.value;
    //set_id( this.id );
    //set_name( this.name );
    window.alert("Account Added!");
    draw_main_menu( this.id, this.name );
  }
  else {
    window.alert( res['err'] );
    this.id = null;
    this.name = null;
  }
}


// called on find bill success
function find_bill_callb( res ) {

  console.log("find bill callb");

  var lines = res.split('?');
  console.log(lines);

  if( isNaN(parseInt( lines[0][0] ))) {
    window.alert( res );
    return;
  }

  var body = document.body;
  var period_input = document.getElementById('bill_period');
  var per = document.getElementById('bill_period').value;
  period_input.value = "";

  var bill_div = document.getElementById('bill_div');

  if ( !document.getElementById('bill_div'))  {
    bill_div = document.createElement('div');
    bill_div.id = "bill_div";
    body.appendChild(bill_div);
  }
  else {
    bill_div.innerHTML = "";
  }

  bill_div.className = " center";

  var tb = document.createElement('table');
  tb.border = 1;
  var tbody = document.createElement('tbody');
  tb.appendChild(tbody);

  var th = document.createElement('th');
  var text = document.createTextNode('Account Number');
  th.appendChild(text);
  tbody.appendChild(th);

  var th = document.createElement('th');
  text = document.createTextNode('End Date');
  th.appendChild(text);
  tbody.appendChild(th);

  var th = document.createElement('th');
  text = document.createTextNode('Start Date');
  th.appendChild(text);
  tbody.appendChild(th);

  var th = document.createElement('th');
  text = document.createTextNode('Due Date');
  th.appendChild(text);
  tbody.appendChild(th);

  var tr = null; 
  var td = null; 

  for( var j=0 ; j< lines.length-1  ; j++ ) {
    tr = document.createElement('tr');
    var tokens = lines[j].split(' ');
    console.log( tokens )
    for( var i=0 ; i<tokens.length ; i++ )  {
      td = document.createElement('td');
      text = document.createTextNode( tokens[i] )
      td.appendChild(text);
      tr.appendChild(td);
    }
    tb.appendChild(tr);
  }
  bill_div.appendChild(tb);
}


function add_plan_callb( res ) {

  console.log("add plan callb");

  if( res['status'] ) {
    window.alert("Added Plan");
  }
  else {
    window.alert( res['err'] );
  }
}


// print ajax errors to console
function print_errors( req, stat, err  ) {

  console.error( "ajax failed:" );
  console.error( req );
  console.error( stat );
  console.error( err );
}


// generic ajax call
function do_ajax( form, pg, servlet, dt, callback ) {

  console.log("do ajax: " + form.id + " " + pg + " " + servlet + 
      " " + dt + " " + callback  );
  var u = server+url+"/" + servlet;
  console.log( "url: " + u );

  $.ajax({
    type: pg,
    cache: false,
    url: u, 

    data: $(form).serialize(),
    dataType: dt,

    success: function(data) { 
      console.log( data );
      callback( data ); 
    },
    error: function( req, stat, err ) { print_errors( req, stat, err ); }
  });
}


function pseudo_login() {

  var form = document.createElement('form');
  form.id = "login_form";
  form.name = "login_form";
  form.action = "";

  var input = document.createElement('input');
  input.type = "text";
  input.name = "account_id";
  input.id = "account_id";
  input.value = this.id;
  form.appendChild( input );

  input = document.createElement('input');
  input.type = "text";
  input.name = "account_name";
  input.id = "account_name";
  input.value = this.name;
  form.appendChild( input );

  input = document.createElement('input');
  input.type = "button";
  input.onclick = function() {
  };
  form.appendChild( input );

  do_ajax( form, "POST", "LoginServlet", "json", login_callb );
}

// render login prompt when page loads
window.onload = function() {

  console.log( "index.html loaded" );
  
  var url = document.URL;
  //if( url.indexOf("#") > -1)
  //  url = url.substring(0, url.length-1);

  if( url.indexOf("?") > -1 ) {
    var tokens = url.split('?').slice(1);
    console.log( tokens )
    this.id = parseInt(tokens[0]);
    this.name = tokens[1];
    console.log("id: " + this.id);
    console.log("name: " + this.name);
    pseudo_login();
    //set_id( this.id );
    //set_name( this.name );
  }
  else 
    draw_login();
}
