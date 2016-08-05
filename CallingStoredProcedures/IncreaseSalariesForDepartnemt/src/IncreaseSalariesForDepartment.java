import javax.management.StandardEmitterMBean;
import java.sql.*;

public class IncreaseSalariesForDepartment {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            String theDepartment = "Engineering";
            int theIncreaseAmount = 10000;

            System.out.println("Salaries before\n");
            showSalaries(myConn, theDepartment);

            myStmt = myConn.prepareCall("{call increase_salaries_for_department(?, ?)}");

            myStmt.setString(1, theDepartment);
            myStmt.setDouble(2, theIncreaseAmount);

            System.out.println("\nCalling stored procedure -> increase_salaries_for_department('" + theDepartment + "', " + theIncreaseAmount + ")");
            myStmt.execute();
            System.out.println("Finished calling stored procedure.");

            System.out.println("\nSalaries after\n");
            showSalaries(myConn, theDepartment);

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.prepareStatement("SELECT * FROM employees WHERE department=?");
            myStmt.setString(1, theDepartment);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                String lastName = myRs.getString("last_name");
                String firstName = myRs.getString("first_name");
                double salary = myRs.getDouble("salary");
                String department = myRs.getString("department");

                System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
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
