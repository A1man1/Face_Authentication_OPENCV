
package face;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class sqlconnection extends Front
{
  
    //constructor
    public sqlconnection(){}
    //constructor variable using to store data
    private String name;
    private String userName;
    private String pass;
    private String contact;
    
    //signup constructor
    public sqlconnection(String name,String userName,String pass,String contact)
    {
     this.name=name;
     this.userName=userName;
     this.pass=pass;
     this.contact=contact;
    }
     
    //login
     private String  usr,pass1 ;
    public sqlconnection(String usr,String pass1)
    {
     this.usr=usr;
     this.pass1=pass1;
    }
    
    public Connection ct;
    public Statement st;
    public ResultSet rt;
    
    private String driver="com.mysql.jdbc";
    private String root="jdbc:mysql://localhost/register";
    private String user="root";
    private String passwd="";
   
    public void work()
    {
    try
    {
      // create a mysql database connection
      String myDriver = "org.gjt.mm.mysql.Driver";
      String myUrl = "jdbc:mysql://localhost/register";
      Class.forName(myDriver);
       ct = (Connection) DriverManager.getConnection(myUrl, "root", "");
    
    
          // create a sql date object so we can use it in our INSERT statement
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
       
      // the mysql insert statement
      String query1= " insert into signup (Name,UserName,password,contact) values (?, ?, ?, ?)";

      // create the mysql insert preparedstatement
      PreparedStatement pt =  (PreparedStatement) ct.prepareStatement(query1);
      pt.setString (1, name);
      pt.setString (2, userName);
      pt.setString (3, pass);
      pt.setString  (4, contact);
        // execute the preparedstatement
      pt.execute();
         String alert1="signup sucessfully";
         JOptionPane.showMessageDialog(null,alert1,"signup Acknowlegdement" ,JOptionPane.INFORMATION_MESSAGE);
      
      ct.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
      if(e.getMessage().equals("Duplicate entry '"+userName+"' for key 'PRIMARY'"))
      {
         String alert1="user already exist";
                JOptionPane.showMessageDialog(null,alert1,"signup Error" ,JOptionPane.INFORMATION_MESSAGE);
      } 
    else if(e.getMessage().equals("Duplicate entry '"+contact+"' for key 'contact'"))
      {
       String alert2="please give another number";
        JOptionPane.showMessageDialog(null,alert2,"signup Error" ,JOptionPane.INFORMATION_MESSAGE);
      } 
    }
    }
   
   public void update(String password ,String username)
   {
    try
    {
       //update an data 
      String query2 = "update signup set password = ? where UserName = ?";
      PreparedStatement preparedStmt = (PreparedStatement) ct.prepareStatement(query2);
      preparedStmt.setString (1, password);
      preparedStmt.setString(2, username);

      // execute the java preparedstatement
      preparedStmt.executeUpdate();
    }
    catch(Exception e){}
   }
   
  
   
   public void log() throws ClassNotFoundException ,SQLException {
    try {
         String Driver = "org.gjt.mm.mysql.Driver";
      String myUrl = "jdbc:mysql://localhost/register";
      Class.forName(Driver);
       ct = (Connection) DriverManager.getConnection(myUrl, "root", "");
        if (usr != null && pass1 != null) {
           System.out.println(usr+" "+pass1);
            String sql = "SELECT * FROM `signup` WHERE `UserName` =? AND `password` =?";
        
           PreparedStatement ps = (PreparedStatement) ct.prepareStatement(sql);
            
            ps.setString(1, usr);
            ps.setString(2, pass1);
            
            rt = ps.executeQuery();
            
            if(rt.next())
            {
                //in this case enter when at least one result comes it means user is valid
                  String alert0="Sucessful login";
                JOptionPane.showMessageDialog(null,alert0,"Login Acknowlegdement" ,JOptionPane.INFORMATION_MESSAGE);
            } 
            else {
                //in this case enter when  result size is zero  it means user is invalid
                   String alert1="UserName and password isn't match";
                JOptionPane.showMessageDialog(null,alert1,"Login Acknowlegdement" ,JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // You can also validate user by result size if its comes zero user is invalid else user is valid

    
    }
    catch (SQLException err) {
        JOptionPane.showMessageDialog(this, err.getMessage());
    }
   }
} 

     
  