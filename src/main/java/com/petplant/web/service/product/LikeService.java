package com.petplant.web.service.product;

import java.util.List;

import com.petplant.web.model.product.dto.LikeDTO;
 
public interface LikeService {
    
	public List<LikeDTO> listAll(String login_id); // 개인별 목록
	public void insert(LikeDTO dto); // 추가
	public void update(LikeDTO dto); // 수정(동일 아이템 추가 확인)
    public void delete(int like_id); // 아이템 개별 삭제 
    public void deleteAll(String login_id); // 전체 삭제
    public int countLike(String login_id, int product_id); // 아이템 개수
}
