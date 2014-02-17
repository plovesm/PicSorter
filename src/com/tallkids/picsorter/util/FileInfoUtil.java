/**
 * 
 */
package com.tallkids.picsorter.util;

import java.io.File;
import java.util.Date;

/**
 * @author Paul
 *
 */
public class FileInfoUtil {

	public Date checkFileCreationDate(File file){
		
		long lastModifiedDate = file.lastModified();
		Date creationDate = new Date(lastModifiedDate);
		
		return creationDate;
	}

	public String checkFileName(File file) {
		
		String name = file.getName();
		
		return name;
	}
}
