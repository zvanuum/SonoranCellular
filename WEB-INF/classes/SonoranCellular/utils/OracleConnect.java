package SonoranCellular.utils;
import java.io.*;
import java.util.*;
import java.sql.*;

public class OracleConnect
{
  public static final String conn_str =
    "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
  public static final String user = "criddlek"; // "zvanuum";
  public static final String pass = "foobar"; // "a2069";
  public Connection conn;

  public OracleConnect()
  {
    init_db();
  }

  // run a query, return the result
  public ResultSet do_query( String q )
  {
    Statement s;
    ResultSet rs;

    rs = null;
    try {
      s = this.conn.createStatement();
      if( s == null ) throw new Exception("createStatement failed");
      rs = s.executeQuery(q);
    }
    catch (Exception e) { e.printStackTrace(); }

    return rs;
  }


  // run an update, return rows or -1 for fail
  public int do_execute_update( String str )
  {
    Statement s;
    ResultSet rs;

    try {
      s = this.conn.createStatement();
      if( s == null ) throw new Exception("createStatement failed");
      else
        return s.executeUpdate( str );
    }
    catch (Exception e) { e.printStackTrace(); }

    return -1;
  }


  // return a list of all the available plans
  public ArrayList<String> query_plans()
  {
    ArrayList<String> plans;
    ResultSet rs;

    plans = new ArrayList<String>();

    rs = do_query("select PlanName from Plan");

    try {
      while( rs.next() )
        plans.add( rs.getString(1) );
    }
    catch (Exception e) { e.printStackTrace(); }

    return plans;
  }


  // return the phone number associated with an imei
  public String query_ph( int imei )
  {
    String ph;
    ResultSet rs;

    ph = "";

    try {
      rs = do_query("select MobileNumber from Phone where IMEI=" + imei);
      if( rs.next() )
        ph = rs.getString(1);
    }
    catch (Exception e) { e.printStackTrace(); }

    return ph;
  }


  // return a list of phone numbers in database
  public ArrayList<String> query_ph_list()
  {
    ArrayList<String> ph;
    ResultSet rs;

    ph = new ArrayList<String>();

    try {
      rs = do_query("select MobileNumber from Phone");
      while( rs.next() )
        ph.add( rs.getString(1));
    }
    catch (Exception e) { e.printStackTrace(); }

    return ph;
  }


  // return the account name of account id
  public String query_name( int id  )
  {
    String name;
    ResultSet rs;

    name = "";
    try {
      rs = do_query("select Name from Account where AccountNumber=" + id);

      if( rs.next() )
        name = rs.getString(1);
    }
    catch (Exception e) { e.printStackTrace(); }

    return name;
  }


  // return true/false if account exists
  public boolean acct_exists( int id )
  {
    ResultSet rs;

    try {
      rs = do_query("select Name from Account where AccountNumber=" + id );
      return rs.next();
    }
    catch (Exception e) { e.printStackTrace(); }
    return true;
  }


  public boolean correct_acct_name( int id, String name )
  {
    ResultSet rs;
    String r_name;

    try {
      rs = do_query("select Name from Account where AccountNumber=" + id );
      if( rs.next() ) {
        r_name = rs.getString(1);
        return r_name.equals( name );
      }
    }
    catch (Exception e) { e.printStackTrace(); }
    return false;
  }


 // return true/false if account exists
  public boolean plan_exists( String plan  )
  {
    ResultSet rs;

    try {
      rs = do_query("select Type from Plan where PlanName='" + plan +"'" );
      return rs.next();
    }

    catch (Exception e) { e.printStackTrace();  }
    return false;
  }


  // return true/false if account exists
  public boolean imei_exists( int imei  )
  {
    ResultSet rs;

    try {

      rs = do_query("select Model from Phone where IMEI=" + imei );
      return rs.next();
    }

    catch (Exception e) { e.printStackTrace();  }
    return false;
  }




  // return imei  if phone number exists, -1 if not
  public int ph_exists( String ph )
  {
    ResultSet rs;

    try {
      rs = do_query("select IMEI from Phone where MobileNumber='"+ph+"'");

      if( rs.next() )
        return rs.getInt(1);
    }
    catch (Exception e) { e.printStackTrace(); }

    return -1;
  }

