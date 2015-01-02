package SonoranCellular.servlets;
import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import SonoranCellular.utils.*;
import SonoranCellular.servlets.*;


public class AddAccountInformation extends HttpServlet
{
  public AddAccountInformation()
  {
    super();
  }

  private String acct_exist_err = "Account number already exists!";
  private String acct_succ = "Account owner added!";
  private String bad_id_err = "Account Number should only contain integers";
  private String long_name_err = 
    "Account Name must be less than forty characters.";

  private OracleConnect oc = new OracleConnect();


  // submiting add account info
  public void doPost
    ( HttpServletRequest req, HttpServletResponse res ) 
    throws ServletException, IOException
  {
    PrintWriter out;
    String id_s, name;
    int id;

    res.setContentType("application/json");
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "POST");
    out = res.getWriter();

    id_s = req.getParameter( "account_id" );
    name = req.getParameter( "account_name" );

    try {
      id = Integer.parseInt( id_s );

      if( oc.acct_exists( id ) )
        out.println( "{ \"status\": false, \"err\": \"" + 
            this.acct_exist_err +"\" }" );

      else if ( name.length() > 40 )
        out.println( "{ \"status\": false, \"err\": \"" + 

            this.long_name_err +"\" }" );
      else {
        oc.insert_acct( id, name );
        out.println( "{ \"status\": true }" );
      }
    }
    catch( Exception e ) {
      e.printStackTrace();
      out.println( "{ \"status\": false, \"err\": \"" + 
          this.bad_id_err +"\" }" );
    }

    out.close();
  }

}
