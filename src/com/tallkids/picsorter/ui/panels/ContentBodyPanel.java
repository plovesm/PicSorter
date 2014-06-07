/**
 * 
 */
package com.tallkids.picsorter.ui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;

import com.tallkids.picsorter.ui.BackupInspectorModelManager;

/**
 * @author ott1982
 *
 */
public class ContentBodyPanel extends Panel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param biMM 
	 * @param layout
	 */
	public ContentBodyPanel(BackupInspectorModelManager biMM, LayoutManager layout) {
		super(layout);
				
		GridBagConstraints cons = new GridBagConstraints();
	    cons.insets = new Insets(5, 5, 5, 5);
		
	    // Holds the directory selection buttons
		DirectorySelectionPanel dirSelPanel = new DirectorySelectionPanel(biMM, layout);
		//dirSelPanel.setBackground(Color.BLUE);
		
		// Holds the outputs like progress bar and console
		OutputDashboardPanel outDashboardPanel = new OutputDashboardPanel(biMM, layout);
		//outDashboardPanel.setBackground(Color.CYAN);
		
		
		cons.gridx = 0;
		cons.gridy = 0;
		add(dirSelPanel, cons);
		
		cons.gridx = 1;
		cons.gridy = 0;
		add(outDashboardPanel, cons);
		
	}

}
