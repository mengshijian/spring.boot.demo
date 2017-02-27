package spring.boot.demo.service;

import java.util.List;

import spring.boot.demo.entity.User;

public interface UserService {

	void save(User user);

	List<User> findAllUser();
}
