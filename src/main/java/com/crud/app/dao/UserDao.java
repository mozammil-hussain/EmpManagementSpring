package com.crud.app.dao;

import com.crud.app.entity.User;

public interface UserDao {
	
	public int saveUser(User u);
	public User loginUser(String email,String password);

}
