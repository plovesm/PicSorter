/**
 * 
 */
package com.tallkids.picsorter.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author ott1982
 *
 */
public class BackupInspector {
	
	/**
	 * Initial UI launch point for BackupInspector
	 */
	public BackupInspector() 
	{
		EventQueue.invokeLater(new Runnable() 
        {

			@Override
			public void run() 
			{
				try 
            	{
            		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } 
            	catch (UnsupportedLookAndFeelException ex) 
                {
                 	// TODO fill in better error handling
            		ex.printStackTrace();
                } 
            	catch (ClassNotFoundException e) 
            	{
					e.printStackTrace();
				} 
            	catch (InstantiationException e) 
            	{
					e.printStackTrace();
				} 
            	catch (IllegalAccessException e) 
            	{
					e.printStackTrace();
				}
				
			}
			
			// Create the main frame
			BackupInspectorMainFrame biMainFrame = new BackupInspectorMainFrame("Backup Inspector");
			
        });
	}

	

}
