package org.yoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yoon.domain.MessageVO;
import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.MemberMapper;
import org.yoon.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService{

	private MessageMapper mapper;
	
	//안 읽은 쪽지 개수
	@Override
	public String countMessage(String userId) {
		log.info("====쪽지 개수======");
		return mapper.countMessageView(userId);
	}
	//최신 쪽지 3개 가져오기
	@Override
	public List<MessageVO> readRecentMessages(String userId) {
		log.info("====최신 쪽지 3개 가져오기======");
		return mapper.readRecentMessages(userId);
	}
	//쪽지 전송
	@Transactional
	@Override
	public int insert(MessageVO vo) {
		log.info("====쪽지 전송======");
		return mapper.insert(vo);
	}
	//보낸쪽지 리스트 가져오기
	@Override
	public List<MessageVO> sentList(String sender) {
		log.info("====내가 보낸 쪽지 리스트======");
		return mapper.sentList(sender);
	}
	//받은 쪽지 리스트 가져오기
	@Override
	public List<MessageVO> receptionList(String receiver) {
		log.info("====내가 받은 쪽지 리스트======");
		return mapper.receptionList(receiver);
	}
	//쪽지 읽음처리
	@Override
	public int readMessage(long mno) {
		log.info("====쪽지를 읽었습니다.======");
		return mapper.readMessage(mno);
	}
	//쪽지 삭제
	@Transactional
	@Override
	public int delete(long mno) {
		log.info("====쪽지를 삭제합니다.======");
		return mapper.delete(mno);
	}

}
