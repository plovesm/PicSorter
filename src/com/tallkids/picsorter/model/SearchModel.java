/**
 * 
 */
package com.tallkids.picsorter.model;

import java.io.File;
import java.io.IOException;

/**
 * @author ott1982
 *
 */
public class SearchModel
{
	
	File		sourceFile;
	File		targetFile;
	File		currentActiveFile;
	int			totalSourceFiles;
	int			currentFileIndex;
	int			totalMissingFiles;
	
	
	/**
	 * @return the sourceFile
	 */
	public File getSourceFile() {
		return sourceFile;
	}
	/**
	 * @param sourceFile the sourceFile to set
	 */
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}
	/**
	 * @return the targetFile
	 */
	public File getTargetFile() {
		return targetFile;
	}
	/**
	 * @param targetFile the targetFile to set
	 */
	public void setTargetFile(File targetFile) {
		this.targetFile = targetFile;
	}
	/**
	 * @return the currentActiveFile
	 */
	public File getCurrentActiveFile() {
		return currentActiveFile;
	}
	/**
	 * @param currentActiveFile the currentActiveFile to set
	 */
	public void setCurrentActiveFile(File currentActiveFile) {
		this.currentActiveFile = currentActiveFile;
	}
	/**
	 * @return the sourceDir
	 */
	public String getSourceDir() {
		
		String dirPath = "";
		
		try 
		{
			dirPath = (getSourceFile() != null) ? getSourceFile().getCanonicalPath() : "";
		} 
		catch (IOException e) 
		{
			// Swallow exception and print stack trace
			e.printStackTrace();
		}
		
		return dirPath;
	}
	
	/**
	 * @return the targetDir
	 */
	public String getTargetDir() {
		
		String dirPath = "";
		
		try 
		{
			dirPath = (getTargetFile() != null) ? getTargetFile().getCanonicalPath() : "";
		} 
		catch (IOException e) 
		{
			// Swallow exception and print stack trace
			e.printStackTrace();
		}
		
		return dirPath;
	}
		
	public int getTotalSourceFiles() {
		return totalSourceFiles;
	}
	public void setTotalSourceFiles(int totalSourceFiles) {
		this.totalSourceFiles = totalSourceFiles;
	}
	/**
	 * @return the currentFileIndex
	 */
	public int getCurrentFileIndex() {
		return currentFileIndex;
	}
	/**
	 * @param currentFileIndex the currentFileIndex to set
	 */
	public void setCurrentFileIndex(int currentFileIndex) {
		this.currentFileIndex = currentFileIndex;
	}
	/**
	 * @return the totalMissingFiles
	 */
	public int getTotalMissingFiles() {
		return totalMissingFiles;
	}
	/**
	 * @param totalMissingFiles the totalMissingFiles to set
	 */
	public void setTotalMissingFiles(int totalMissingFiles) {
		this.totalMissingFiles = totalMissingFiles;
	}

	public SearchModel() {
		super();
	}
	
	
	
}
