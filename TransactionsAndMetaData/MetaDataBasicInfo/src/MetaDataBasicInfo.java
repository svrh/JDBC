import java.sql.*;

public class MetaDataBasicInfo {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            DatabaseMetaData databaseMetaData = myConn.getMetaData();

            System.out.println("\nBasic MetaData\n");

            System.out.println("Product name: " + databaseMetaData.getDatabaseProductName());
            System.out.println("Product version: " + databaseMetaData.getDatabaseProductVersion());
            System.out.println();

            System.out.println("JDBC Driver name: " + databaseMetaData.getDriverName());
            System.out.println("JDBC Driver version: " + databaseMetaData.getDriverVersion());

            System.out.println("\nSchemaInfo\n");
            displaySchemaInfo(databaseMetaData);

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, null);
        }
    }

    private static void displaySchemaInfo(DatabaseMetaData databaseMetaData) throws SQLException {
        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        String columnNamePattern = null;
        String types[] = null;

        ResultSet myRs = null;

        try {
            System.out.println("List of Tables");
            System.out.println("--------------");

            myRs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);

            while (myRs.next()) {
                System.out.println(myRs.getString("TABLE_NAME"));
            }

            System.out.println("\n\nList of Columns");
            System.out.println("----------------");

            myRs = databaseMetaData.getColumns(catalog, schemaPattern, "employees", columnNamePattern);

            while (myRs.next()) {
                System.out.println(myRs.getString("COLUMN_NAME"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(null, myRs);
        }
    }

    private static void close(Connection myConn, ResultSet myRs) throws SQLException {
        if (myConn != null) {
            myConn.close();
        }
        if (myRs != null) {
            myRs.close();
        }
    }
}
