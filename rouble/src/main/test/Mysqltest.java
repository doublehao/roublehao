import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Mysqltest {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("�޷���������");
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://10.11.152.80:3306/apkactivity", "MyAdmin", "chiNa@SoRuhen");
        } catch (SQLException e) {
            System.out.println("�޷��������ݿ�");
        } finally {
            System.out.println(conn == null);
        }

        /*try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from orders");
            while(rs.next()) {
                int x = rs.getInt("orders_id");
                System.out.println(x);
            }
        } catch (SQLException e) {
            System.out.println("�޷���ѯ");
        }*/

    }
}

