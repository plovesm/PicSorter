/**
 * 
 */
package com.tallkids.picsorter.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ott1982
 *
 */
public class FileSearch {
	
	private String 			targetDir = "";
	private String 			sourceDir = "";
	private int 			totalSourceFiles = 0;
	private int				currentSourceFile = 0;
	private int 			missingFileCount = 0;
    
	private final static int BLOCK_SIZE = 65536;
		
    private boolean checkForFileMatch(File activeFile, String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (activeFile != null && list != null)
        {
	        
	        for (File f : list) {
	            
	        	System.out.println("Checking Target: " + f.getAbsolutePath());
	        	
	        	if (f.isDirectory()) {
	            	checkForFileMatch(activeFile, f.getAbsolutePath());
	            }
	            else {
	            	try {
	            		if(f.getName().equals(activeFile.getName()) && 
	            				isFileBinaryEqual(f, activeFile))
						{
							return true;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
        }
        
        return false;
    }
    
    private void checkIfFilesAreBackedUp() 
    {
    	checkIfFilesAreBackedUp(getSourceDir());
    }
    
    private void checkIfFilesAreBackedUp(String path) 
    {
        System.out.println("Path to search: " + path);
    	
    	File startingPoint = new File(path);
    	
    	if(startingPoint.exists())
    	{
	        // Step 1: Get the total number of files in this directory tree
    		setTotalSourceFiles(getTotalSourceFiles(startingPoint));
    		
    		File[] list = startingPoint.listFiles();
	         
	        if (getTargetDir() != null && 
	        		!getTargetDir().isEmpty() && 
	        		list != null)
	        {
	        	System.out.println("Is Directory? " + startingPoint.isDirectory());
	        	System.out.println("How many files: " + list.length);
	        	System.out.println("Actual number of Files: " + getTotalSourceFiles());
	    		
	        	for (File f : list) {
		            if (f.isDirectory()) {
		                checkIfFilesAreBackedUp(f.getAbsolutePath());
		                System.out.println("Checking Dir:" + f.getAbsoluteFile());
		            }
		            else {
		            	System.out.println("Checking File: " + f.getName());
		            	
		            	boolean match = checkForFileMatch(f, getTargetDir());
		            	
		            	if(!match)
		                {
		                	setMissingFileCount(getMissingFileCount() + 1);
		            		System.out.println(f.getAbsolutePath());
		                }
		            }
		        }
	        }
    	}
    	else
    	{
    		System.out.println("Directory doesn't exist.");
    	}
    }
    
	public void searchDirectory(String searchDir, String targetDir)
	{
		
		setTargetDir(targetDir);
		setSourceDir(searchDir);
		
		System.out.println("Search Dir: " + getSourceDir());
		System.out.println("Target Dir: " + getTargetDir());
		
		checkIfFilesAreBackedUp();        
		
	}
	
	/**
    * Compare binary files. Both files must be files (not directories) and exist.
    * 
    * @param first  - first file
    * @param second - second file
    * @return boolean - true if files are binery equal
    * @throws IOException - error in function
    */
   public boolean isFileBinaryEqual(File first, File second) throws IOException
   {
      // TODO: Test: Missing test
      boolean retval = false;
      
      if ((first.exists()) && (second.exists()) 
         && (first.isFile()) && (second.isFile()))
      {
         if (first.getCanonicalPath().equals(second.getCanonicalPath()))
         {
            retval = true;
         }
         else
         {
            FileInputStream firstInput = null;
            FileInputStream secondInput = null;
            BufferedInputStream bufFirstInput = null;
            BufferedInputStream bufSecondInput = null;

            try
            {            
               firstInput = new FileInputStream(first); 
               secondInput = new FileInputStream(second);
               bufFirstInput = new BufferedInputStream(firstInput, BLOCK_SIZE); 
               bufSecondInput = new BufferedInputStream(secondInput, BLOCK_SIZE);
   
               int firstByte;
               int secondByte;
               
               while (true)
               {
                  firstByte = bufFirstInput.read();
                  secondByte = bufSecondInput.read();
                  if (firstByte != secondByte)
                  {
                     break;
                  }
                  if ((firstByte < 0) && (secondByte < 0))
                  {
                     retval = true;
                     break;
                  }
               }
            }
            finally
            {
               try
               {
                  if (bufFirstInput != null)
                  {
                     bufFirstInput.close();
                  }
               }
               finally
               {
                  if (bufSecondInput != null)
                  {
                     bufSecondInput.close();
                  }
               }
            }
         }
      }
      
      return retval;
   	}

	public int getMissingFileCount() {
		return missingFileCount;
	}

	public void setMissingFileCount(int missingFileCount) {
		this.missingFileCount = missingFileCount;
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

	/**
	 * @return the searchDir
	 */
	public String getSourceDir() {
		return sourceDir;
	}

	/**
	 * @param searchDir the searchDir to set
	 */
	public void setSourceDir(String sourcehDir) {
		this.sourceDir = sourcehDir;
	}

	/**
	 * Traverses the source tree to find all files that will need to be checked.
	 * 
	 * @param File startingPoint
	 * @return the totalSourceFiles
	 */	
	private int getTotalSourceFiles(File startingPoint) {
		
		int fileCount = 0;
		
		// Iterate through the file list and count how many files are there
		List<File> fileList = traverseDirectory(startingPoint, null);
		fileCount = fileList.size();		
		
		return fileCount;
	}
	
	/**
	 * @return the totalSourceFiles
	 */
	public int getTotalSourceFiles() {
		return totalSourceFiles;
	}

	/**
	 * @param totalSourceFiles the totalSourceFiles to set
	 */
	public void setTotalSourceFiles(int totalSourceFiles) {
		this.totalSourceFiles = totalSourceFiles;
	}

	/**
	 * @return the currentSourceFile
	 */
	public int getCurrentSourceFile() {
		return currentSourceFile;
	}

	/**
	 * @param currentSourceFile the currentSourceFile to set
	 */
	public void setCurrentSourceFile(int currentSourceFile) {
		this.currentSourceFile = currentSourceFile;
	}

	public List<File> traverseDirectory(File startingPoint, List<File> files)
	{
		
		File[] startingList = startingPoint.listFiles();
		List<File> fileList = (files != null) ? files : new ArrayList<File>() ;
		
		for (File f : startingList) {
            if (f.isDirectory()) {
            	traverseDirectory(f, fileList);
                System.out.println("Checking Dir:" + f.getAbsoluteFile());
            }
            else {
            	System.out.println("Adding File: " + f.getName());
            	
            	fileList.add(f);
            }
        }
		
		return fileList;
	}
}
