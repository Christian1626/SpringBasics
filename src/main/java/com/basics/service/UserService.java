package com.basics.service;

import java.util.List;

import com.basics.model.User;

public interface UserService {
	public List<User> getAllUsers();
	public List<User> search(String critere, String property);
	public User getUser(int id);
	public void createUser(final User user);
	public void updateUser(final User user);
	public void deleteUser(final User user);
}
