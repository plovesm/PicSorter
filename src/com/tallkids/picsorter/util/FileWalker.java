/**
 * 
 */
package com.tallkids.picsorter.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author ott1982
 *
 */
public class FileWalker {
	
	private String targetDir = "";
	private String sourceDir = "";
	private int missingFileCount = 0;
    
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
    	
    	File root = new File(path);
    	
    	System.out.println("Is Directory? " + root.isDirectory());
    	System.out.println("Exists?  " + root.exists());
    	
        File[] list = root.listFiles();
         
        if (getTargetDir() != null && 
        		!getTargetDir().isEmpty() && 
        		list != null)
        {
        	System.out.println("How many files: " + list.length);
        	
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
   public boolean isFileBinaryEqual(
      File first,
      File second
   ) throws IOException
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

	public static void main(String[] args) {
        FileWalker fw = new FileWalker();
        
        String homeDir = System.getProperty("user.home");
		String separator = System.getProperty("file.separator");
		
		String dir1 = homeDir + separator + "Desktop" + separator + "dir1";
		String dir2 = homeDir + separator + "Desktop" + separator + "dir2";
        
        /*
        String homeDir = System.getProperty("user.home");
        String searchDir = homeDir + "/Desktop"; //"/Pictures/iPhoto Library/Originals";
        // /Users/ott1982/Pictures/iPhoto Library/Originals
        System.out.println(searchDir);
        
        File root = new File(searchDir);
        
        System.out.println(searchDir.isEmpty());
        */
        fw.searchDirectory(dir1, dir2);
        System.out.println("Total missing files: " + fw.getMissingFileCount());
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

}
