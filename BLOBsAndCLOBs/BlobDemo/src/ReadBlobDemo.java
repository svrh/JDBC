import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

public class ReadBlobDemo {
    public static void main(String[] args) throws Exception {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        InputStream input = null;
        FileOutputStream output = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            myStmt = myConn.createStatement();
            String sql = "select resume from employees where email='john.doe@foo.com'";
            myRs = myStmt.executeQuery(sql);

            File theFIle = new File("CV_from_db.pdf");
            output = new FileOutputStream(theFIle);

            if (myRs.next()) {
                input = myRs.getBinaryStream("resume");
                System.out.println("Reading resume from database...");
                System.out.println(sql);

                byte buffer[] = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }

                System.out.println("\nSaved to file: " + theFIle.getAbsolutePath());
                System.out.println("\nCompleted successfully!");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }

            if (output != null) {
                output.close();
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
