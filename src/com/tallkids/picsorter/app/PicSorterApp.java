/**
 * 
 */
package com.tallkids.picsorter.app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.tallkids.picsorter.ui.TestFrame;

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
		//DirectorySelectionFrame dirSelUI = new DirectorySelectionFrame();
		TestFrame dirSelUI = new TestFrame();
		
		// Step 1: Launch UI
		try {
            // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		
		//dirSelUI.buildDirectorySelection();
		
		
		
        
	}

}
