package com.petplant.web.model.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.product.dto.ProductDTO;


@Repository
public class ProductDAOImpl implements ProductDAO {
	
    @Inject
    SqlSession sqlSession;

	// 상품 전체 목록
	@Override
	public List<ProductDTO> listAll(String search_option, String keyword, int start, int end) throws Exception {     
		
		Map<String,Object> map = new HashMap<String, Object>();
        map.put("search_option", search_option);
        map.put("keyword", "%" + keyword + "%");
        map.put("start", start); // 맵에 자료 저장
        map.put("end", end);
        
		return sqlSession.selectList("product.listAll", map);
	}

	// 상품 상세 정보
	@Override
	public ProductDTO detailProduct(int product_id) throws Exception {
		
		return sqlSession.selectOne("product.detail_product", product_id);
	}

	// 상품 상세 정보 수정
	@Override
	public void updateProduct(ProductDTO dto) throws Exception {
		
		sqlSession.update("product.update_product",dto);
	}

	// 등록한 상품 삭제
	@Override
	public void deleteProduct(int product_id) throws Exception {
		
		sqlSession.delete("product.product_delete",product_id);
	}

	// 상품 등록
	@Override
	public void insertProduct(ProductDTO dto) throws Exception {
		
		sqlSession.insert("product.insert", dto);
	}

	// 파일의 정보 : 파일 삭제할때 파일명 비교하기위해 파일명을 가져오는 메서드
	@Override
	public String fileInfo(int product_id) {
		
		return sqlSession.selectOne("product.file_info",product_id);
	}

	//레코드 개수 계산
	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("search_option", search_option);
		map.put("keyword", "%"+keyword+"%");
		
		return sqlSession.selectOne("product.countArticle", map);
	}
}
