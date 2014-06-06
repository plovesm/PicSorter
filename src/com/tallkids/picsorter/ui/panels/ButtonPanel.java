/**
 * 
 */
package com.tallkids.picsorter.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.tallkids.picsorter.ui.BackupInspectorModelManager;

/**
 * @author ott1982
 *
 */
public class ButtonPanel extends Panel {
	
	BackupInspectorModelManager biMM = new BackupInspectorModelManager();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param layout
	 */
	public ButtonPanel(BackupInspectorModelManager biMM, LayoutManager layout) {
		super(layout);
		
		if(biMM != null)
		{
			this.biMM = biMM;
		}
		
		addButtons();
	}

	private void addButtons() {
		
		GridBagConstraints cons = new GridBagConstraints();
	    cons.insets = new Insets(5, 5, 5, 5);
		
		
		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				
				// Close the frame
				if(biMM.getMainFrameModel() != null &&
						biMM.getMainFrameModel().getObject() != null)
				{
					biMM.getMainFrameModel().getObject().dispose();
				};
				
			}
		});
		
		
		add(btnClose, cons);
	}

}
