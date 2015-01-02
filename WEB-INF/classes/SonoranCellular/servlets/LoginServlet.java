package SonoranCellular.servlets;
import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import SonoranCellular.servlets.*;
import SonoranCellular.utils.*;
import java.sql.*;


public class LoginServlet extends HttpServlet
{
  public LoginServlet()
  {
    super();
  }

  private String acct_exist_err = "Account number does not exist";
  private String acct_name_err = "Please enter the correct account name";
  private String invalid_id_err = "Please enter valid account id";

  private OracleConnect oc = new OracleConnect();


  // submiting login info
  public void doPost
    ( HttpServletRequest req, HttpServletResponse res ) 
    throws ServletException, IOException
  {
    PrintWriter out;
    String id_s, name;
    HttpSession sess;
    int id;

    System.out.println( "entered login servlet" );

    res.setContentType("application/json");
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "POST");
    out = res.getWriter();


    id_s = req.getParameter( "account_id" );
    name = req.getParameter( "account_name" );

    System.out.println( "account id: " + id_s );

    try {
      id = Integer.parseInt( id_s );

      if( !oc.acct_exists( id ) ) 
        out.println( "{ \"status\": false, \"err\": \"" + 
            this.acct_exist_err +"\" }" );

      else if( !oc.correct_acct_name( id, name ))
        out.println( "{ \"status\": false, \"err\": \"" + 
            this.acct_name_err +"\" }" );

      else {
        out.println( "{ \"status\": true }" );
      }
    }
    catch( Exception e ) {  
      out.println( "{ \"status\": false, \"err\": \"" + 
         this.invalid_id_err + "\" }" );
      e.printStackTrace(); 
    }
    out.close();
  }


}
