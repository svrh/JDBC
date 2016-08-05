import java.io.File;
import java.io.FileInputStream;
import  java.sql.*;

public class WriteBlobDemo {
    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        FileInputStream input = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            String sql = "update employees set resume=? where email='john.doe@foo.com'";
            myStmt = myConn.prepareStatement(sql);

            File theFile = new File("CV.pdf");
            input = new FileInputStream(theFile);
            myStmt.setBinaryStream(1, input);

            System.out.println("Reading input file: " + theFile.getAbsolutePath());

            System.out.println("\nStoring CV in database: " + theFile);
            System.out.println(sql);

            myStmt.executeUpdate();

            System.out.println("\nCompleted successfully!");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
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
