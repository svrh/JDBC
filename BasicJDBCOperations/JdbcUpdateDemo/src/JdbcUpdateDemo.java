import java.sql.*;

public class JdbcUpdateDemo {
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

            System.out.print("Before the update: ");
            displayEmployee(myConn, "John", "Doe");

            System.out.println("\nExecuting the update for: John Doe\n");

            int rowsAffected = myStmt.executeUpdate(
                    "UPDATE employees " +
                    "set email='john.doe@foo2.com' " +
                    "WHERE last_name='Doe' AND first_name='John'");

            System.out.print("After the update: ");
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
            myStmt = myConn.prepareStatement("SELECT last_name, first_name, email FROM  employees WHERE last_name=? AND first_name=?");

            myStmt.setString(1, lastName);
            myStmt.setString(2, firstName);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                String ln = myRs.getString("last_name");
                String fn = myRs.getString("first_name");
                String email = myRs.getString("email");

                System.out.printf("%s %s %s\n", fn, ln, email);
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
