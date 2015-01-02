package SonoranCellular.servlets;
import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import SonoranCellular.servlets.*;
import SonoranCellular.utils.*;


public class AddPlan extends HttpServlet
{
  public AddPlan()
  {
    super();
  }

  private String plan_exist_err = "Plan name does not exist";
  private String imei_exist_err = "Phone imei does not exist";
  private String imei_format_err = "Please enter valid imei number";
  private String gen_format_err = "Please enter valid format in all fields";

  private OracleConnect oc = new OracleConnect();


  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String id_s, plan_name, imei_s, mobile_number, model, manufacturer, type,
           data_s;
    int imei, db_imei, id, data;
    Double price;
    PrintWriter out;

    res.setContentType("application/json");
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "POST");
    out = res.getWriter();

    id_s = req.getParameter( "account_id" );
    // this should work since we already error checked at login servlet
    id = Integer.parseInt( id_s );
    String ppm_s = req.getParameter( "price" );

    plan_name = req.getParameter( "plan_name" );
    type = req.getParameter( "type" );
    data_s = req.getParameter( "data" );
    imei_s = req.getParameter( "imei" );
    mobile_number = req.getParameter( "mobile_number" );
    manufacturer = req.getParameter( "manufacturer" );
    model = req.getParameter( "mobile_model" );

    try {

      data = Integer.parseInt( data_s );
      imei = Integer.parseInt( imei_s );
      price = Double.parseDouble( ppm_s );

      // check plan name exists
      // If plan does not exist, create it
      if( !oc.plan_exists( plan_name )) {
        System.out.println("Plan does not exist");
        oc.insert_plan( plan_name, price, data, type );
      }

      // check imei exists
      // If phone does not exist, create it then add the subscription.
      if( !oc.imei_exists( imei )) {
        System.out.println("Phone does not exist");
        oc.insert_phone( imei, mobile_number, manufacturer, model );
      }

      // add plan if both phone and plan exist
      if ( oc.imei_exists( imei ) && oc.plan_exists( plan_name ) ) {

        boolean ret = oc.insert_subscribe( imei, id, plan_name );
        System.out.println("both imei and plan exists");
        System.out.println("insert subscribe return: " + ret);

        if( ret )
          out.println( "{ \"status\": true }" );
        else
          out.println( "{ \"status\": false, \"err\": \"" +
             "error inserting into subscribes\" }" );
      }
    }

    // error message parsing imei
    catch( Exception e ) {
      out.println( "{ \"status\": false, \"err\": \"" +
         this.gen_format_err + " \" }" );
      e.printStackTrace();
    }

    out.close();
  }

}
