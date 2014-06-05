/**
 * 
 */
package com.tallkids.picsorter.ui.actions;

import javax.swing.SwingWorker;

import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.util.FileSearchUtil;

/**
 * @author ott1982
 *
 */
public class UpdateBackupInspectorProgressBar extends SwingWorker<Object, Object> {
	
	private SearchModel searchModel;
	
	/**
	 * @param searchModel
	 */
	public UpdateBackupInspectorProgressBar(SearchModel searchModel) {
		super();
		
		// Protect the searchModel from being set to null
		if(searchModel != null)
		{
			this.searchModel = searchModel;
		}
		else
		{
			// TODO error out and trap
		}
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		int i = 0;
        int max = searchModel.getTotalSourceFiles();
        
        // Search the target directory for backups
    	System.err.println("Max: " + max);
    	
    	// Reset the total missing files
    	searchModel.setTotalMissingFiles(0);
    	
        if(searchModel.getSourceFileList() != null && !searchModel.getSourceFileList().isEmpty())
        {
        	while (i <= max) 
        	{
                if(!FileSearchUtil.isFileBackedUp(searchModel.getSourceFileList().get(i), searchModel))
                {
                	//increment the number of files
                	searchModel.setTotalMissingFiles(searchModel.getTotalMissingFiles() + 1);
                }
                
                // Update the loop index and current file index
                i = searchModel.getCurrentFileIndex() + 1;
                searchModel.setCurrentFileIndex(i);
                
                // Calculate the progress percentage
                int progress = Math.round(((float)i / (float)max) * 100f);
                
                // Set the progress property of the SwingWorker
                setProgress(progress);
                
                /* 
                try {
                    Thread.sleep(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }
		
		return null;
	}

}
