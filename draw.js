var server = "http://localhost:3868";
var url = "/SonoranCellular";
var name = null;
var id = null;


function ignore_enter() {
  return (event.keyCode!=13);
}


function draw_login() {

  var div = document.getElementById('content');
  div.innerHTML = "";

  var h3 = document.createElement('h3');
  var text = document.createTextNode('Sonoran Cellular');
  h3.appendChild( text );
  div.appendChild( h3 );

  var form = document.createElement('form');
  form.id = "login_form";
  form.name = "login_form";
  form.action = "";

  var fs = document.createElement('fieldset');
  var leg = document.createElement('legend');
  text = document.createTextNode('Login');
  leg.appendChild( text );
  fs.appendChild( leg );

  var p = document.createElement('p');
  var text = document.createTextNode('Account Id: ');
  p.appendChild(text);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "account_id";
  input.id = "account_id";
  input.onkeydown = ignore_enter;;
  p.appendChild(input);
  fs.appendChild(p);

  p = document.createElement('p');
  text = document.createTextNode('Account Name: ');
  p.appendChild(text);
  input = document.createElement('input');
  input.type = "text";
  input.name = "account_name";
  input.id = "account_name";
  input.onkeydown = ignore_enter;;
  p.appendChild(input);
  fs.appendChild(p);

  p = document.createElement('p');
  input = document.createElement('input');
  input.type = "button";
  input.value = "Submit";
  input.onclick = function() {
    var id_in = document.getElementById("account_id");
    var name_in = document.getElementById("account_name");
    window.location.replace( server + url + "?" + id_in.value 
        + "?" + name_in.value );
  };
  p.appendChild(input);
  fs.appendChild(p);
  form.appendChild(fs);
  div.appendChild(form);

  p = document.createElement('p');
  input = document.createElement('input');
  input.type = "button";
  input.value = "Add Account";
  input.onclick = draw_add_acct;
  p.appendChild(input);
  div.appendChild(p);
}


function draw_main_menu( id, name ) {

  this.id = id;
  this.name = name;

  console.log( this.id, this.name );

  var div = document.getElementById('content');
  div.innerHTML = "";

  var h3 = document.createElement('h3');
  var text = document.createTextNode('Sonoran Cellular');
  h3.appendChild( text );
  div.appendChild( h3 );

  var ul = document.createElement('ul');
  var li = document.createElement('li');
  var a = document.createElement('a');
  text = document.createTextNode('Add Plan')
  a.href="#";
  a.appendChild(text);
  a.onclick = draw_add_plan;
  li.appendChild(a);
  ul.appendChild(li);

  li = document.createElement('li');
  a = document.createElement('a');
  text = document.createTextNode('Find Bill');
  a.href="#";
  a.onclick = draw_find_bill;
  a.appendChild(text);
  li.appendChild(a);
  ul.appendChild(li);

  li = document.createElement('li');
  a = document.createElement('a');
  text = document.createTextNode('Shared Plans');
  a.href="./JSP/SharedAssignment.jsp?" + id + "?" + name;
  a.appendChild(text);
  a.onclick = function() { console.log('get shared plans'); };
  li.appendChild(a);
  ul.appendChild(li);

  li = document.createElement('li');
  a = document.createElement('a');
  text = document.createTextNode('Log Out');
  a.onclick = function() {
    console.log(' clicked log out');
    window.location.replace( server + url );
    //draw_login();
  };
  a.appendChild(text);
  li.appendChild(a);
  ul.appendChild(li);

  div.appendChild(ul);
}


function draw_add_acct() {

  div = document.getElementById('content');
  div.innerHTML = "";

  var h3 = document.createElement('h3');
  var text = document.createTextNode('Sonoran Cellular');
  h3.appendChild( text );
  div.appendChild( h3 );

  var form = document.createElement('form');
  form.id = "add_act_form";
  form.name = "add_act_form";
  form.action = "";

  var fs = document.createElement('fieldset');
  var leg = document.createElement('legend');
  text = document.createTextNode('Add Account');
  leg.appendChild( text );
  fs.appendChild( leg );

  var p = document.createElement('p');
  var text = document.createTextNode('Account Id: ');
  p.appendChild(text);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "account_id";
  input.id = "account_id";
  p.appendChild(input);
  fs.appendChild(p);

  p = document.createElement('p');
  text = document.createTextNode('Account Name: ');
  p.appendChild(text);
  input = document.createElement('input');
  input.type = "text";
  input.name = "account_name";
  input.id = "account_name";
  p.appendChild(input);
  fs.appendChild(p);

  p = document.createElement('p');
  input = document.createElement('input');
  input.type = "button";
  input.value = "Submit";
  input.onclick = function() {
    console.log('post add acct');
    do_ajax( form, "POST", "AddAccountInformation", "json", add_acct_callb );
  };
  p.appendChild(input);

  input = document.createElement('input');
  input.type = "button";
  input.value = "Cancel";

  input.onclick = function() {
    console.log('cancel add acct');
    draw_login();
  };

  p.appendChild(input);
  fs.appendChild(p);

  form.appendChild(fs);
  div.appendChild(form);
}


