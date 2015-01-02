package SonoranCellular.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import SonoranCellular.servlets.*;
import SonoranCellular.utils.*;
import java.sql.*;

public class FindBill extends HttpServlet
{

  public FindBill()
  {
    super();
  }

  private String no_bills = "There is no bill for the specified billing period.\nPlease enter another billing period and try again.";
  private String out_of_bounds_err = "Request could not be carried out.";
  private OracleConnect oc = new OracleConnect();

  public void doPost
    ( HttpServletRequest req, HttpServletResponse res ) 
    throws ServletException, IOException
  {
    PrintWriter out;
    String id_s, name;
    HttpSession sess;
    String period;

    System.out.println( "Find Bill" );

    res.setContentType("text/plain");
    res.setHeader("success", "yes");
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "POST");

    out = res.getWriter();
    period = req.getParameter( "bill_period" );

    try {
      period = period.substring(5,7) + "/" + period.substring(8,10) + "/" +
        period.substring(0,4);
       System.out.println(period);
    }
    catch( IndexOutOfBoundsException e) {
      e.printStackTrace();
      out.println( this.out_of_bounds_err );
      out.close();
      return;
    }

    if ( oc.date_OOB( period ) )
      out.println( this.out_of_bounds_err );

    else if ( !oc.bill_exists(period) )
      out.println( this.no_bills );
    
    else {
      try {
        ResultSet rs = oc.do_query( 
            "select * from Bill where DueDate=to_date('" + 
              period + "', 'MM/DD/YYYY')" );

        while ( rs.next() ) {
          out.printf( "%s %s %s %s?", rs.getString(1), 
              rs.getDate(2), rs.getDate(3), rs.getDate(4) );
          System.out.printf( "%s %s %s %s?", rs.getString(1), 
              rs.getDate(2), rs.getDate(3), rs.getDate(4) );
        }
      }
      catch( Exception e) {
        e.printStackTrace();
        out.println( "undefined error");
      }
    }
    System.out.println("closing out writer for find bill");
    out.close();
  }
}

