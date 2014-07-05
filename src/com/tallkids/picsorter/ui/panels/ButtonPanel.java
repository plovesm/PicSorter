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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import com.tallkids.picsorter.constants.AppConfigConstants;
import com.tallkids.picsorter.ui.BackupInspectorModelManager;
import com.tallkids.picsorter.ui.actions.UpdateBackupInspectorProgressBar;
import com.tallkids.picsorter.util.AppUtils;
import com.tallkids.picsorter.util.FileSearchUtil;

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
		
		final JButton btnSearch = new JButton("Quick Search");
		btnSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				
				AppUtils.writeOutputLn(biMM, "Initializing...");
				
				// Populate the lists and then do the search
				FileSearchUtil.populateSourceAndTargetLists(biMM.getSearchModel());
				// Set the max on the progress bar
		    	biMM.getProgressBarModel().getObject().setMaximum(biMM.getSearchModel().getTotalSourceFiles());
		    	
		    	// Reset the counter
		    	biMM.getProgressBarModel().getObject().setValue(0);
		    	repaint();
		    	
		    	System.err.println("Qsearch: " + biMM.getSearchModel().getTotalSourceFiles());
		    			    	
		    	btnSearch.setEnabled(false);
		    	
		    	UpdateBackupInspectorProgressBar task = new UpdateBackupInspectorProgressBar(biMM);

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
                            
                            biMM.getProgressBarModel().getObject().setValue(progress);
                            
                            System.err.println("Progress Bar value: " + biMM.getProgressBarModel().getObject().getValue());
                            repaint();
                        } 
                        else if (name.equals("state")) 
                        {
                        	UpdateBackupInspectorProgressBar.StateValue state = (UpdateBackupInspectorProgressBar.StateValue) evt.getNewValue();
                        	System.out.println("State: " + state);
                            switch (state) {
                                case DONE:
                                	btnSearch.setEnabled(true);
                                	AppUtils.writeOutputLn(biMM, "Total missing files: " + biMM.getSearchModel().getTotalMissingFiles());
                                    repaint();
                                	break;
								case PENDING:
									break;
								case STARTED:
									break;
								default:
									break;
	                            }
                        }
                    }

                });
		    	
		    	task.execute();		    	
		    	
		    	biMM.getMainFrameModel().getObject().pack();
		    	
				// Search the target directory for backups
		    	FileSearchUtil.searchDirectory(biMM.getSearchModel());
				System.out.println("Total missing files: " + biMM.getSearchModel().getTotalMissingFiles());

			}
		});
		
		JButton btnBinarySearch = new JButton("Binary Search (Slower)");
		btnBinarySearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				
				// Populate the lists and then do the search
				FileSearchUtil.populateSourceAndTargetLists(biMM.getSearchModel());
				// Set the max on the progress bar
				biMM.getProgressBarModel().getObject().setMaximum(biMM.getSearchModel().getTotalSourceFiles());
		    	System.err.println("Bsearch: " + biMM.getSearchModel().getTotalSourceFiles());
		    	
		    	biMM.getMainFrameModel().getObject().pack();
		    	
				// Search the target directory for backups
		    	FileSearchUtil.searchDirectory(biMM.getSearchModel(), AppConfigConstants.BINARY_SEARCH);
				System.out.println("Total missing files: " + biMM.getSearchModel().getTotalMissingFiles());

			}
		});
		
		add(btnClose, cons);
		add(btnSearch, cons);
	}

}
