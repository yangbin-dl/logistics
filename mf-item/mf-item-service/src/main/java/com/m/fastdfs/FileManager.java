package com.m.fastdfs;

import com.m.fastdfs.common.MyException;
import com.m.fastdfs.common.NameValuePair;
import com.m.fastdfs.sources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileManager implements Config {
	static Logger logger = LoggerFactory.getLogger(FileManager.class);

	private static FileManager instance = new FileManager();

	private static TrackerClient trackerClient = null;    
	private static String DEFAULT_GROUP_NAME;

	static {
		try {
			String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
			String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
			logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);
			ClientGlobal.init(fdfsClientConfigFilePath);
			DEFAULT_GROUP_NAME = ClientGlobal.getDEFAULT_GROUP_NAME();
		} catch (Exception e) {
			logger.error("init error", e);
		}
	}

	public static TrackerServer getTrackerServer(){    
		TrackerServer trackerServer = null;    
		try {
			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trackerServer;    
	}    

	public static StorageClient getStorageClient() throws IOException{    
		StorageServer storageServer = new StorageServer("120.201.127.207",22122,3);
		StorageClient storageClient = new StorageClient(getTrackerServer(), storageServer);
		return storageClient;    
	}    

	public static FileManager getOnly() {
		return instance;
	}

	/** 
	 * @Title: FileManager
	 * @throws IOException 
	 * @author mengfanzhu
	 * @throws 
	 */
	public String upload(String fileName,String extension, InputStream is) throws IOException {
		byte[] file_buff = null;
		if (is != null) {
			int len = is.available();
			file_buff = new byte[len];
			is.read(file_buff);
		}
		FastDFSFile file = new FastDFSFile(fileName, file_buff, extension);
		return upload(file);
	}

	public byte[] download(String group_name, String remote_filename) throws IOException, MyException
	{
		final long file_offset = 0;
		final long download_bytes = 0;

		return getStorageClient().download_file(group_name, remote_filename, file_offset, download_bytes);
	}

	public String upload(FastDFSFile file) throws IOException {
		logger.info("File Name: " + file.getFileName() + "  File Length: " + file.getContent().length);

		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", "120");
		meta_list[1] = new NameValuePair("heigth", "120");
		meta_list[2] = new NameValuePair("author", "Emart");

		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		try {
			uploadResults = getStorageClient().upload_file(file.getContent(),
					file.getExt(), meta_list);
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file: " + file.getFileName(),e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file: "+ file.getFileName(), e);
			e.printStackTrace();
		}
		logger.info("upload_file time used: "+ (System.currentTimeMillis() - startTime) + " ms");

		if (uploadResults == null) {
			logger.error("upload file fail, error code: "+ getStorageClient().getErrorCode());
		}

		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String fileAbsolutePath = PROTOCOL
				+ getTrackerServer().getInetSocketAddress().getHostName() + ":"
				+ TRACKER_NGNIX_PORT + SEPARATOR + groupName + SEPARATOR
				+ remoteFileName;

		logger.info("upload file successfully!!!  " + "group_name: "
				+ groupName + ", remoteFileName:" + " " + remoteFileName);

		return fileAbsolutePath;
	}

	/** 
	 * @Title: FileManager
	 * @param groupName 
	 * @param remoteFileName
	 * @return 
	 * @author mengfanzhu
	 * @throws 
	 */
	public FileInfo getFile(String groupName, String remoteFileName) {
		try {
			return getStorageClient().get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}
	/** 
	 * @Title: FileManager
	 * @param remoteFileName
	 * @return 
	 * @author mengfanzhu
	 * @throws 
	 */
	public FileInfo getFile(String remoteFileName) {
		return getFile(DEFAULT_GROUP_NAME, remoteFileName);
	}

	public void deleteFile(String groupName, String remoteFileName) throws Exception {
		getStorageClient().delete_file(groupName, remoteFileName);
	}

	/** 
	 * @Title: FileManager
	 * @param remoteFileName
	 * @throws Exception 
	 * @author mengfanzhu
	 * @throws 
	 */
	public void deleteFile(String remoteFileName) throws Exception {
		deleteFile(DEFAULT_GROUP_NAME, remoteFileName);
	}

	public static StorageServer[] getStoreStorages(String groupName) throws IOException {
		return trackerClient.getStoreStorages(getTrackerServer(), groupName);
	}

	public static ServerInfo[] getFetchStorages(String groupName,String remoteFileName) throws IOException {
		return trackerClient.getFetchStorages(getTrackerServer(), groupName,remoteFileName);
	}
}
