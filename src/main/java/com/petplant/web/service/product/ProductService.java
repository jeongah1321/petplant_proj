package com.petplant.web.service.product;

import java.util.List;

import com.petplant.web.model.product.dto.ProductDTO;

public interface ProductService {
 
    // 메서드는 DAO클래스와 동일
	public List<ProductDTO> listAll(String search_option, String keyword,int start, int end) throws Exception; // 상품 전체 목록
	public ProductDTO detailProduct(int product_id) throws Exception; // 상품 상세 정보
	public void updateProduct(ProductDTO dto) throws Exception; // 상품 상세 정보 수정
	public void deleteProduct(int product_id) throws Exception; // 등록한 상품 삭제
	public void insertProduct(ProductDTO dto) throws Exception; // 상품 등록
	public String fileInfo(int product_id); // 파일의 정보 : 파일 삭제할때 파일명 비교하기위해 파일명을 가져오는 메서드

	//레코드 개수 계산
	public int countArticle(String search_option, String keyword) throws Exception;
}