/**
 * 
 */
package com.tallkids.picsorter.ui.panels;

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
	
	BackupInspectorModelManager biMM = new BackupInspectorModelManager();
	
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
		
		if(biMM != null)
		{
			this.biMM = biMM;
		}
		
		GridBagConstraints cons = new GridBagConstraints();
	    cons.insets = new Insets(5, 5, 5, 5);
		
		DirectorySelectionPanel dirSelPanel = new DirectorySelectionPanel(layout);
		dirSelPanel.setSize(200, 200);
		
		OutputDashboardPanel outDashboardPanel = new OutputDashboardPanel(layout);
		dirSelPanel.setSize(200, 100);
		
		add(dirSelPanel);
		add(outDashboardPanel);
		
	}

}
