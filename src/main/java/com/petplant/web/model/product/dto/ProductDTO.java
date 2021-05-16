package com.petplant.web.model.product.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
    
    private int product_id;
    private String product_name_ko;
    private String product_name_en;
    private String classification;
    private String origin;
    private String difficulty;
    private String description;
    private String picture_url;
    private MultipartFile file1;
	
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public String toString() {
		return "ProductDTO [product_id=" + product_id + ", product_name_ko=" + product_name_ko + ", product_name_en="
				+ product_name_en + ", classification=" + classification + ", origin=" + origin + ", difficulty="
				+ difficulty + ", description=" + description + ", picture_url=" + picture_url + ", file1=" + file1
				+ "]";
	}
}