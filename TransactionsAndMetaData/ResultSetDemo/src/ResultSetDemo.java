import java.sql.*;

public class ResultSetDemo {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT id, last_name, first_name, salary FROM employees");

            ResultSetMetaData rsMetaData = myRs.getMetaData();

            int columnCount = rsMetaData.getColumnCount();
            System.out.println("Column count: " + columnCount + "\n");

            for (int column = 1; column <= columnCount; column++) {
                System.out.println("Column name: " + rsMetaData.getColumnName(column));
                System.out.println("Column type name: " + rsMetaData.getColumnTypeName(column));
                System.out.println("Is Nullable: " + rsMetaData.isNullable(column));
                System.out.println("Is Auto Increment: " + rsMetaData.isAutoIncrement(column));
                System.out.println();
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
