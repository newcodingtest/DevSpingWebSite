package org.yoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyPageDTO;
import org.yoon.domain.ReplyVO;
import org.yoon.mapper.BoardMapper;
import org.yoon.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{

	private ReplyMapper mapper;
	
	private BoardMapper Bmapper;
	
	@Transactional
	@Override
	public int register(ReplyVO reply) {
		log.info("reply register:  "+ reply);
		Bmapper.updateReplyCnt(reply.getBno(), 1);
		return mapper.insert(reply);
	}

	@Override
	public ReplyVO get(Long rno) {
		log.info("reply get:  "+ rno);
		return mapper.read(rno);
	}

	@Transactional
	@Override
	public int remove(Long rno) {
		log.info("reply remove:  "+ rno);
		ReplyVO vo = mapper.read(rno);
		
		Bmapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public int modify(ReplyVO reply) {
		log.info("reply modify:  "+ reply);
		return mapper.update(reply);
	}

	@Override
	public ReplyPageDTO getList(Criteria cri, Long bno) {
		log.info("reply getList:  "+ bno);
		return new ReplyPageDTO(mapper.getCountByBno(bno),mapper.getListPaging(cri, bno));
	}
	
	
	
}
	
