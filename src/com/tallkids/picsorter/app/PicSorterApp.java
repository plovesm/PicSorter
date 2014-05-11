/**
 * 
 */
package com.tallkids.picsorter.app;

import com.tallkids.picsorter.ui.DirectorySelectionFrame;

/**
 * @author plovesm
 *
 */
public class PicSorterApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Step 0: Declare variables
		DirectorySelectionFrame dirSelUI = new DirectorySelectionFrame();
		
		// Step 1: Launch UI
		dirSelUI.buildDirectorySelection();
		
		
		
        
	}

}
