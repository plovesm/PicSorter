/**
 * 
 */
package com.tallkids.picsorter.model;

/**
 * @author ott1982
 *
 */
public class SearchModel
{
	
	String sourceDir = "";
	String targetDir = "";
	/**
	 * @return the sourceDir
	 */
	public String getSourceDir() {
		return sourceDir;
	}
	/**
	 * @param sourceDir the sourceDir to set
	 */
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}
	/**
	 * @return the targetDir
	 */
	public String getTargetDir() {
		return targetDir;
	}
	/**
	 * @param targetDir the targetDir to set
	 */
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
	
	public SearchModel(String sourceDir, String targetDir) {
		super();
		this.sourceDir = sourceDir;
		this.targetDir = targetDir;
	}
	
	public SearchModel() {
		super();
	}
	
	
	
}
