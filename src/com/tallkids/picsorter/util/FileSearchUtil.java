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

import com.tallkids.picsorter.model.SearchModel;

/**
 * @author ott1982
 *
 */
public class FileSearchUtil {
	
	private final static int BLOCK_SIZE = 65536; // Used for getting the binary comparison
	
	private static boolean checkForFileMatch(File activeFile, String path) {

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
    
    private static void checkIfFilesAreBackedUp(SearchModel searchModel) 
    {
        // First check searchModel and make sure source dir is available
    	if(searchModel != null && searchModel.getSourceDir() != null)
    	{
	    	// Establish the starting point
	    	File startingPoint = (searchModel.getCurrentActiveFile() != null) ? 
	    								searchModel.getCurrentActiveFile() : searchModel.getSourceFile();
	    									
	    	if(startingPoint.exists())
	    	{
	    		System.out.println("Path to search: " + startingPoint.getAbsolutePath());
		        
	    		File[] list = startingPoint.listFiles();
		         
		        if (searchModel.getTargetDir() != null && 
		        		!searchModel.getTargetDir().isEmpty() && 
		        		list != null)
		        {
		        	System.out.println("Is Directory? " + startingPoint.isDirectory());
		        	System.out.println("How many files: " + list.length);
		        	System.out.println("Actual number of Files: " + getTotalSourceFiles(startingPoint));
		    		
		        	for (File f : list) {
			            
		        		// Set the active file
		        		searchModel.setCurrentActiveFile(f);
		        		
		        		if (f.isDirectory()) {
			                checkIfFilesAreBackedUp(searchModel);
			                System.out.println("Checking Dir:" + f.getAbsoluteFile());
			            }
			            else {
			            	System.out.println("Checking File: " + f.getName());
			            	
			            	// Now iterate through the target directory
			            	if(!checkForFileMatch(f, searchModel.getTargetDir()))
			                {
			                	searchModel.setTotalMissingFiles(searchModel.getTotalMissingFiles() +1);
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
    }
    
    public static void searchDirectory(SearchModel searchModel)
	{
		
		System.out.println("Search Dir: " + searchModel.getSourceDir());
		System.out.println("Target Dir: " + searchModel.getTargetDir());
		
		checkIfFilesAreBackedUp(searchModel);        
		
	}
	
	/**
    * Compare binary files. Both files must be files (not directories) and exist.
    * 
    * @param first  - first file
    * @param second - second file
    * @return boolean - true if files are binery equal
    * @throws IOException - error in function
    */
    public static boolean isFileBinaryEqual(File first, File second) throws IOException
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

	/**
	 * Traverses the source tree to find all files that will need to be checked.
	 * 
	 * @param File startingPoint
	 * @return the totalSourceFiles
	 */	
	public static int getTotalSourceFiles(File startingPoint) {
		
		int fileCount = 0;
		
		// Iterate through the file list and count how many files are there
		List<File> fileList = traverseDirectory(startingPoint, null);
		fileCount = fileList.size();		
		System.out.println("File Count: " + fileCount);
		return fileCount;
	}
	
	/**
	 * Traverse the directory to create a list of just the files
	 * 
	 * @param startingPoint
	 * @param files - pre-seeded list to add to
	 * @return fileList - list of just the files in the directory
	 */
	public static List<File> traverseDirectory(File startingPoint, List<File> files)
	{
		
		File[] startingList = startingPoint.listFiles();
		List<File> fileList = (files != null) ? files : new ArrayList<File>() ;
		
		for (File f : startingList) {
            if (f.isDirectory()) {
            	System.out.println("Traverse Checking Dir:" + f.getAbsoluteFile());
            	traverseDirectory(f, fileList);
            }
            else {
            	System.out.println("Traverse Adding File: " + f.getName());
            	fileList.add(f);
            }
        }
		
		return fileList;
	}
}
