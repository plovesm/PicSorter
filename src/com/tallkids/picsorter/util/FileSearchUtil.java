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

import javax.swing.JProgressBar;

import com.tallkids.picsorter.constants.AppConfigConstants;
import com.tallkids.picsorter.model.SearchModel;

/**
 * @author ott1982
 *
 */
public class FileSearchUtil {
	
	private final static int BLOCK_SIZE = 65536; // Used for getting the binary comparison

	/**
	 * Search a directory to see what files are not backed up
	 * 
	 * @param searchModel
	 */
    public static void searchDirectory(SearchModel searchModel)
	{
    	searchDirectory(searchModel, null, null);
	}
    
    /**
     * Search a directory to see what files are not backed up
     * 
     * @param searchModel
     * @param progressBar
     */
    public static void searchDirectory(SearchModel searchModel, JProgressBar progressBar)
	{
    	searchDirectory(searchModel, progressBar, null);
	}
    
    /**
     * Search a directory to see what files are not backed up
     * 
     * @param searchModel
     * @param searchMode
     */
    public static void searchDirectory(SearchModel searchModel, String searchMode)
	{
    	searchDirectory(searchModel, null, searchMode);
	}
    
    /**
     * Search a directory to see what files are not backed up
     * 
     * @param searchModel
     * @param progressBar
     * @param searchMode
     */
    public static void searchDirectory(SearchModel searchModel, JProgressBar progressBar, String searchMode)
	{
		
		System.out.println("Search Dir: " + searchModel.getSourceDir());
		System.out.println("Target Dir: " + searchModel.getTargetDir());
		
		// Default to quick search if null is passed in
		searchMode = (searchMode == null) ? AppConfigConstants.QUICK_SEARCH : searchMode;
		progressBar = (progressBar == null) ? new JProgressBar() : progressBar;
		
		// Search through the lists for matches
		if(searchModel != null && searchModel.getSourceFileList() != null && searchModel.getTargetFileList() != null)
		{
			// Restart the count as zero
			searchModel.setTotalMissingFiles(0);
			searchModel.setCurrentFileIndex(0);
			
			System.out.println("Progress Bar Max: " + progressBar.getMaximum());
			
			for(File file : searchModel.getSourceFileList())
			{
				boolean match = false;
				
				// Increment the current file index
				searchModel.setCurrentFileIndex(searchModel.getCurrentFileIndex() +1);
				progressBar.setValue(searchModel.getCurrentFileIndex());
				progressBar.firePropertyChange("value", searchModel.getCurrentFileIndex()-1, searchModel.getCurrentFileIndex());
				
				System.out.println("Progress Bar Value: " + progressBar.getValue());
				System.out.println("Current Index: " + searchModel.getCurrentFileIndex());
				System.out.println("Checking File: " + file.getName());
				for(File targetFile : searchModel.getTargetFileList())
				{
					if(AppConfigConstants.BINARY_SEARCH.equals(searchMode))
					{
						try 
						{
							if(isFileBinaryEqual(file, targetFile))
							{
								match = true;
								break;
							}
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
					else
					{
						if(file.getName().equals(targetFile.getName()) &&
								file.length() == targetFile.length())
						{
							match = true;
							break;
						}
					}
				}
				
				if(!match)
				{
					System.out.println("Missing File: " + file.getName());
					searchModel.setTotalMissingFiles(searchModel.getTotalMissingFiles() +1);
				}
			}
		}
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
	public static void populateSourceAndTargetLists(SearchModel searchModel) {
		
		if(searchModel != null)
		{
			// Iterate through the file list and count how many files are there
			searchModel.setSourceFileList(traverseDirectory(searchModel.getSourceFile(), null));
			searchModel.setTargetFileList(traverseDirectory(searchModel.getTargetFile(), null));
						
			System.out.println("Source File Count: " + searchModel.getTotalSourceFiles());
			System.out.println("Target File Count: " + searchModel.getTargetFileList().size());
		}
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
		List<File> fileList = (files != null) ? files : new ArrayList<File>() ;
		
		if(startingPoint != null && startingPoint.exists())
		{
			File[] startingList = startingPoint.listFiles();
			
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
		}
		else
		{
			System.err.println("Directory doesn't exist!");
		}
		
		return fileList;
	}
}
