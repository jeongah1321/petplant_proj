package com.petplant.web.service.product;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.petplant.web.model.product.dao.LikeDAO;
import com.petplant.web.model.product.dto.LikeDTO;

@Service
public class LikeServiceImpl implements LikeService {

	@Inject
    LikeDAO likeDao;
	
	// 개인별 목록
	@Override
	public List<LikeDTO> listAll(String login_id) {
		return likeDao.listAll(login_id);
	}

	// 추가 
	@Override
	public void insert(LikeDTO dto) {
		 likeDao.insert(dto);
	}
	
	// 수정(동일 아이템 추가 확인)
	@Override
	public void update(LikeDTO dto) {
		likeDao.update(dto);
	}
	
	// 아이템 개별 삭제 
	@Override
	public void delete(int like_id) {
		likeDao.delete(like_id);
	}

	// 전체 삭제
	@Override
	public void deleteAll(String login_id) {
		likeDao.deleteAll(login_id);
	}

	// 동일한 아이템 레코드 확인
	@Override
	public int countLike(String login_id, int product_id) {
		return likeDao.countLike(login_id, product_id);
	}	
}
