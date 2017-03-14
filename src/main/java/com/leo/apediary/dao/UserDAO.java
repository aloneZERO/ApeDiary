package com.leo.apediary.dao;

import com.leo.apediary.domain.User;

public interface UserDAO {

	User find(String username, String password);

	int update(User user);
	
}
