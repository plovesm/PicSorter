/**
 * 
 */
package com.tallkids.picsorter.util;

import java.io.File;

/**
 * @author ott1982
 *
 */
public class FileWalker {
	
	private File activeSearchFile = new File("");
	private String targetDir = "/Volumes/BlackBox2TB/Backups/Pictures/Photos_by_date";
	private boolean match = false;
    private int missingFileCount = 0;
    
    public void walkTarget( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;
        
        for ( File f : list ) {
            if ( f.isDirectory() ) {
            	walkTarget( f.getAbsolutePath() );
                //System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
            	if(f.equals(activeSearchFile))
            	{
            		match = true;
            	}
            }
        }
        
        
    }
    
    public void walkSource( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walkSource( f.getAbsolutePath() );
                //System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
            	activeSearchFile = f;
            	
            	//System.out.println( "Checking File: " + f.getName() );
            	
            	walkTarget(targetDir);
            	
            	if(!match)
                {
                	setMissingFileCount(getMissingFileCount() + 1);
            		System.out.println( f.getAbsolutePath() );
                }
            }
        }
    }
    public int getMissingFileCount() {
		return missingFileCount;
	}

	public void setMissingFileCount(int missingFileCount) {
		this.missingFileCount = missingFileCount;
	}

	public static void main(String[] args) {
        FileWalker fw = new FileWalker();
        String homeDir = System.getProperty("user.home");
        String searchDir = homeDir + "/Pictures/iPhoto Library/Originals";
        // /Users/ott1982/Pictures/iPhoto Library/Originals
        //System.out.println(searchDir);
        
        fw.walkSource(searchDir);
        System.out.println("Total missing files: " + fw.getMissingFileCount());
    }
}
