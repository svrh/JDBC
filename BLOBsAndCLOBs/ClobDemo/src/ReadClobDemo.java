import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.*;

public class ReadClobDemo {
    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        Reader input = null;
        FileWriter output = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "student", "demoPass1:)");

            myStmt = myConn.createStatement();
            String sql = "select resume from employees where email='john.doe@foo.com'";
            myRs = myStmt.executeQuery(sql);

            File theFile = new File("resume.txt");
            output = new FileWriter(theFile);

            if (myRs.next()) {
                input = myRs.getCharacterStream("resume");
                System.out.println("Reading resume from database...");
                System.out.println(sql);

                int theChar;
                while ((theChar = input.read())> 0) {
                    output.write(theChar);
                }

                System.out.println("\nSaved to file: " + theFile.getAbsolutePath());
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
