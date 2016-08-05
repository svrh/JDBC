import java.sql.*;

public class GetEmployeesForDepartment {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        CallableStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            String theDepartment = "Engineering";

            myStmt = myConn.prepareCall("{call get_employees_for_department(?)}");
            myStmt.setString(1, theDepartment);

            System.out.println("Calling stored procedure -> get_employees_for_department('" + theDepartment +"')");
            myStmt.execute();
            System.out.println("Finished calling the stored procedure.\n");

            myRs = myStmt.getResultSet();
            display(myRs);
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
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

    private static void display(ResultSet myRs) throws SQLException {
        while (myRs.next()) {
            String lastName = myRs.getString("last_name");
            String firstName = myRs.getString("first_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
        }
    }

}