  // return true if specified duedate exists in Bill table
  public Boolean bill_exists ( String duedate )
  {
    ResultSet rs;

    try {
      rs = do_query( "select DueDate from Bill where DueDate=to_date('" + duedate + "', 'MM/DD/YYYY')" );
      return rs.next();
    }
    catch ( Exception e ) { e.printStackTrace(); }

    return false;
  }

  // return false if specified date actually exists
  // return true if date is out of bounds or is not a date
  public Boolean date_OOB ( String billperiod )
  {
    String month_st = billperiod.substring(0,1);
    String day_st = billperiod.substring(3,4);

    try {
      int month = Integer.parseInt(month_st);
      int day = Integer.parseInt(day_st);

      if ( month < 1 || month > 12 )
        return true;
      else {
        switch ( month ) {
          case 1:
          case 3:
          case 5:
          case 7:
          case 8:
          case 10:
          case 12:
            if ( day > 31 || day < 0 )
              return true;
            break;
          case 4:
          case 6:
          case 9:
          case 11:
            if ( day > 30 || day < 0 )
              return true;
            break;
          case 2:
            if ( day > 28 || day < 0 )
              return true;
            break;
        }
      }
    }
    catch ( Exception e ) { return true; }

    return false;
  }

  // insert new account into account table
  public void insert_acct( int id, String name  )
  {
    if( acct_exists( id ) )
      System.out.println("\nAccount owner already exists!\n");

    else if( do_execute_update(
          "insert into Account values("+id+",'"+name+"')") == 1 )
        System.out.println("\nAccount owner added!\n");
  }


  // insert new subscription into subscribe table
  public boolean insert_subscribe( int imei, int id, String plan )
  {
    int ret;

    ret = do_execute_update("insert into Subscribe " +
            "values("+imei+","+id+",'"+plan+"')");
    return  (ret == 1);
  }

  // insert new plan into plan table
  public boolean insert_plan( String planName, Double ppm, int dataUsage, String type )
  {
    int ret;

    ret = do_execute_update("insert into Plan " +
            "values('" + planName + "'," + ppm + "," + dataUsage + ",'" + type + "')" );

    return ( ret == 1 );
  }

  // insert new phone into phone table
  public boolean insert_phone( int imei, String ph, String manufacturer, String model )
  {
    int ret;

    ret = do_execute_update("insert into Phone values(" +
            imei + ",'" + ph + "','" + manufacturer + "','" + model + "')");

    return ( ret == 1);
  }

  // return a hashmap of user -> [ { mobile_number, plan name } ... ]
  public Map<String, List<String[]>> build_subscribe_map()
  {
    Map<String, List<String[]>> map, sorted;
    List<String[]> l;
    String name, ph, plan;
    ResultSet rs;

    map = new HashMap<String, List<String[]>>();

    try {
      rs = do_query("select * from Subscribe");

      while( rs.next() ) {
        name =  query_name( rs.getInt(2) );
        ph = query_ph( rs.getInt(1) );
        plan = rs.getString(3);
        String[] str = { ph, plan };

        if( map.containsKey( name )) {
          l = map.get( name );
          l.add( str );
        }
        else {
          l = new ArrayList<String[]>();
          l.add( str );
          map.put( name, l );
        }
      }
    }
    catch (Exception e) { e.printStackTrace(); }

    // sort the plan/number alphabetically on plan name
    for( String k : map.keySet() ) {
      l = map.get( k );
      Collections.sort(l, new Comparator<String[]>() {
        @Override
        public int compare( String[] one, String[] two ) {
          return one[1].compareTo(two[1]);
        }
      });
    }

    // sort on keys
    sorted = new TreeMap<String, List<String[]>>(map);

    return sorted;
  }

