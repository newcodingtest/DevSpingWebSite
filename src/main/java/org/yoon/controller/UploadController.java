package org.yoon.controller;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.yoon.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j
@Controller
public class UploadController {

	// 년/월/일 폴더 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

	// 이미지 타입 확인 메서드
	private boolean checkImageType(File file) {

		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@GetMapping("/uploadAjax1")
	public void uploadForm() {
		log.info("업로드 폼");
	}

	/*
	 * @PostMapping("/uploadAjaxAction1") public void
	 * uploadFormAction(MultipartFile[] uploadFile, Model model) {
	 * 
	 * String uploadFolder = "C:\\upload1";
	 * 
	 * File uploadPath = new File(uploadFolder,getFolder()); log.info(uploadPath);
	 * 
	 * if(uploadPath.exists() == false) { uploadPath.mkdirs(); }
	 * 
	 * for(MultipartFile multipartFile : uploadFile) { log.info("==========");
	 * log.info("업로드 파일이름:  "+multipartFile.getOriginalFilename());
	 * log.info("업로드 파일크기:  "+multipartFile.getSize());
	 * 
	 * String uploadFileName = multipartFile.getOriginalFilename(); //중복방지 uuid
	 * 적용========================================================== UUID uuid =
	 * UUID.randomUUID(); //[uuid랜덤값_파일 본래이름] uploadFileName =
	 * uuid.toString()+"_"+uploadFileName;
	 * 
	 * 
	 * //IE uploadFileName =
	 * uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
	 * log.info(uploadFileName);
	 * 
	 * File saveFile = new File(uploadPath, uploadFileName); try {
	 * multipartFile.transferTo(saveFile); } catch (Exception e) {
	 * log.error(e.getMessage()); } } }
	 */

	/*
	 * //업로드된 파일을 받아서 서버위치로 저장
	 * 
	 * @PostMapping("/uploadFormAction") public void uploadFormPost(MultipartFile[]
	 * uploadFile, Model model) { String uploadFolder = "C:\\upload1";
	 * 
	 * for(MultipartFile multipartFile : uploadFile) {
	 * log.info("===========================");
	 * log.info("업로드 파일 이름:  "+multipartFile.getOriginalFilename());
	 * log.info("업로드 파일 크기:  "+multipartFile.getSize());
	 * 
	 * //파일 생성(부모,자식) File saveFile = new File(uploadFolder,
	 * multipartFile.getOriginalFilename()); try { //브라우저에서 업로드받은 파일을 서버의 파일 도착지인
	 * saveFile로 transferTo 을 이용해 전송 multipartFile.transferTo(saveFile); } catch
	 * (Exception e) { log.error(e.getMessage()); }//End Catch }// End for
	 * 
	 * }//End uploadFormPost
	 */

	//항상 로그인 된 상태에서만 가용
	//파일 업로드
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

		// 파일 여러개->배열형태
		List<AttachFileDTO> list = new ArrayList<>();
		 String uploadFolder = "C:\\upload1";
		

		// getFolder 메서드로 폴더 패턴 생성
		String uploadFolderPath = getFolder();
		// 폴더 만들기(부모,자식)
		File uploadPath = new File(uploadFolder, uploadFolderPath);

		// 업로드 폴더가 존재하지 않는다면 만들어준다.
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO dto = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();
			// ie의 경우 전체 파일 경로가 전송됨으로 \\까지 제외한 문자열이 실제 파일이름으로 저장
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("오직 파일 이름만:  " + uploadFileName);
			dto.setFileName(uploadFileName);

			// 중복방지 uuid 적용==========================================================
			UUID uuid = UUID.randomUUID();
			// [uuid랜덤값_파일 본래이름]
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				// 브라우저에서 업로드된 파일을 서버내에 생성(부모,자식)
				File saveFile = new File(uploadPath, uploadFileName);
				// 브라우저에서 업로드받은 파일을 서버의 파일 도착지인 saveFile로 transferTo 을 이용해 전송
				multipartFile.transferTo(saveFile);

				dto.setUuid(uuid.toString());
				dto.setUploadPath(uploadFolderPath);

				// 업로드된 파일이 이미지이면 썸네일 파일하나를 더 생성해준다.
				if (checkImageType(saveFile)) {
					dto.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));

					// Thumbnailator이용 파일생성->파라미터값으로 weight height 지정 가능
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				list.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // End for
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	


