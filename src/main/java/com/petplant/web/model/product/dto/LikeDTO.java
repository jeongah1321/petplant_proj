package com.petplant.web.model.product.dto;

import org.springframework.web.multipart.MultipartFile;

public class LikeDTO {
    private int like_id; //카트에 담기는 아이템 번호?
    private String login_id;
    private String login_name;
    private int product_id;
    private String product_name_ko;
    private String product_name_en;
    private String classification;
    private String difficulty;
    private String picture_url;
    private MultipartFile file1;
    private int like_check;
	
	// Getters and Setters
	public int getLike_id() {
		return like_id;
	}
	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name_ko() {
		return product_name_ko;
	}
	public void setProduct_name_ko(String product_name_ko) {
		this.product_name_ko = product_name_ko;
	}
	public String getProduct_name_en() {
		return product_name_en;
	}
	public void setProduct_name_en(String product_name_en) {
		this.product_name_en = product_name_en;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public MultipartFile getFile1() {
		return file1;
	}
	public void setFile1(MultipartFile file1) {
		this.file1 = file1;
	}
	public int getLike_check() {
		return like_check;
	}
	public void setLike_check(int like_check) {
		this.like_check = like_check;
	}
	// toStirng
    @Override
	public String toString() {
		return "LikeDTO [like_id=" + like_id + ", login_id=" + login_id + ", login_name=" + login_name + ", product_id="
				+ product_id + ", product_name_ko=" + product_name_ko + ", product_name_en=" + product_name_en
				+ ", classification=" + classification + ", difficulty=" + difficulty + ", picture_url=" + picture_url
				+ ", file1=" + file1 + ", like_check=" + like_check + "]";
	}



}
