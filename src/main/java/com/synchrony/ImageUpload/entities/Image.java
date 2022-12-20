package com.synchrony.ImageUpload.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "image_table")
public class Image {
	
	
	public Image() {
		super();
	}

	public Image(String name, String type, byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long imageId;
	
	@Column
	private String name;
	
	@Column
	private String type;
	
	@Column(length = 50000000)
	private byte[] picByte;
	
	@ManyToOne
	@JoinColumn(name = "user_user_id", referencedColumnName = "userId")
	@JsonIgnore
	private User user;

}
