package controller;

/**
 *
 * @author almir
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Konekcija {

    Connection conn = null;

    public static Connection konekcija() throws FileNotFoundException, IOException, SQLException {

        int pozicija = 0;
        String url = "";
        String user = "";
        String password = "";

        //POZICIJA KOJU KORISTI PROGRAM DIST POZICIJE
        //File file = new File("../src/hostbaze.txt");
        //POZICIJA KOJU KORISTI NETBEANS PRI POKRETANJU
        //File file = new File("hostbaze.txt");
        File file = new File("hostbaze.txt");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (String word : line.split("\\s")) {
                if (!word.isEmpty()) {

                    switch (pozicija) {
                        case 0:
                            url = word;
                            break;
                        case 1:
                            user = word;
                            break;
                        case 2:
                            password = word;
                            break;
                        default:
                            break;
                    }

                }
                pozicija++;
            }
        }

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            //Connection conn = DriverManager.getConnection("jdbc:mysql://204.2.195.97:15406/testbaza?allowPublicKeyRetrieval=true&useSSL=false?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "almir97", "mspassword");
            return conn;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}


/*public class Konekcija {

    Connection conn = null;

    public static Connection konekcija() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://162.55.48.186:3306/vpsdb?allowPublicKeyRetrieval=true&useSSL=false?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "almo", "ITA-lm1997");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }
}
*/
