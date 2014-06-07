/**
 * 
 */
package com.tallkids.picsorter.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.tallkids.picsorter.model.Model;
import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.ui.panels.ButtonPanel;
import com.tallkids.picsorter.ui.panels.ContentBodyPanel;

/**
 * @author ott1982
 *
 */
public class BackupInspector {
	
	//create GribBagLayout and the GridBagLayout Constraints
    BackupInspectorModelManager biMM = new BackupInspectorModelManager();
	GridBagLayout gridBag = new GridBagLayout();
    
	
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
				
				buildUIComponents();
				
			}
        });
	}

	private void buildUIComponents()
	{
		biMM.setSearchModel(new SearchModel());
		
		// Create the main frame
		BackupInspectorMainFrame biMainFrame = new BackupInspectorMainFrame("Backup Inspector");
		biMM.setMainFrameModel(new Model<BackupInspectorMainFrame>(biMainFrame));
		
		// Create the content panel
		ContentBodyPanel contentPane = new ContentBodyPanel(biMM, gridBag);
		
		// Create the button panel
		ButtonPanel buttonPanel = new ButtonPanel(biMM, gridBag);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		
		// Add the panels
		biMainFrame.add(contentPane, BorderLayout.NORTH);
		biMainFrame.add(buttonPanel, BorderLayout.SOUTH);
		biMainFrame.pack();
		
		// Show the frame
		biMainFrame.setVisible(true);
		
	}

}
