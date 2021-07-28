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
	
	//�� ���� ���� ����
	@Override
	public String countMessage(String userId) {
		log.info("====���� ����======");
		return mapper.countMessageView(userId);
	}
	//�ֽ� ���� 3�� ��������
	@Override
	public List<MessageVO> readRecentMessages(String userId) {
		log.info("====�ֽ� ���� 3�� ��������======");
		return mapper.readRecentMessages(userId);
	}
	//���� ����
	@Transactional
	@Override
	public int insert(MessageVO vo) {
		log.info("====���� ����======");
		return mapper.insert(vo);
	}
	//�������� ����Ʈ ��������
	@Override
	public List<MessageVO> sentList(String sender) {
		log.info("====���� ���� ���� ����Ʈ======");
		return mapper.sentList(sender);
	}
	//���� ���� ����Ʈ ��������
	@Override
	public List<MessageVO> receptionList(String receiver) {
		log.info("====���� ���� ���� ����Ʈ======");
		return mapper.receptionList(receiver);
	}
	//���� ����ó��
	@Override
	public int readMessage(long mno) {
		log.info("====������ �о����ϴ�.======");
		return mapper.readMessage(mno);
	}
	//���� ����
	@Transactional
	@Override
	public int delete(long mno) {
		log.info("====������ �����մϴ�.======");
		return mapper.delete(mno);
	}

}
