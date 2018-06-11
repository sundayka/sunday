package startup;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


public class UserLoginController {
	
	private UserService userService = new UserServiceImpl();

	public NoteResult<User> execute(String name,String password){
		
		System.out.println(name+":"+password);
		NoteResult<User> result = userService.checkLogin(name, password);
		System.out.println(result);
		return result;
		
	}

	public NoteResult<User> execute(String name,String nickname,String password,HttpServletResponse res){
		System.out.println(name+nickname+password);
		NoteResult<User> result = userService.addUser(name, nickname, password);
		System.out.println(result);
		return result;
	}
	
}
