package org.yoon.mapper;

import java.util.List; 

import org.yoon.domain.AttachFileDTO;
import org.yoon.domain.BoardAttachVO;

public interface AttachMapper {
	
	public void insert(BoardAttachVO vo);
	
	public void delete(String uuid);
	
	public void deleteAll(Long bno);
	
	//게시물 번호로 첨부파일 찾기
	public List<BoardAttachVO> findByBno(Long bno);
	
	public List<BoardAttachVO>getOldFiles();

}
