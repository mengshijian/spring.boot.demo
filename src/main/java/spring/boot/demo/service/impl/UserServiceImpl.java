package spring.boot.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.demo.entity.User;
import spring.boot.demo.mapper.UserMapper;
import spring.boot.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Resource(name="userMapper")
	private UserMapper<User> mapper;
	@Override
	public void save(User user) {
		mapper.inserUser(user);
	}
	@Override
	public List<User> findAllUser() {
		return mapper.findUserList();
	}
}
