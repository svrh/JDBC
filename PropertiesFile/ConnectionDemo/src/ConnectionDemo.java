import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectionDemo {
    public static void main(String[] args) throws Exception {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("demo.properties"));

            String user = props.getProperty("user");
            String pass = props.getProperty("password");
            String dbUrl = props.getProperty("dburl");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + dbUrl);
            System.out.println("User: " + user);

            myConn = DriverManager.getConnection(dbUrl, user, pass);

            System.out.printf("\nConnection successful!\n\n");

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery("select * from employees");

            while (myRs.next()){
                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        }
    }
}
