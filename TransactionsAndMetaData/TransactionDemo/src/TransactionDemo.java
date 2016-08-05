import java.sql.*;
import java.util.Scanner;

public class TransactionDemo {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            myConn.setAutoCommit(false);

            System.out.println("Salaries before: \n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

            myStmt = myConn.createStatement();
            myStmt.executeUpdate("DELETE FROM employees WHERE department='HR'");
            myStmt.executeUpdate("UPDATE employees SET salary=300000 WHERE department='Engineering'");

            System.out.println("\nTransaction steps are ready.\n");

            boolean ok = askUserIfOkToSave();

            if (ok) {
                myConn.commit();
                System.out.println("\nTransactions commited.\n");
            } else {
                myConn.rollback();
                System.out.println("\nTransactions rolled back.\n");
            }

            System.out.println("Salaries after:\n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private static boolean askUserIfOkToSave() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Is it OK to save? yes/no: ");
        String input = scanner.nextLine();
        scanner.close();
        return input.equalsIgnoreCase("yes");
    }

    private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        System.out.println("\nShowing salaries for department: " + theDepartment);

        try {
            myStmt = myConn.prepareStatement("SELECT * FROM employees WHERE department =?");
            myStmt.setString(1, theDepartment);
            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                String lastName = myRs.getString("last_name");
                String firstName = myRs.getString("first_name");
                double salary = myRs.getDouble("salary");
                String department = myRs.getString("department");

                System.out.printf("%s, %s, %s, %.2f\n", lastName, firstName,
                        department, salary);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(null, myStmt, myRs);
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
}
