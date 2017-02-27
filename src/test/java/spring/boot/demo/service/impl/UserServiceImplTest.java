package spring.boot.demo.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import spring.boot.demo.BaseTest;
import spring.boot.demo.entity.User;
import spring.boot.demo.service.UserService;

public class UserServiceImplTest extends BaseTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void testSave() {
		try {
			User user = new User();
			user.setUname("zhangzhan");
			user.setUphone("123456");
			userService.save(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
