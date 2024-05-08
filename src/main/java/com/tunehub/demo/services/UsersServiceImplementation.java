package com.tunehub.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.demo.entities.Users;
import com.tunehub.demo.repositories.UsersRepository;

@Service
public class UsersServiceImplementation implements UsersService {

	@Autowired
	UsersRepository repo;

	@Override
	public String addUser(Users user) {

		repo.save(user);

		return "user added successfully";
	}

	@Override
	public boolean emailExists(String email) {

		if (repo.findByEmail(email) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean validateUser(String email, String password) {
		Users user = repo.findByEmail(email);

		if (user != null) {
			if (password.equals(user.getPassword())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public String getRole(String email) {
		Users user = repo.findByEmail(email);
		return user.getRole();
	}

	@Override
	public Users getUserByEmail(String email) {
		Users user = repo.findByEmail(email);
		return user;
	}

	@Override
	public void updateUser(Users user) {
		repo.save(user);
	}

	@Override
	public boolean updatePassword(String email, String password) {
		// TODO Auto-generated method stub
		Users user = repo.findByEmail(email);
		if(user==null) {
			return false;
		}else {
			user.setPassword(password);
			repo.save(user);
			return true;
		}
		
	}

}
