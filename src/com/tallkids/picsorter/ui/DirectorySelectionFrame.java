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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.tallkids.picsorter.constants.AppConfigConstants;
import com.tallkids.picsorter.constants.StyleConstants;
import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.util.FileSearchUtil;

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
	private JPanel 				outputPanel;
	private JPanel 				buttonPanel;
	private SearchModel			searchModel = new SearchModel();
	
	public void buildDirectorySelection() {
		
		/** Step 0: Declare method level variables
		 * 	
		 */
		//create GribBagLayout and the GridBagLayout Constraints
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
        /** Step 1: Build overall frame
         * 
         */
        mainFrame = new JFrame("Backup Sorter");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(100, 50);
		 
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(10, 10, 10, 10);
		
        /** Step 2: Build main panel
         * 
         */
		mainPanel = new JPanel(gridBag);
		outputPanel = new JPanel(gridBag);
		
		/** Step 2a: Build Status bar for searchable files
	     * 
	     */
		final JProgressBar progressBar = new JProgressBar()
		{
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see java.awt.Component#isVisible()
			 */
			@Override
			public boolean isVisible() 
			{
				// only show if 
				return true; //(searchModel.getTotalSourceFiles() > 0);
			}
			
			
			
		};
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		searchModel.setProgressBar(progressBar);
		
		/** Step 2b: Build file chooser for source and target directory
		 * 
		 */
		sourceChooser = new JFileChooser();
		sourceChooser.setCurrentDirectory(new java.io.File("."));
		sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
		 // Row label
	    JLabel lblSourceSelection = new JLabel("Choose the source directory: ");
	    lblSourceSelection.setFont(StyleConstants.SMALL_LABEL_FONT);
	    
	    // Label to display the selected target directory
	    final JLabel lblSourceOutput = new JLabel("");
	    
	    // Button to launch the file dialog
	    JButton btnOpenSourceLocation = new JButton("Open Source Location");
	    btnOpenSourceLocation.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				int returnVal = sourceChooser.showOpenDialog(mainPanel);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {

			    	String sourceDir = sourceChooser.getSelectedFile().getAbsolutePath();
			    	   
			    	System.out.println("Your source folder is: " + sourceDir);
			    	   
			    	lblSourceOutput.setText(sourceDir);
			    	   
			    	// Set the source file
			    	searchModel.setSourceFile(sourceChooser.getSelectedFile());
			    	   
			    	mainFrame.pack();
			    }
			}
		});
	    
	    targetChooser = new JFileChooser();
	    targetChooser.setCurrentDirectory(new java.io.File("."));
	    targetChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    // Row label
	    JLabel lblTargetSelection = new JLabel("Choose the target directory: ");
	    lblTargetSelection.setFont(StyleConstants.SMALL_LABEL_FONT);
	    
	    // Label to display the selected target directory
	    final JLabel lblTargetOutput = new JLabel("");
	    
	    // Button to launch the file dialog
	    JButton btnOpenTargetLocation = new JButton("Open Target Location");
	    btnOpenTargetLocation.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				int returnVal = targetChooser.showOpenDialog(mainPanel);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	   
			    	String targetDir = targetChooser.getSelectedFile().getAbsolutePath();
			    	   
			    	System.out.println("Your target folder is: " + targetDir);
			    	   
			    	lblTargetOutput.setText(targetDir);
			    	   
			    	searchModel.setTargetFile(targetChooser.getSelectedFile());
			    	   
			    	mainFrame.pack();
			    }
			}
		});
	    
	    
	    /** Step 3: Build output panel
	     * 
	     */
	    JTextArea outputArea = new JTextArea(5, 100);
	    //outputArea.setRows(5);
	    //outputArea.setSize(mainPanel.getWidth(), 25);
	    JScrollPane outputScrollPane = new JScrollPane(outputArea); 
	    outputArea.setEditable(false);
	    
		
		/** Step 4: Build button panel - Cancel, Search
		 * 
		 */
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
		
		final JButton btnSearch = new JButton("Quick Search");
		btnSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				
				// Populate the lists and then do the search
				FileSearchUtil.populateSourceAndTargetLists(searchModel);
				// Set the max on the progress bar
		    	searchModel.getProgressBar().setMaximum(searchModel.getTotalSourceFiles());
		    	// Reset the counter
		    	searchModel.getProgressBar().setValue(0);
		    	repaint();
		    	
		    	System.err.println("Qsearch: " + searchModel.getTotalSourceFiles());
		    			    	
		    	btnSearch.setEnabled(false);
		    	
		    	Sleeper task = new Sleeper();

		    	task.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        String name = evt.getPropertyName();
                        System.err.println("Name of Event fired: " + name);
                        System.err.println("Value of Event fired: " + evt.getNewValue());
                        if (name.equals("progress")) 
                        {
                            int progress = (Integer) evt.getNewValue();
                            
                            System.err.println("Progress of Event fired: " + progress);
                            
                            searchModel.getProgressBar().setValue(progress);
                            
                            System.err.println("Progress Bar value: " + searchModel.getProgressBar().getValue());
                            repaint();
                        } 
                        else if (name.equals("state")) 
                        {
                        	Sleeper.StateValue state = (Sleeper.StateValue) evt.getNewValue();
                        	System.out.println("State: " + state);
                            switch (state) {
                                case DONE:
                                	btnSearch.setEnabled(true);
                                	System.out.println("Total missing files: " + searchModel.getTotalMissingFiles());
                                    break;
                            }
                        }
                    }

                });
		    	
		    	task.execute();		    	
		    	
		    	mainFrame.pack();
		    	
				// Search the target directory for backups
		    	FileSearchUtil.searchDirectory(searchModel);
				System.out.println("Total missing files: " + searchModel.getTotalMissingFiles());

			}
		});
		
		JButton btnBinarySearch = new JButton("Binary Search (Slower)");
		btnBinarySearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				
				// Populate the lists and then do the search
				FileSearchUtil.populateSourceAndTargetLists(searchModel);
				// Set the max on the progress bar
				searchModel.getProgressBar().setMaximum(searchModel.getTotalSourceFiles());
		    	System.err.println("Bsearch: " + searchModel.getTotalSourceFiles());
		    	
		    	mainFrame.pack();
		    	
				// Search the target directory for backups
		    	FileSearchUtil.searchDirectory(searchModel, AppConfigConstants.BINARY_SEARCH);
				System.out.println("Total missing files: " + searchModel.getTotalMissingFiles());

			}
		});
		
		/** Step 5: Add all the components
		 * 
		 */
		
		// First row
		cons.gridx = 0;
		cons.gridy = 0;
		mainPanel.add(lblSourceSelection, cons);
		cons.gridx = 1;
		cons.gridy = 0;
		mainPanel.add(lblSourceOutput, cons);
		cons.gridx = 2;
		cons.gridy = 0;
	    mainPanel.add(btnOpenSourceLocation, cons);
	    
	    // Second row
	    cons.gridx = 0;
		cons.gridy = 1;
		mainPanel.add(lblTargetSelection, cons);
		cons.gridx = 1;
		cons.gridy = 1;
		mainPanel.add(lblTargetOutput, cons);
		cons.gridx = 2;
		cons.gridy = 1;
	    mainPanel.add(btnOpenTargetLocation, cons);
	    
	    // Third row
	    cons.gridx = 0;
		cons.gridy = 2;
		mainPanel.add(searchModel.getProgressBar(), cons);
		mainFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		// Output Area
		cons.insets = new Insets(1, 2, 8, 2);
		outputPanel.add(outputScrollPane, cons);
		mainFrame.getContentPane().add(outputPanel, BorderLayout.CENTER);
		
		// Bottom button panel
		cons.insets = new Insets(1, 2, 8, 2);
		cons.gridx = 0;
		cons.gridy = 0;
		buttonPanel.add(btnSearch, cons);
		cons.gridx = 0;
		cons.gridy = 1;
		buttonPanel.add(btnBinarySearch, cons);
		cons.gridx = 0;
		cons.gridy = 2;
		buttonPanel.add(btnClose, cons);
		mainFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		// Step 5: Show the Frame
		mainFrame.pack();
		mainFrame.setVisible(true);
		

	}
	
	//SwingWorker class is used to simulate a task being performed
	class Sleeper extends SwingWorker 
	{

        @Override
        public Void doInBackground() throws InterruptedException {
               
        	 int i = 0;
             int max = searchModel.getTotalSourceFiles(); //2000;
             
             // TODO null checks
             
             // Search the target directory for backups
 	    	//FileSearchUtil.searchDirectory(searchModel);
 	    	System.err.println("Max: " + max);
             
             while (i <= max) {
                 System.err.println("Index Before: " + i);
                 
                 if(FileSearchUtil.isFileBackedUp(searchModel.getSourceFileList().get(i), searchModel))
                 {
                 	//increment the number of files
                 	searchModel.setTotalMissingFiles(searchModel.getTotalMissingFiles() + 1);
                 }
                 
                 i = searchModel.getCurrentFileIndex() + 1;//+= 10;
                 searchModel.setCurrentFileIndex(i);
                 System.err.println("Index After: " + i);
                 
                 int progress = Math.round(((float)i / (float)max) * 100f);
                 
                 setProgress(progress);
                 try 
                 {
                 	Thread.sleep(0);
                 } 
                 catch (Exception e) 
                 {
                     e.printStackTrace();
                 }
             }
             return null;
        }
	}
}
