package com.java.util;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CopyObjectResult;

@Component
public class OosUtil {

	String endpoint = "";
	// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录
	// https://ram.console.aliyun.com 创建RAM账号。
	String accessKeyId = "";
	String accessKeySecret = "";
	int statu=-1;

	public void oosUpload(MultipartFile file, String name,OSS ossClient) throws OSSException, ClientException, IOException {
		
		if(ossClient== null) {
			statu=0;
			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		}
		// 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
		ossClient.putObject("pkd-picture", name + ".jpg", file.getInputStream());
		// 关闭OSSClient
		if(statu==0) {
			ossClient.shutdown();
		}
		statu=-1;
	}

	public void oosDelete(String name,OSS ossClient) {
		if(ossClient== null) {
			statu=0;
			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		}
		// 删除文件
		ossClient.deleteObject("pkd-picture", name + ".jpg");
		// 关闭OSSClient。
		if(statu==0) {
			ossClient.shutdown();
		}
		statu=-1;
	}

	public void oosCopy(String sourceObjectName, String destinationObjectName,OSS ossClient) {
		if(ossClient== null) {
			statu=0;
			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		}
		String sourceBucketName = "pkd-picture";
		String destinationBucketName = "pkd-picture";
		System.out.println("开始拷贝");
		// 拷贝文件。
		CopyObjectResult result = ossClient.copyObject(sourceBucketName, sourceObjectName+".jpg", destinationBucketName,
				destinationObjectName+".jpg");
		System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());

		// 关闭OSSClient。
		if(statu==0) {
			ossClient.shutdown();
		}
		statu=-1;
	}

	/**
	 * @param uploadfile            需要更改的文件
	 * @param sourceObjectName      原本的名字
	 * @param destinationObjectName 修改后的名字
	 */
	public void oosUpadte(MultipartFile uploadfile, String sourceObjectName, String destinationObjectName)
			throws Exception {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		if (destinationObjectName != null && !destinationObjectName.equals("") && uploadfile != null) {
			System.out.println("到");
			oosUpload(uploadfile, destinationObjectName,ossClient);
			oosDelete(sourceObjectName,ossClient);
		}
		
		if (destinationObjectName != null && !destinationObjectName.equals("") && uploadfile == null) {
			System.out.println("这");
			oosCopy(sourceObjectName, destinationObjectName,ossClient);
			System.out.println("COPY成功");
			oosDelete(sourceObjectName,ossClient);
		}
		if (uploadfile != null && (destinationObjectName == null || destinationObjectName.equals(""))) {
			System.out.println("了");
			oosDelete(sourceObjectName,ossClient);
			oosUpload(uploadfile, sourceObjectName,ossClient);
		}
		ossClient.shutdown();
	}
}
