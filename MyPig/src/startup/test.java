package startup;

import java.sql.Connection;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        UserService service = new UserServiceImpl();
        System.out.println("service");
        NoteResult<User> result = service.checkLogin("123","e10adc3949ba59abbe56e057f20f883e");
        System.out.println(result);
    }
}
