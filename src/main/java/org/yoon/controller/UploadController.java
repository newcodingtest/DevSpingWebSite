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

	// ��/��/�� ���� ����
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

	// �̹��� Ÿ�� Ȯ�� �޼���
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
		log.info("���ε� ��");
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
	 * log.info("���ε� �����̸�:  "+multipartFile.getOriginalFilename());
	 * log.info("���ε� ����ũ��:  "+multipartFile.getSize());
	 * 
	 * String uploadFileName = multipartFile.getOriginalFilename(); //�ߺ����� uuid
	 * ����========================================================== UUID uuid =
	 * UUID.randomUUID(); //[uuid������_���� �����̸�] uploadFileName =
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
	 * //���ε�� ������ �޾Ƽ� ������ġ�� ����
	 * 
	 * @PostMapping("/uploadFormAction") public void uploadFormPost(MultipartFile[]
	 * uploadFile, Model model) { String uploadFolder = "C:\\upload1";
	 * 
	 * for(MultipartFile multipartFile : uploadFile) {
	 * log.info("===========================");
	 * log.info("���ε� ���� �̸�:  "+multipartFile.getOriginalFilename());
	 * log.info("���ε� ���� ũ��:  "+multipartFile.getSize());
	 * 
	 * //���� ����(�θ�,�ڽ�) File saveFile = new File(uploadFolder,
	 * multipartFile.getOriginalFilename()); try { //���������� ���ε���� ������ ������ ���� ��������
	 * saveFile�� transferTo �� �̿��� ���� multipartFile.transferTo(saveFile); } catch
	 * (Exception e) { log.error(e.getMessage()); }//End Catch }// End for
	 * 
	 * }//End uploadFormPost
	 */

	//�׻� �α��� �� ���¿����� ����
	//���� ���ε�
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

		// ���� ������->�迭����
		List<AttachFileDTO> list = new ArrayList<>();
		 String uploadFolder = "C:\\upload1";
		

		// getFolder �޼���� ���� ���� ����
		String uploadFolderPath = getFolder();
		// ���� �����(�θ�,�ڽ�)
		File uploadPath = new File(uploadFolder, uploadFolderPath);

		// ���ε� ������ �������� �ʴ´ٸ� ������ش�.
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO dto = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();
			// ie�� ��� ��ü ���� ��ΰ� ���۵����� \\���� ������ ���ڿ��� ���� �����̸����� ����
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("���� ���� �̸���:  " + uploadFileName);
			dto.setFileName(uploadFileName);

			// �ߺ����� uuid ����==========================================================
			UUID uuid = UUID.randomUUID();
			// [uuid������_���� �����̸�]
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				// ���������� ���ε�� ������ �������� ����(�θ�,�ڽ�)
				File saveFile = new File(uploadPath, uploadFileName);
				// ���������� ���ε���� ������ ������ ���� �������� saveFile�� transferTo �� �̿��� ����
				multipartFile.transferTo(saveFile);

				dto.setUuid(uuid.toString());
				dto.setUploadPath(uploadFolderPath);

				// ���ε�� ������ �̹����̸� ����� �����ϳ��� �� �������ش�.
				if (checkImageType(saveFile)) {
					dto.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));

					// Thumbnailator�̿� ���ϻ���->�Ķ���Ͱ����� weight height ���� ����
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
	


	// ���� ���ε�� ����� �̹��� �����ֱ�
	// ���ڿ��� ���ϰ�θ� fileName���� �ް� byte[]�� �̹��� �����͸� ����(http ��� �޽����� �����ؼ�)
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFileImage(String fileName) {
		log.info("����� ������� �Ǵ� ���� �̸�: " + fileName);
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

	
	  //���� �ٿ�ε�
	 //MIME Ÿ��APPLICATION_OCTET_STREAM_VALUE << �ٿ�ε� �� �� �ִ� Ÿ��
	  @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	  @ResponseBody
	  public ResponseEntity<Resource>downloadFile(@RequestHeader("User-Agent")String userAgent,String fileName){
		  log.info("�ٿ�ε� �����̸�: "+fileName);
		  
		  Resource resource = new FileSystemResource("c:\\upoload1\\"+fileName);
		  
		  log.info(resource.exists());
		  //�ٿ�޾ƾ� �� �ش� ������ �������� ���� �� Not Found ���� �ڵ� ����
		  if(resource.exists()!= false) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		  log.info("resource: "+resource);
		  
		  String resourceName = resource.getFilename();
		  
		  //�ٿ�ε�� UUID �����ϰ� �� ���� �̸�
		  String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		  log.info("uuid ���� �� �����̸�: " +resourceOriginalName);
		  
		  //HttpHeader ��ü�� �̿��Ͽ� �ٿ�ν� ������ �̸��� ó��
		  HttpHeaders headers = new HttpHeaders();
		  try {
			  String downloadName = null;
			  
			  //������ ������ ���� ���ڵ� ó��
			  //�������� IE�ϰ��, ie�� �����̸��� �ѱ����� ��ü�� �ٿ�ó���� �ȵǱ� ������ �ѱ� ���ڵ� �ʿ�
			  if(userAgent.contains("Trident")) {
				 log.info("ie ������");
				 downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
			  ////�������� ������ ���	  
			  }else if(userAgent.contains("Edge")) {
				 log.info("���� ������");
				 downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				 log.info("���� ������ �ٿ�ε� �����̸�: "+downloadName);
			  }else {
				  log.info("ũ�� �� �ٸ� ������");
				  downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			  }
			  
			  log.info("���� �ٿ�ε� ���� �̸�: "+downloadName);
			  //Content-Disposition << ���� �̸��� �ѱ��� ��� ����� ������ ���� �ذ�
			  headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		  return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	  }
	  
	  
	//���� �ٿ�ε�
		 //MIME Ÿ��APPLICATION_OCTET_STREAM_VALUE << �ٿ�ε� �� �� �ִ� Ÿ��
		  @GetMapping(value = "/download1", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		  @ResponseBody
		  public ResponseEntity<Resource>downloadFile1(@RequestHeader("User-Agent")String userAgent,String fileName){
			  log.info("�ٿ�ε� �����̸�: "+fileName);
			  
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
				  //�������� IE�ϰ��, ie�� �����̸��� �ѱ����� ��ü�� �ٿ�ó���� �ȵǱ� ������ �ѱ� ���ڵ� �ʿ�
				  if(userAgent.contains("Trident")) {
					 log.info("ie ������");
					 downloadName = URLEncoder.encode(resourceName,"UTF-8").replaceAll("\\+", " ");
				  ////�������� ������ ���	  
				  }else if(userAgent.contains("Edge")) {
					 log.info("���� ������");
					 downloadName = URLEncoder.encode(resourceName,"UTF-8");
					 log.info("���� ������ �ٿ�ε� �����̸�: "+downloadName);
				  }else {
					  log.info("ũ�� �� �ٸ� ������");
					  downloadName = new String(resourceName.getBytes("UTF-8"),"ISO-8859-1");
				  }
				  log.info("downloadName: "+downloadName);
				  //Content-Disposition << ���� �̸��� �ѱ��� ��� ����� ������ ���� �ذ�
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
		 log.info("���� ���� ���: " +fileName);
		 File file;
		 
		 //�Ϲ� ������ ��� 
		 try {
			 //���� ���ϸ��� �ּҸ� ���ڵ��Ͽ� ��¥ �ּҸ��� ��´���
			file = new File("c:\\upload1\\"+URLDecoder.decode(fileName,"UTF-8"));
			 
			//����
			file.delete();
		//�̹��� �����ǰ��	
			if(type.equals("image")) {
				
				//����� �̹������� ����  -> _S �� �����ش�.
				String FileName = file.getAbsolutePath().replace("s_", "");
				log.info("FileName: "+FileName);
				file = new File(FileName);
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 return new ResponseEntity<String>("�����Ǿ����ϴ�",HttpStatus.OK);
	 }
	 

	 
	 
	 
}
