package com.synchrony.ImageUpload.services;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synchrony.ImageUpload.entities.Image;
import com.synchrony.ImageUpload.entities.User;
import com.synchrony.ImageUpload.exceptions.IncorrectPasswordException;
import com.synchrony.ImageUpload.exceptions.NoUserFoundException;
import com.synchrony.ImageUpload.models.UserModel;
import com.synchrony.ImageUpload.repos.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User addUser(String username, String password) {
		
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);
		log.info("saving new User");
		return userRepository.save(user);
		
		
	}


	public User addImages(String username, String password, Set<Image> images) throws Exception {
		
		User existinguser = validateUser(username);
		
		validatePassword(password, existinguser.getPassword());
		
		Set<Image> existingImages = existinguser.getImages();
		existingImages.addAll(images);
		existinguser.setImages(existingImages);
		log.info("Saving new Image for User : {}",existinguser.getUserName());
		return userRepository.save(existinguser);
	}


	public User getUser(Long id) throws Exception {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(optionalUser.isEmpty()) {
			log.info("User does not exist");
			throw new NoUserFoundException("User does not exist");
		}
		User existingUser = optionalUser.get();
		log.info("fetching User and Images");
		return existingUser;
		
	}


	public void deleteUser(Long id, UserModel userModel) throws Exception {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isEmpty()) {
			System.out.println("User does not Exist");
			throw new NoUserFoundException("User does not Exist");
		}
		User user = validateUser(userModel.getUsername());
		validatePassword(userModel.getPassword(), user.getPassword());
		log.info("deleting User with id : {}",id);
		
		userRepository.deleteById(id);
		
	} 
	
	public User validateUser(String username) throws Exception {
Optional<User> optionalUser = userRepository.findByUserName(username);
		
		if(optionalUser.isEmpty()) {
			System.out.println("User does not Exist");
			throw new NoUserFoundException("User does not Exist");
		}
		User existinguser = optionalUser.get();
		return existinguser;
	}
	
	public void validatePassword(String password, String actualPassword) throws Exception {
		if(!StringUtils.equals(password, actualPassword)) {
			System.out.println("password is incorrect");
			throw new IncorrectPasswordException("password is incorrect");
		}
	}

}
