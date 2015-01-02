<%@ page language="java" contentType="text/html" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="SonoranCellular.utils.*" %>
<html>
  <head>
    <meta http-equiv=Content-Type content="text/html" />
    <title>Sonoran Cellular</title>

    <link rel="stylesheet" type="text/css" href="../style.css">

    <script type="text/javascript" src="../jquery.min.js"></script>
    <script type="text/javascript" src="../shared_plan.js"></script>

  </head>

  <body link=#f0f0ff alink vlink=#f0f0ff>
    <h3>SonoranCellular</h3>

    <div id="content" >
      <p class="center" >Accounts with the same plan:</p>
  <%
  OracleConnect oc = new OracleConnect();
  %>

  <table border="1" class="center" >
  <tr>
  <td> <b> Plan Name </b> </td>
  <td> <b> Account Name </b> </td>

  <%

  Map<String, List<String>> plans = oc.plans_and_subscribers();

  for ( String key : plans.keySet() ) {
    out.println( "<tr>" );
    out.println( "<td> " + key + " </td>" );

    if ( plans.get( key ).size() == 0 )
      out.println( "<td> n/a </td>" );
    else
      out.println( "<td> " + plans.get( key ).get( 0 ) + " </td>" );

    for ( int i = 1; i < plans.get( key ).size(); i++ ) {
      out.println( "<tr>" );
      out.println( "<td> </td>");
      
      if ( plans.get( key ).size() == 0 )
        out.println( "<td> n/a </td>" );
      else
        out.println( "<td> " + plans.get( key ).get(i) + " </td>" );
    }
  }

  %>

  </table>

    <div class="center">
      <p>
        <input type=button name="main_menu_but" id="main_menu_but" 
          value="Main Menu" />
      </p>
    </div>

    </div>
  </body>
</html>
