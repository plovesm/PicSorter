/**
 * 
 */
package com.tallkids.picsorter.ui.panels;

import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.tallkids.picsorter.constants.StyleConstants;
import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.ui.BackupInspectorModelManager;

/**
 * @author ott1982
 *
 */
public class DirectorySelectionPanel extends Panel {

	private BackupInspectorModelManager biMM = new BackupInspectorModelManager();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param arg0
	 */
	public DirectorySelectionPanel(BackupInspectorModelManager biMMIn, SearchModel sm, LayoutManager layout) {
		super(layout);
		
		final SearchModel searchModel;
		
		if(sm != null)
		{
			searchModel = sm;
		}
		else
		{
			searchModel = new SearchModel();
		}
		
		if( biMM != null)
		{
			this.biMM = biMMIn;
		}
		
		if(biMM.getMainFrameModel() != null)
		{
			final JFileChooser sourceChooser = new JFileChooser();
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
					int returnVal = sourceChooser.showOpenDialog(biMM.getMainFrameModel().getObject());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
	
				    	String sourceDir = sourceChooser.getSelectedFile().getAbsolutePath();
				    	   
				    	System.out.println("Your source folder is: " + sourceDir);
				    	   
				    	lblSourceOutput.setText(sourceDir);
				    	   
				    	// Set the source file
				    	searchModel.setSourceFile(sourceChooser.getSelectedFile());
				    	   
				    	biMM.getMainFrameModel().getObject().pack();
				    }
				}
			});
		    
		    final JFileChooser targetChooser = new JFileChooser();
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
					int returnVal = targetChooser.showOpenDialog(biMM.getMainFrameModel().getObject());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	   
				    	String targetDir = targetChooser.getSelectedFile().getAbsolutePath();
				    	   
				    	System.out.println("Your target folder is: " + targetDir);
				    	   
				    	lblTargetOutput.setText(targetDir);
				    	   
				    	searchModel.setTargetFile(targetChooser.getSelectedFile());
				    	   
				    	biMM.getMainFrameModel().getObject().pack();
				    }
				}
			});
		}
	}

}
