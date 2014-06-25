package com.contactmanger.service;

public class Contacts {
	
	private long _id;
	private String imagePath;
	private String description;
	private Boolean isFavorite;
	
	Contacts (String imagePath, String desctription, Boolean isFavorite){
		this.imagePath = imagePath;
		this.description = desctription;
		this.isFavorite = isFavorite;
	}
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
}
