/**
 * 
 */
package com.tallkids.picsorter.util;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Paul
 *
 */
public class FileInfoUtil {

	public Date checkFileLastModifiedDate(File file){
		
		long lastModifiedDate = file.lastModified();
		Date creationDate = new Date(lastModifiedDate);
		
		return creationDate;
	}

	public String checkFileName(File file) {
		
		String name = file.getName();
		
		return name;
	}

	public String checkFolderName(File testFile) {
		
		
		String folderName = "";
		Date lastModifiedDate = checkFileLastModifiedDate(testFile);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastModifiedDate);
		int month = cal.get(Calendar.MONTH);
		
		DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        
        String monthName = months[month];
		
        int monthNum = month + 1;
        
        if(monthNum < 10)
        {
        	folderName = "0" + monthNum + "-" + monthName;
        }
        else
        {
        	folderName = monthNum + "-" + monthName;
        }
        
        
		return folderName;
	}
}
