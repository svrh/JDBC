import java.sql.*;

public class JdbcDeleteDemo {
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

            System.out.print("Before the delete: ");
            displayEmployee(myConn, "John", "Doe");

            System.out.println("\nDeleting the employee: John Doe\n");

            int rowsAffected = myStmt.executeUpdate(
                    "DELETE FROM employees " +
                    "WHERE last_name='Doe' and first_name='John'");

            System.out.print("After the delete: ");
            displayEmployee(myConn, "John", "Doe");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }


    }

    private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.prepareStatement("SELECT last_name, first_name FROM  employees WHERE last_name=? AND first_name=?");

            myStmt.setString(1, lastName);
            myStmt.setString(2, firstName);

            myRs = myStmt.executeQuery();

            boolean found = false;

            while (myRs.next()) {
                String ln = myRs.getString("last_name");
                String fn = myRs.getString("first_name");

                System.out.printf("Found employee: %s %s\n", fn, ln);
                found=true;
            }

            if (!found) {
                System.out.printf("Employee not found! (%s %s)\n", firstName, lastName);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myStmt, myRs);
        }
    }

    private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
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

    private static void  close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);
    }
}
