package org.yoon.mapper;

import java.util.List; 

import org.yoon.domain.AttachFileDTO;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.MemberAttachVO;

public interface AttachMapper {
	
	public void insert(BoardAttachVO vo);
	
	public void insertUser(MemberAttachVO vo);
	
	public void delete(String uuid);
	
	public void deleteAll(Long bno);
	
	public void deleteUser(String userid);
	
	//게시물 번호로 첨부파일 찾기
	public List<BoardAttachVO> findByBno(Long bno);
	
	//회원 아이디로 프로필파일 찾기
	public List<BoardAttachVO> findByUser(String userid);
	
	public List<BoardAttachVO>getOldFiles();

}
