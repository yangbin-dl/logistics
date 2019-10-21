package com.mallfe.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FileUploadingUtil {  

	public static Map<String, String> upload(String filePath, Map<String, MultipartFile> files) throws IOException {  
		File file = new File(filePath);  
		if (!file.exists()) {  
			file.mkdirs();  
		}  
		Map<String, String> result = new HashMap<String, String>();  
		Iterator<Entry<String, MultipartFile>> iter = files.entrySet().iterator();  
		while (iter.hasNext()) {  
			MultipartFile aFile = iter.next().getValue();  
			if (aFile.getSize() != 0 && !"".equals(aFile.getName())) {  
				result.put(aFile.getOriginalFilename(), uploadFile(filePath,aFile));  
			}  
		}  
		return result;  
	}  

	public void download(HttpServletResponse response, String filePath, String fileName) throws Exception{  
		File file = new File(filePath);  
		if(file.exists()){  
			response.reset();  
			response.setCharacterEncoding("utf-8");  
			response.setContentType("text/html;charset=utf-8");  
			response.addHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");  
			response.addHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"),"ISO8859-1"));  
			int fileLength = (int)file.length();  
			response.setContentLength(fileLength);  

			if(fileLength!=0){  
				InputStream inStream = new FileInputStream(file);  
				byte[] buf = new byte[4096];  

				ServletOutputStream servletOS = response.getOutputStream();  
				int readLength;  

				while((readLength = inStream.read(buf))!=-1){  
					servletOS.write(buf, 0, readLength);  
				}  
				inStream.close();  
				servletOS.flush();
				servletOS.close();
			}  
		}else{  
			response.setContentType("text/html;charset=utf-8");  
			PrintWriter out = response.getWriter();  
			out.println(fileName + "error");  
		}  
	} 

	private static String uploadFile(String fielPath,MultipartFile aFile) throws IOException {  
		String filePath = initFilePath(fielPath,aFile.getOriginalFilename());  
		try {  
			write(aFile.getInputStream(), new FileOutputStream(filePath));  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		}  
		return filePath;  
	}  

	private static void write(InputStream in, OutputStream out) throws IOException {  
		try {  
			byte[] buffer = new byte[1024];  
			int bytesRead = -1;  
			while ((bytesRead = in.read(buffer)) != -1) {  
				out.write(buffer, 0, bytesRead);  
			}  
			out.flush();  
		} finally {  
			try {  
				in.close();  
				out.close();  
			} catch (IOException ex) {  
			}  
		}  
	}  

	public static Map<String, String> getFileMap(String filePath) {  
		Map<String, String> map = new HashMap<String, String>();  
		File[] files = new File(filePath).listFiles();  
		if (files != null) {  
			for (File file : files) {  
				if (file.isDirectory()) {  
					File[] files2 = file.listFiles();  
					if (files2 != null) {  
						for (File file2 : files2) {  
							String name = file2.getName();  
							map.put(file2.getParentFile().getName() + "/" + name,  
									name.substring(name.lastIndexOf("_") + 1));  
						}  
					}  
				}  
			}  
		}  
		return map;  
	}  

	private static String initFilePath(String filePath, String name) {  
		String dir = getFileDir(name) + "";  
		File file = new File(filePath + dir);  
		if (!file.exists()) {  
			file.mkdirs();  
		}  
		Long num = new Date().getTime();  
		Double d = Math.random() * num;  
		return (file.getPath() + "/" + num + d.longValue() + "_" + name).replaceAll(" ", "-");  
	}  

	private static int getFileDir(String name) {  
		return name.hashCode() & 0xf;  
	}  
}  

