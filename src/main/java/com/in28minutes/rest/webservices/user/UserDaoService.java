package com.in28minutes.rest.webservices.user;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.time.LocalDate;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();

	private static int userCount = 0;
	static {
		users.add(new User(++userCount, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(++userCount, "Eve", LocalDate.now().minusYears(25)));
		users.add(new User(++userCount, "Tom", LocalDate.now().minusYears(20)));
	}
	
	public List<User> findAll()	{
		return users;
	}
	
	public User findone(int userId)	{
		//User user = null;
		Predicate<? super User> predicate = user -> ((Integer)user.getId()).equals(userId);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void save(User user) {
		user.setId(++userCount);
		users.add(user);
	}
	
}
