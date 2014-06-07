/**
 * 
 */
package com.tallkids.picsorter.util;

import com.tallkids.picsorter.ui.BackupInspectorModelManager;

/**
 * @author ott1982
 *
 */
public class AppUtils {

	public static boolean writeOutputLn(BackupInspectorModelManager biMM, String line)
	{
		boolean result = true; // Innocent until proven guilty
		
		biMM.getOutputPanelModel().getObject().setText(
				(biMM.getOutputPanelModel().getObject().getText().length() == 0) ?
    			line : 
    			biMM.getOutputPanelModel().getObject().getText() + "\n"	+ line);
		
		return result;
	}
}