  // return a hashmap of plan name -> [ account name1, account name2, ... ]
  public Map<String, List<String>> plans_and_subscribers()
  {
    Map<String, List<String>> map, sorted;
    List<String> list;
    String plan;
    ResultSet rs;

    map = new HashMap<String, List<String>>();

    try {
      rs = do_query( "select * from Plan" );

      // Put each plan name into the map
      while( rs.next() ) {
        plan = rs.getString(1);
        list = new ArrayList<String>();
        map.put( plan, list );
      }

      // For each plan name, find the associated accounts and hash the account names to that plan name
      for ( String key : map.keySet() ) {
        ResultSet rs2 = do_query( "select acc.Name from Subscribe s, Account acc where s.PlanName='" + key + "' and s.AccountNumber=acc.AccountNumber" );

        // Find all AccountNames associated with a PlanName (the hash key) and chain the keys to that bucket
        while ( rs2.next() ) {
          if ( !map.get( key ).contains( rs2.getString(1) ) )
            map.get( key ).add( rs2.getString(1) );
        }

        // Sort the list to hold names in alphabetical order
        Collections.sort( map.get( key ) );

      }
    }
    catch (Exception e) { e.printStackTrace(); }

    // Sort map by key
    sorted = new TreeMap<String, List<String>>(map);

    return sorted;
  }


  // return list of the dependent accounts for a master account
  public List<String[]> build_owns_list( int master )
  {
    List<String[]> list;
    String key, name;
    int id;
    ResultSet rs;

    list = new ArrayList<String[]>();

    try {
      rs = do_query("select DependentAccountNumber from Owns " +
          "where MasterAccountNumber='" + master +"'");

      key = query_name( master );

      while( rs.next() ) {
        id = rs.getInt(1);
        name = query_name( id );
        String[] str = { Integer.toString(id), name };
        list.add( str );
      }
    }
    catch (Exception e) { e.printStackTrace(); }

    return list;
  }


  // menu item 2 was selected, show the phone numbers
  public void numbers()
  {
    ArrayList<String> ph;
    int i, n;

    ph = query_ph_list();

    // sort numerically
    Collections.sort( ph );
    System.out.println("\nHere is the list of all mobile numbers.\n");

    if( ph.size() == 0 )
      System.out.println("None!");
    else
      for( i=0 ; i<ph.size() ; i++ )
        System.out.println(ph.get(i));

    System.out.println();
  }


  // menu item 3 was selected
  public void subscribe( int id, String ph, String plan  )
  {
    int imei;
    ArrayList<String> plans;

    // halt if account doesnt exist
    if( !acct_exists(id) ) {
      System.out.println("\nAccount owner does not exist!\n");
      return;
    }

    // halt if phone doesnt exist
    if( ( imei = ph_exists(ph) ) == -1) {
      System.out.println("\nPhone number does not exist!\n");
      return;
    }

    plans = query_plans();

    // otherwise try to make a new subscription. sql not allow the duplicates
    if( insert_subscribe( imei, id, plan ) ) {
      System.out.println("\n\n"+id+" successfully subscribed to the "+
            "plan \""+ plan + "\"\n");
    }
  }

  // print out html to form a table of each subscriber's name related to a plan
  /*public void write_table()
  {
    PrintWriter out = new PrintWriter();
    Map<String, List<String>> plans = plans_and_subscribers();

    for ( String key : plans.keySet() ) {
      out.println( "<tr>" );
      out.println( "<td> " + key + " </td>" );
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
  } */


  // read sql file line by line, executing each statement as
  // terminated by a semicolon, ignoring comments
  /*public void run_sql_file( String name  )
  {
    BufferedReader br;
    String cmd, line;

    cmd = "";
    try {
      br = new BufferedReader( new FileReader( new File( name )));

      while( (line = br.readLine()) != null ) {

        // ignore comments
        if( line.contains("--") )
            continue;

        // remove newlines
        cmd += line.trim();

        // end of sql statement
        if( cmd.indexOf( ";" ) != -1) {
          cmd = cmd.substring(0, cmd.indexOf(";"));
          if( (do_execute_update( cmd ) == -1 ))
              System.exit(1);
          cmd = "";
        }
      }
    }
    catch (Exception e) { e.printStackTrace(); }
  } */


  // drop, create/populate the tables
  /*public void create_pop_tables()
  {
    run_sql_file( drop_file );
    run_sql_file( create_file );
  } */


  // initialize the database hooks
  private void init_db( )
  {
    try {
      Class.forName("oracle.jdbc.OracleDriver");
      conn = DriverManager.getConnection( this.conn_str, this.user, this.pass );

      if( this.conn == null) throw new Exception("getConnection failed");
      this.conn.setAutoCommit(true);
    }
    catch (Exception e) {
     e.printStackTrace();
     System.exit(1);
    }
  }


}
