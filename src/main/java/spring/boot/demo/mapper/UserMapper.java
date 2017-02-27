package spring.boot.demo.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import spring.boot.demo.entity.Account;

@Repository("userMapper")
public interface UserMapper<T> extends SqlMapper<T> {

	public void inserUser(T user);
	
	public List<T> findUserList();
	
	public void delUserById(Integer uid);
	
	public void updataUser(T user);
	
	public String updateUser2(Map<String, Object> map);
	
	public T findUserById(Integer uid);
	
	public void updateUser(T t);
	
	public List<Account> selectAccForUser(Integer uid);
	
}
