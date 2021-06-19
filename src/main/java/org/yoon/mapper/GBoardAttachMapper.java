package org.yoon.mapper;

import java.util.List;

import org.yoon.domain.GBoardAttachVO;


public interface GBoardAttachMapper {
	
	public void insert(GBoardAttachVO vo);
	
	public void delete(String uuid);
	
	public List<GBoardAttachVO> findBygno(Long gno);
	
	public void deleteAll(Long gno);
	
}