function draw_add_plan() {

  console.log( "add plan: " + id );

  var div = document.getElementById('content');
  div.innerHTML = "";

  var h3 = document.createElement('h3');
  var text = document.createTextNode('Sonoran Cellular');
  h3.appendChild( text );
  div.appendChild( h3 );

  var fs = document.createElement('fieldset');
  var leg = document.createElement('legend');
  text = document.createTextNode('Add Plan');
  leg.appendChild( text );
  fs.appendChild( leg );

  // add plan form
  var form = document.createElement('form');
  form.id = "add_plan_form";
  form.name = "add_plan_form";
  form.action = "";

  // label plan name 
  var p = document.createElement('p');
  var text = document.createTextNode('Plan Name: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input plan name
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "plan_name";
  input.id = "plan_name";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  // label price month
  var p = document.createElement('p');
  var text = document.createTextNode('Price per Month: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input price month
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "price";
  input.id = "price";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  // label data
  var p = document.createElement('p');
  var text = document.createTextNode('Allowed Data Usage (GB): ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input data
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "data";
  input.id = "data";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  // label type
  var p = document.createElement('p');
  var text = document.createTextNode('Plan Type: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input type
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "type";
  input.id = "type";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);


  // label imei 
  var p = document.createElement('p');
  var text = document.createTextNode('IMEI: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input plan name
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "imei";
  input.id = "imei";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  // label manu
  var p = document.createElement('p');
  var text = document.createTextNode('Manufacturer: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input manu
  var input = document.createElement('input');
  input.type = "text";
  input.name = "manufacturer";
  input.id = "manufacturer";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  var input = document.createElement('input');
  input.type = "hidden";
  input.name = "account_id";
  input.id = "account_id";
  input.value = id;
  input.onkeydown = ignore_enter;
  form.appendChild(input);

  // label mobile number 
  var p = document.createElement('p');
  var text = document.createTextNode('Mobile Number: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input mobile number
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "mobile_number";
  input.id = "mobile_number";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  // label mobile model
  var p = document.createElement('p');
  var text = document.createTextNode('Mobile Model: ');
  p.appendChild(text);
  fs.appendChild(p);

  // text input mobile model
  div.appendChild(form);
  var input = document.createElement('input');
  input.type = "text";
  input.name = "mobile_model";
  input.id = "mobile_model";
  input.onkeydown = ignore_enter;
  p.appendChild(input);
  fs.appendChild(p);

  // submit button
  p = document.createElement('p');
  input = document.createElement('input');
  input.type = "button";
  input.value = "Submit";
  input.onclick = function() {
    do_ajax( form, "POST", "AddPlan", "json", add_plan_callb );
  };
  p.appendChild(input);

  // cancel button
  input = document.createElement('input');
  input.type = "button";
  input.value = "Cancel";
  input.onclick = function() {
    console.log('cancel add acct');
    draw_main_menu( id, name );
  };
  p.appendChild(input);
  fs.appendChild(p);

  form.appendChild(fs);
  div.appendChild(form);
}


function draw_find_bill( ) {

  var div = document.getElementById('content');
  div.innerHTML = "";

  var h3 = document.createElement('h3');
  var text = document.createTextNode('Sonoran Cellular');
  h3.appendChild( text );
  div.appendChild( h3 );

  var form = document.createElement('form');
  form.id = "bill_form";
  form.name = "bill_form";
  form.action = "";

  var fs = document.createElement('fieldset');
  var leg = document.createElement('legend');
  text = document.createTextNode('Find Bill');
  leg.appendChild( text );
  fs.appendChild( leg );

  // bill period label
  var p = document.createElement('p');
  var text = document.createTextNode('Billing period (YYYY-MM-DD): ');
  p.appendChild(text);
  fs.appendChild(p);

  // bill period text input
  var input = document.createElement('input');
  input.type = "text";
  input.name = "bill_period";
  input.id = "bill_period";
  input.onkeydown = ignore_enter;

  p.appendChild(input);
  fs.appendChild(p);

  p = document.createElement('p');
  input = document.createElement('input');
  input.type = "button";
  input.value = "Submit";
  input.id = "find_bill_sub";

  input.onclick = function() {
    console.log( "clicked find bill sub" );
    do_ajax( form, "POST", "FindBill", "text", find_bill_callb );
  };
  p.appendChild(input);

  input = document.createElement('input');
  input.type = "button";
  input.value = "Cancel";

  input.onclick = function() {
    console.log('cancel add acct');
    if ( document.getElementById('bill_div'))  {
      document.getElementById("bill_div").innerHTML = "";
    }
    draw_main_menu(id, name);
  };
  p.appendChild(input);
  fs.appendChild(p);

  form.appendChild(fs);
  div.appendChild(form);
}
