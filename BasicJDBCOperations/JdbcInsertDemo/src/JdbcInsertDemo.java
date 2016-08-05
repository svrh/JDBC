import java.sql.*;

public class JdbcInsertDemo {
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

            System.out.println("Inserting a new emploee to database\n");

            int rowsAffected = myStmt.executeUpdate(
                    "INSERT INTO employees " +
                    "(last_name, first_name, email, department, salary) " +
                    "VALUES " +
                    "('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 33000.00)");

            myRs = myStmt.executeQuery("SELECT * FROM employees order by last_name");

            while (myRs.next()) {
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
