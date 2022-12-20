package com.synchrony.ImageUpload.Controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synchrony.ImageUpload.entities.Image;
import com.synchrony.ImageUpload.entities.User;
import com.synchrony.ImageUpload.models.UserModel;
import com.synchrony.ImageUpload.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/v1/user", produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<User> registeruser(@RequestBody UserModel userModel) {
		
		User user = userService.addUser(userModel.getUsername(), userModel.getPassword());
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/v1/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<User> getUser(@PathVariable Long id) throws Exception {
		
		User user =  userService.getUser(id);
		
		return ResponseEntity.ok(user);
		
	}
	
	@DeleteMapping(path = "/v1/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteUser(@PathVariable Long id, @RequestBody UserModel userModel) throws Exception {
		
		userService.deleteUser(id,userModel);
	}
	
	@PutMapping(value = {"/v1/user/upload-images"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<User> updloadImages(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "file") MultipartFile[] file) {
		
		try {
			log.info("uploading new Image for user :{}", username);
			Set<Image> images = uploadImage(file);
			User user = userService.addImages(username,password,images);
			return ResponseEntity.ok(user);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	private Set<Image> uploadImage(MultipartFile[] files) throws IOException {
		Set<Image> images = new HashSet<>();
		
		for(MultipartFile file : files) {
			Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			images.add(image);
		}
		return images;
	}
}
