import java.sql.*;

public class GetCountForDepartment {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            String theDepartment = "Engineering";

            myStmt = myConn.prepareCall("{call get_count_for_department(?, ?)}");
            myStmt.setString(1, theDepartment);
            myStmt.registerOutParameter(2, Types.INTEGER);

            System.out.println("Calling stored procedure -> get_count_for_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.printf("Finished calling stored procedure.\n");

            int theCount = myStmt.getInt(2);

            System.out.println("\nThe Count: "+ theCount);
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
