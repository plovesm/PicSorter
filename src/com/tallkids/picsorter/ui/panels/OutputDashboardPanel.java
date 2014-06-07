/**
 * 
 */
package com.tallkids.picsorter.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;

import javax.swing.JProgressBar;

import com.tallkids.picsorter.model.Model;
import com.tallkids.picsorter.ui.BackupInspectorModelManager;

/**
 * @author ott1982
 *
 */
public class OutputDashboardPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param layout
	 */
	public OutputDashboardPanel(BackupInspectorModelManager biMM, LayoutManager layout) {
		super(layout);
		
		GridBagConstraints cons = new GridBagConstraints();
	    cons.insets = new Insets(5, 5, 5, 5);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
		Model<JProgressBar> progressBarModel = new Model<JProgressBar>(progressBar);
		biMM.setProgressBarModel(progressBarModel);
		
		OutputConsolePanel consoleOut = new OutputConsolePanel();
		consoleOut.setSize(200, 300);
				
		Model<OutputConsolePanel> consoleOutModel = new Model<OutputConsolePanel>(consoleOut);
		biMM.setOutputPanelModel(consoleOutModel);
		
		cons.gridx = 0;
		cons.gridy = 0;
		add(progressBar, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		add(consoleOut, cons);
		
	}

}
