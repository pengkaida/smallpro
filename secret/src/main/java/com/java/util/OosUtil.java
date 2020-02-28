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
	// ���������˺�AccessKeyӵ������API�ķ���Ȩ�ޣ����պܸߡ�ǿ�ҽ�����������ʹ��RAM�˺Ž���API���ʻ��ճ���ά�����¼
	// https://ram.console.aliyun.com ����RAM�˺š�
	String accessKeyId = "";
	String accessKeySecret = "";
	int statu=-1;

	public void oosUpload(MultipartFile file, String name,OSS ossClient) throws OSSException, ClientException, IOException {
		
		if(ossClient== null) {
			statu=0;
			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		}
		// �ϴ��ļ���<yourLocalFile>�ɱ����ļ�·�����ļ���������׺��ɣ�����/users/local/myfile.txt��
		ossClient.putObject("pkd-picture", name + ".jpg", file.getInputStream());
		// �ر�OSSClient
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
		// ɾ���ļ�
		ossClient.deleteObject("pkd-picture", name + ".jpg");
		// �ر�OSSClient��
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
		System.out.println("��ʼ����");
		// �����ļ���
		CopyObjectResult result = ossClient.copyObject(sourceBucketName, sourceObjectName+".jpg", destinationBucketName,
				destinationObjectName+".jpg");
		System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());

		// �ر�OSSClient��
		if(statu==0) {
			ossClient.shutdown();
		}
		statu=-1;
	}

	/**
	 * @param uploadfile            ��Ҫ���ĵ��ļ�
	 * @param sourceObjectName      ԭ��������
	 * @param destinationObjectName �޸ĺ������
	 */
	public void oosUpadte(MultipartFile uploadfile, String sourceObjectName, String destinationObjectName)
			throws Exception {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		if (destinationObjectName != null && !destinationObjectName.equals("") && uploadfile != null) {
			System.out.println("��");
			oosUpload(uploadfile, destinationObjectName,ossClient);
			oosDelete(sourceObjectName,ossClient);
		}
		
		if (destinationObjectName != null && !destinationObjectName.equals("") && uploadfile == null) {
			System.out.println("��");
			oosCopy(sourceObjectName, destinationObjectName,ossClient);
			System.out.println("COPY�ɹ�");
			oosDelete(sourceObjectName,ossClient);
		}
		if (uploadfile != null && (destinationObjectName == null || destinationObjectName.equals(""))) {
			System.out.println("��");
			oosDelete(sourceObjectName,ossClient);
			oosUpload(uploadfile, sourceObjectName,ossClient);
		}
		ossClient.shutdown();
	}
}
