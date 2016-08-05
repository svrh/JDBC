import java.sql.*;

public class GreetTheDepartment {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            String theDepartment = "Engineering";

            myStmt = myConn.prepareCall("{call greet_the_department(?)}");
            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.setString(1, theDepartment);

            System.out.println("Calling stored procedure -> greet_the_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finished calling stored procedure.");

            String theResult = myStmt.getString(1);

            System.out.println("\nThe result: " + theResult);

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        }
    }
}
