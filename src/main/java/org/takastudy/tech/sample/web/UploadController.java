package org.takastudy.tech.sample.web;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RestController
public class UploadController {
	
	private static final String BUCKET_NAME = "cloud-btc";
	private static final String KEY_DIR = "org_images/";
	private static final String TMP_FILE = "C:/tmp";
	
	 AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

	@RequestMapping(value="/upload" ,produces = "application/json")
	public Response uploadFile(@RequestParam("files") MultipartFile files){
		Response res = new Response();
		
		if(files.isEmpty()){
			res.status = "error";
			res.errorStatus = "Error No File";
			return res;
		}
		
		String filename = files.getOriginalFilename();
		
		File uploadFile = new File(TMP_FILE,filename);
		try {
			files.transferTo(uploadFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			res.status = "error";
			res.errorStatus = ": UploadFile write LocalFile.";
			return res;
			
		}
		
		try{
			s3client.putObject(new PutObjectRequest(
	                BUCKET_NAME, KEY_DIR+filename, uploadFile));			
		}catch(AmazonServiceException | IllegalArgumentException ae){
			ae.printStackTrace();
			
			res.status = "error";
			res.errorStatus = ": Amazon S3 write Error.";
			return res;
		}

		
		res.status = "success";
		res.fileList.add(filename);
		
		
		
		return res;
	}
	
	
}
