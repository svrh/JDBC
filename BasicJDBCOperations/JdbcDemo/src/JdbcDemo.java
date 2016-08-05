import java.sql.*;

/**
 * Created by svetoslav on 04.08.16.
 */
public class JdbcDemo {
    public static void main(String[] args) throws SQLException {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";
        String user = "student";
        String pass = "demoPass1:)";

        try {
            myConn = DriverManager.getConnection(dbUrl, user, pass);

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery("SELECT * FROM employees");

            while (myRs.next()) {
                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name") + " - " + myRs.getString("email"));
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
