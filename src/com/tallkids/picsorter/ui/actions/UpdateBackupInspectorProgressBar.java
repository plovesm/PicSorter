/**
 * 
 */
package com.tallkids.picsorter.ui.actions;

import javax.swing.SwingWorker;

import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.ui.BackupInspectorModelManager;
import com.tallkids.picsorter.util.FileSearchUtil;

/**
 * @author ott1982
 *
 */
public class UpdateBackupInspectorProgressBar extends SwingWorker<Object, Object> {
	
	private BackupInspectorModelManager biMM;
	
	/**
	 * @param biMM
	 */
	public UpdateBackupInspectorProgressBar(BackupInspectorModelManager biMM) {
		super();
		this.biMM = biMM;	
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		int i = 0;
        int max = biMM.getSearchModel().getTotalSourceFiles();
        
        // Search the target directory for backups
    	System.err.println("Max: " + max);
    	
    	// Reset the total missing files
    	biMM.getSearchModel().setTotalMissingFiles(0);
    	
        if(biMM.getSearchModel().getSourceFileList() != null && !biMM.getSearchModel().getSourceFileList().isEmpty())
        {
        	while (i <= max) 
        	{
                if(!FileSearchUtil.isFileBackedUp(biMM.getSearchModel().getSourceFileList().get(i), biMM.getSearchModel()))
                {
                	//increment the number of files
                	biMM.getSearchModel().setTotalMissingFiles(biMM.getSearchModel().getTotalMissingFiles() + 1);
                }
                
                // Update the loop index and current file index
                i = biMM.getSearchModel().getCurrentFileIndex() + 1;
                biMM.getSearchModel().setCurrentFileIndex(i);
                
                // Calculate the progress percentage
                int progress = Math.round(((float)i / (float)max) * 100f);
                
                // Set the progress property of the SwingWorker
                setProgress(progress);
                                
            }
        }
		
		return null;
	}

}
