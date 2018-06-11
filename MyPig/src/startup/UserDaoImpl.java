package startup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    private static Connection con;

    static {
        try {
            con = JDBC.getConnection();
        }catch (Exception e){
            System.out.println("获取连接失败！！！");
        }
    }

    @Override
    public User findByName(String cn_user_name) {
        String sql = "select *from cn_user where cn_user_name=?";
        User user = new User();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,cn_user_name);
            ResultSet set = ps.executeQuery();
            while (set.next()){
                user.setCn_user_id(set.getString("cn_user_id"));
                user.setCn_user_name(set.getString("cn_user_name"));
                user.setCn_user_password(set.getString("cn_user_password"));
                user.setCn_user_token(set.getString("cn_user_token"));
                user.setCn_user_nick(set.getString("cn_user_nick"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByUserId(String userId) {
        return null;
    }

    @Override
    public Integer addUser(User user) {
        return null;
    }
}
