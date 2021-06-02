package org.yoon.cron;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yoon.domain.BoardAttachVO;
import org.yoon.mapper.AttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
//db내에 기록되있는 파일정보와 실제 c경로에 있는 파일의 존재 비교를 통해서 db에 없는 정보가 c경로에 존재 삭제를 진행하는 트리거
public class FileCheck {

	@Setter(onMethod_=@Autowired)
	private AttachMapper attachMapper;
	
	//어제 날짜 생성 메서드
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str= sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
	//초,분,시,일,월,주,년   //매일 새벽2시
	@Scheduled(cron= "0 0 2 * * *")
	public void checkFiles() throws Exception{
		log.warn("파일 목록 확인");
		log.warn("파일 목록 확인");
		
		//어제 날짜 폴더에 속해있는 파일 리스트 가져오기
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		//파일 주소 가져오기
		List<Path>fileListPaths = fileList.stream()
				.map(vo -> Paths.get("c:\\upload1", vo.getUploadPath(),vo.getUuid()+"_"+vo.getFileName()))
				.collect(Collectors.toList());
		//파일 타입이 이미지 타입이면?
		fileList.stream().filter(vo ->vo.isFileType()==true)
			.map(vo -> Paths.get("c:\\upload1", vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName()))
			.forEach(p->fileListPaths.add(p));
		
		log.warn("==================================");
		fileListPaths.forEach(p -> log.warn(p));
		
		File targetDir = Paths.get("c:\\upload1",getFolderYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file ->fileListPaths.contains(file.toPath())==false);
		
		log.warn("===================================");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
		
		
	}
}
