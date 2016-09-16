package com.hiekn.bridgehealth.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import com.sun.jersey.core.header.FormDataContentDisposition;

public interface IFileService {
	
	public String upload(InputStream fileIn, FormDataContentDisposition fileInfo, String uploadedFileLocation,String finalFileName) throws Exception;

	public byte [] downloadFiles(File file) throws IOException;
}
