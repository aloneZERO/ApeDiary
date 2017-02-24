package com.leo.diary.dao;

import com.leo.diary.domain.User;

public interface UserDAO {

	User find(String username, String password);

	int update(User user);
	
}
