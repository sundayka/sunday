package startup;

import javax.annotation.Resource;



public class UserServiceImpl implements UserService{
	
	private UserDao userDao = new UserDaoImpl();

	public NoteResult<User> checkLogin(String name,String password) {
		NoteResult<User> result = new NoteResult<User>();
		User user = userDao.findByName(name);
		if (user==null) {
			result.setStatus(1);
			result.setMsg("用户名不存在");
			return result;
		}else if (!password.equals(user.getCn_user_password())) {
			result.setStatus(2);
			result.setMsg("密码错误");
			return result;
		}
		result.setStatus(0);
		result.setMsg("登陆成功");
		result.setData(user);
		return result;
	}

	public NoteResult<User> addUser(String name, String nickname, String password) {
		NoteResult<User> result = new NoteResult<User>();
		User user = userDao.findByName(name);
		if (user != null) {
			result.setStatus(1);
			result.setMsg("该用户名不可用");
			return result;
		}else {
			user = new User();
			user.setCn_user_id(NoteUtil.createId());
			user.setCn_user_name(name);
			user.setCn_user_nick(nickname);
			user.setCn_user_password(Md5Utils.md5(password));
			int condition= userDao.addUser(user);
			if (condition==1) {
				result.setStatus(0);
				result.setMsg("注册成功！");
				result.setData(user);
				return result;
			}else {
				result.setStatus(1);
				result.setMsg("注册失败");
				return result;
			}
			
		}
		
	}
	

	

}
