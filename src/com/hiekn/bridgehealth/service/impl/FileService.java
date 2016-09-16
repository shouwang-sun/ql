package com.hiekn.bridgehealth.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.service.IFileService;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Service("fileService")
public class FileService implements IFileService {

	@Override
	public String upload(InputStream fileIn,FormDataContentDisposition fileInfo, String uploadedFileLocation, String finalFileName)throws Exception {
		OutputStream out = null;
		String fileName = "";
		try {
			byte[] fileByte = new byte[1024];
			int len = 0;
			out = new FileOutputStream(new File(uploadedFileLocation + "/" + finalFileName));
			while ((len = fileIn.read(fileByte)) != -1) {
				out.write(fileByte, 0, len);
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			System.err.println(e);
		}finally{
			if (out != null) {
				out.close();
			}
			if (fileIn != null) {
				fileIn.close();
			}
		}
		
		if(finalFileName!=null && !"".equals(finalFileName.trim())){
			fileName = finalFileName;
		}
		else{
			fileName = fileInfo.getFileName();
			fileName = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
		}
		return finalFileName;
	}
	
	
	@Override
	public byte[] downloadFiles(File file) throws IOException{
		byte[] buffer = null;
		buffer = null;
		FileInputStream fis = new FileInputStream(file);  
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
		byte[] b = new byte[1000];  
		int n;  
		while ((n = fis.read(b)) != -1) {  
			bos.write(b, 0, n);  
		}  
		fis.close();  
		bos.close();  
		buffer = bos.toByteArray();
		return buffer;
	}
}
