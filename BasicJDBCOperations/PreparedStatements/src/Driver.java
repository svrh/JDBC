import java.sql.*;

public class Driver {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";
        String user = "student";
        String pass = "demoPass1:)";

        try {
            myConn = DriverManager.getConnection(dbUrl, user, pass);

            myStmt = myConn.prepareStatement("SELECT * FROM employees WHERE salary > ? and department=?");
            myStmt.setDouble(1, 80000);
            myStmt.setString(2, "Legal");

            myRs = myStmt.executeQuery();

            display(myRs);

            System.out.println("\nReuse the prepared statement: salary > 25000, department = HR\n");

            myStmt.setDouble(1, 25000);
            myStmt.setString(2, "HR");

            myRs = myStmt.executeQuery();

            display(myRs);

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

    public static void display(ResultSet myRs) throws SQLException{
        while (myRs.next()) {
            String firstName = myRs.getString("first_name");
            String lastName = myRs.getString("last_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.printf("%s, %sm %.2f, %s\n", lastName, firstName, salary, department);
        }
    }
}
