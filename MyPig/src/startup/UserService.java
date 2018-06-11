package startup;


public interface UserService {

	public NoteResult<User> checkLogin(String name, String password);
	
	public NoteResult<User> addUser(String name, String nickname, String password);
		
}
