package startup;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static int initialSize;
    private static int maxActive;
    private static BasicDataSource ds;


    static {
        try {
            ds = new BasicDataSource();
            Properties pr = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+File.separator+"config/db.properties"));
            pr.load(bufferedReader);
            driver = pr.getProperty("jdbc.driver");
            url = pr.getProperty("jdbc.url");

            username = pr.getProperty("jdbc.username");
            password = pr.getProperty("jdbc.password");

            initialSize = Integer.parseInt(pr.getProperty("jdbc.initialSize"));
            maxActive = Integer.parseInt(pr.getProperty("jdbc.maxSize"));
        } catch (Exception e) {
            System.out.println("读取配置文件失败！！！");
        }

        ds.setDriverClassName(driver);
        ds.setUrl(url);

        ds.setUsername(username);
        ds.setPassword(password);

        ds.setInitialSize(initialSize);
        ds.setMaxActive(maxActive);

    }
    /**
     * getConnection()从连接池中获取重用
     * 如果满了则等待，有归还则获取
     */
    public static Connection getConnection() throws SQLException {
        Connection con = ds.getConnection();
        return con;
    }

    public static Connection getConnection(boolean setCommit) throws SQLException{
        Connection connection = ds.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    public static void close(Connection con) {
        if (con!=null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("数据库关闭失败！！！");
            }
        }
    }

    public static void rollback(Connection con) {
        if (con!=null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                System.out.println("数据库回滚失败！！！");
            }
        }
    }

}