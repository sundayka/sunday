package startup;



public interface UserDao {
	
	public User findByName(String cn_user_name);
	
	public User findByUserId(String userId);
	
	public Integer addUser(User user);

}