	// 파일 업로드시 썸네일 이미지 보여주기
	// 문자열로 파일경로를 fileName으로 받고 byte[]로 이미지 데이터를 전송(http 헤더 메시지에 포함해서)
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFileImage(String fileName) {
		log.info("썸네일 보여줘야 되는 파일 이름: " + fileName);
		File file = new File("c:\\upload1\\" + fileName);
		
		log.info(file);
		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();

			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	  //파일 다운로드
	 //MIME 타입APPLICATION_OCTET_STREAM_VALUE << 다운로드 할 수 있는 타입
	  @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	  @ResponseBody
	  public ResponseEntity<Resource>downloadFile(@RequestHeader("User-Agent")String userAgent,String fileName){
		  log.info("다운로드 파일이름: "+fileName);
		  
		  Resource resource = new FileSystemResource("c:\\upoload1\\"+fileName);
		  
		  log.info(resource.exists());
		  //다운받아야 할 해당 파일이 존재하지 않을 시 Not Found 에러 코드 리턴
		  if(resource.exists()!= false) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		  log.info("resource: "+resource);
		  
		  String resourceName = resource.getFilename();
		  
		  //다운로드시 UUID 제거하고 본 파일 이름
		  String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		  log.info("uuid 제거 후 파일이름: " +resourceOriginalName);
		  
		  //HttpHeader 객체를 이용하여 다운로시 파일의 이름을 처리
		  HttpHeaders headers = new HttpHeaders();
		  try {
			  String downloadName = null;
			  
			  //브라우저 종류에 따른 인코딩 처리
			  //브라우저가 IE일경우, ie는 파일이름이 한글파일 자체를 다운처리가 안되기 때문에 한글 인코딩 필요
			  if(userAgent.contains("Trident")) {
				 log.info("ie 브라우저");
				 downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
			  ////브라우저가 엣지일 경우	  
			  }else if(userAgent.contains("Edge")) {
				 log.info("엣지 브라우저");
				 downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				 log.info("엣지 브라우저 다운로드 파일이름: "+downloadName);
			  }else {
				  log.info("크롬 및 다른 브라우저");
				  downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			  }
			  
			  log.info("최종 다운로드 파일 이름: "+downloadName);
			  //Content-Disposition << 파일 이름이 한글일 경우 저장시 깨지는 문제 해결
			  headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		  return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	  }
	  
	  
	//파일 다운로드
		 //MIME 타입APPLICATION_OCTET_STREAM_VALUE << 다운로드 할 수 있는 타입
		  @GetMapping(value = "/download1", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		  @ResponseBody
		  public ResponseEntity<Resource>downloadFile1(@RequestHeader("User-Agent")String userAgent,String fileName){
			  log.info("다운로드 파일이름: "+fileName);
			  
			  Resource resource = new FileSystemResource("c:\\upoload1\\"+fileName);
			
			  log.info("resource: "+resource);
			  log.info(resource.exists());
			  if(resource.exists()!=false) {
				  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  }
			  
			  String resourceName = resource.getFilename();
			  log.info("resourceName: "+resourceName);
			  HttpHeaders headers = new HttpHeaders();
		
			  try {
				  String downloadName = null;
				  //브라우저가 IE일경우, ie는 파일이름이 한글파일 자체를 다운처리가 안되기 때문에 한글 인코딩 필요
				  if(userAgent.contains("Trident")) {
					 log.info("ie 브라우저");
					 downloadName = URLEncoder.encode(resourceName,"UTF-8").replaceAll("\\+", " ");
				  ////브라우저가 엣지일 경우	  
				  }else if(userAgent.contains("Edge")) {
					 log.info("엣지 브라우저");
					 downloadName = URLEncoder.encode(resourceName,"UTF-8");
					 log.info("엣지 브라우저 다운로드 파일이름: "+downloadName);
				  }else {
					  log.info("크롬 및 다른 브라우저");
					  downloadName = new String(resourceName.getBytes("UTF-8"),"ISO-8859-1");
				  }
				  log.info("downloadName: "+downloadName);
				  //Content-Disposition << 파일 이름이 한글일 경우 저장시 깨지는 문제 해결
				  headers.add("Content-Disposition", "attachment; filename=" + new String(downloadName.getBytes("UTF-8"), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			  return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		  }
	  
	  
	  
	  
	 @PreAuthorize("isAuthenticated()")
	 @PostMapping(value="/deleteFile" ,produces = "text/json; charset=utf-8")
	 @ResponseBody
	 public ResponseEntity<String>deleteFile(String fileName, String type){
		 log.info("파일 삭제 대상: " +fileName);
		 File file;
		 
		 //일반 파일의 경우 
		 try {
			 //받은 파일명의 주소를 디코드하여 진짜 주소명을 얻는다음
			file = new File("c:\\upload1\\"+URLDecoder.decode(fileName,"UTF-8"));
			 
			//삭제
			file.delete();
		//이미지 파일의경우	
			if(type.equals("image")) {
				
				//썸네일 이미지까지 삭제  -> _S 을 없애준다.
				String FileName = file.getAbsolutePath().replace("s_", "");
				log.info("FileName: "+FileName);
				file = new File(FileName);
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 return new ResponseEntity<String>("삭제되었습니다",HttpStatus.OK);
	 }
	 

	 
	 
	 
}
