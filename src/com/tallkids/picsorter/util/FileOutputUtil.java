/**
 * 
 */
package com.tallkids.picsorter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ott1982
 *
 */
public class FileOutputUtil {
	
	public static File createOutputFile(String filename) {
		String currDir = System.getProperty("user.dir");
		
		return createOutputFile(currDir, filename, true);
	}
	
	public static File createOutputFile(String filename, boolean overwriteExisting) {
		String currDir = System.getProperty("user.dir");
		
		return createOutputFile(currDir, filename, overwriteExisting);
	}
	
	public static File createOutputFile(String path, String filename, boolean overwriteExisting) {
		
		File newFile = new File(path + "\\" + filename);
		
		if(newFile.exists())
		{
			//File exists
			if(overwriteExisting)
			{
				newFile.delete();
				return createNewFile(newFile);
			}
			else
			{
				return newFile;
			}
		}
		else
		{
			return createNewFile(newFile);
		}
		
	}
	
	private static File createNewFile(File newFile){
		
		try 
		{
			if(newFile.createNewFile())
			{
				//Directory created successfully
				return newFile;
			}
			else
			{
				//file creation failed
				return null;
			}
		} 
		catch (IOException e) {
			return null;
		}		
	}
	
	public static boolean writeLineToFile(String newLine, File file) {
		boolean success = false;
		
		try 
		{
			 
			String content = newLine;

			// if file doesnt exists, then create it
			if (!file.exists()) {
				if(createNewFile(file) == null)
				{
					return false;
				}
				
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(content);
			bw.append("\n");
			bw.close();
			
			success = true;

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return success;
	}
}
