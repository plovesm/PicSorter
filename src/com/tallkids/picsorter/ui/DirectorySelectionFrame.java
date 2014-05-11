/**
 * 
 */
package com.tallkids.picsorter.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.util.FileSearch;

/**
 * @author ott1982
 *
 */
public class DirectorySelectionFrame extends JFrame
{

	private static final long 	serialVersionUID = 1L;
	private JFileChooser 		sourceChooser;
	private JFileChooser 		targetChooser; 
	private JFrame 				mainFrame;
	private JPanel 				mainPanel;
	private JPanel 				buttonPanel;
	private SearchModel			searchModel = new SearchModel();
	
	public void buildDirectorySelection() {
		
		// Step 0: Declare method level variables		
		final FileSearch fs = new FileSearch();
		
		//create GribBagLayout and the GridBagLayout Constraints
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
		// Step 1: Build overall frame
        mainFrame = new JFrame("Backup Sorter");
        mainFrame.setSize(500, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(100, 50);
		 
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(10, 10, 10, 10);
		
		// Step 2: Build main panel
		mainPanel = new JPanel(gridBag);
		
		// Step 2a: Build file chooser for source and target directory
		sourceChooser = new JFileChooser();
		sourceChooser.setCurrentDirectory(new java.io.File("."));
		sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
		JLabel lblSourceSelection = new JLabel("Choose the source directory: ");
		
	    JButton btnOpenSourceLocation = new JButton("Open Source Location");
	    btnOpenSourceLocation.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				int returnVal = sourceChooser.showOpenDialog(mainPanel);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       try {
			    	   String sourceDir = sourceChooser.getSelectedFile().getCanonicalPath();
			    	   
			    	   System.out.println("Your source folder is: " +
			    			   sourceDir);
			    	   
			    	   searchModel.setSourceDir(sourceDir);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
			}
		});
	    
	    targetChooser = new JFileChooser();
	    targetChooser.setCurrentDirectory(new java.io.File("."));
	    targetChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    JLabel lblTargetSelection = new JLabel("Choose the source directory: ");
		
	    JButton btnOpenTargetLocation = new JButton("Open Target Location");
	    btnOpenTargetLocation.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				int returnVal = targetChooser.showOpenDialog(mainPanel);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       try {
			    	   System.out.println("Your target folder is: " +
			    			   targetChooser.getSelectedFile().getCanonicalPath());
			    	   
			    	   searchModel.setTargetDir(targetChooser.getSelectedFile().getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
			}
		});
	    
		
		// Step 3: Build button panel - Cancel, Search
		buttonPanel = new JPanel(gridBag);
		
		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				
				// Close the frame
				mainFrame.dispose();
				
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				
				// Close the frame
				fs.searchDirectory(searchModel.getSourceDir(), searchModel.getTargetDir());
				System.out.println("Total missing files: " + fs.getMissingFileCount());

			}
		});
		
		
		// Step 4: Add all the components
		
		cons.gridx = 0;
		cons.gridy = 0;
		mainPanel.add(lblSourceSelection, cons);
		cons.gridx = 1;
		cons.gridy = 0;
	    mainPanel.add(btnOpenSourceLocation, cons);
	    cons.gridx = 0;
		cons.gridy = 1;
		mainPanel.add(lblTargetSelection, cons);
		cons.gridx = 1;
		cons.gridy = 1;
	    mainPanel.add(btnOpenTargetLocation, cons);	    
		mainFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		cons.gridx = 0;
		cons.gridy = 0;
		buttonPanel.add(btnClose, cons);
		cons.gridx = 1;
		cons.gridy = 0;
		buttonPanel.add(btnSearch, cons);
		mainFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		// Step 5: Show the Frame
		mainFrame.setVisible(true);
		

	}

}
