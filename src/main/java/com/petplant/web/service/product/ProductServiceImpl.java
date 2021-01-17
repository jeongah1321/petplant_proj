package com.petplant.web.service.product;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.petplant.web.model.product.dao.ProductDAO;
import com.petplant.web.model.product.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
    ProductDAO productDao;
	
	// 상품 전체 목록
	@Override
	public List<ProductDTO> listAll(String search_option, String keyword, int start, int end) throws Exception {
		return productDao.listAll(search_option, keyword, start, end);
	}

	// 상품 상세 정보
	@Override
	public ProductDTO detailProduct(int product_id) throws Exception {
		return productDao.detailProduct(product_id);
	}

	// 상품 상세 정보 수정
	@Override
	public void updateProduct(ProductDTO dto) throws Exception {
		productDao.updateProduct(dto);
	}

	// 등록한 상품 삭제
	@Override
	public void deleteProduct(int product_id) throws Exception {
		productDao.deleteProduct(product_id);
	}

	// 상품 등록
	@Override
	public void insertProduct(ProductDTO dto) throws Exception {
		productDao.insertProduct(dto);
	}
	
	// 파일의 정보 : 파일 삭제할때 파일명 비교하기위해 파일명을 가져오는 메서드
	@Override
	public String fileInfo(int product_id) {
		return productDao.fileInfo(product_id);
	}
	
	//레코드 개수 계산
	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		return productDao.countArticle(search_option, keyword); 
		//boardDao를 호출해서 리턴
	}
}
