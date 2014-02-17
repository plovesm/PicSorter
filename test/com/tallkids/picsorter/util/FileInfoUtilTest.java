/**
 * 
 */
package com.tallkids.picsorter.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;

import org.junit.Test;

/**
 * @author Paul
 *
 */
public class FileInfoUtilTest {
	
	FileInfoUtil fileInfoUtil = new FileInfoUtil();
	
	@Test
	public void testCheckFileLastModifiedDate(){
		
		
		File testFile = getTestFile();
		
		Date creationDate = fileInfoUtil.checkFileLastModifiedDate(testFile);
		long testDateLong = 1247549551658L;
		Date testDate = new Date(testDateLong);
		
		assertNotNull(creationDate);
		assertEquals("Test Date is not equal", testDate, creationDate);
		
	}
	
	@Test
	public void testCheckFileName(){
		
		
		File testFile = getTestFile();
		
		String fileName = fileInfoUtil.checkFileName(testFile);
		
		assertNotNull(fileName);
		assertEquals("Name is not equal", "Desert.jpg", fileName);
		
	}
	
	@Test
	public void testCheckFolderName(){
		
		
		File testFile = getTestFile();
		
		String folderName = fileInfoUtil.checkFolderName(testFile);
		
		assertNotNull(folderName);
		assertEquals("Name is not equal", "07-July", folderName);
		
	}
	
	@Test
	public void testCreateFolder(){
		
		
		//TODO test
		
	}
	
	@Test
	public void testCopyFileToFolder(){
		
		
		//TODO test
		
	}
	
	//TODO Add tests for null, no date, boundary
	
	@SuppressWarnings("unused")
	private File getTestFile()
	{
		String filename = "Desert.jpg";
		String finalfile = "";
		String homeDir = System.getProperty("user.home");
		String currDir = System.getProperty("user.dir");
		String workingDir = homeDir + File.separator + "Desktop" + File.separator + "workspace";
		
		finalfile = currDir + File.separator + filename;
		
		File file = new File(finalfile);
		
		return file;
	}
}
