/**
 * 
 */
package com.tallkids.picsorter.ui;

import javax.swing.JFrame;

import com.tallkids.picsorter.model.Model;

/**
 * @author Paul
 *
 */
public class BackupInspectorModelManager {
	
	Model<? extends JFrame> mainFrameModel;

	/**
	 * @return the mainFrameModel
	 */
	public Model<? extends JFrame> getMainFrameModel() {
		return mainFrameModel;
	}

	/**
	 * @param mainFrameModel the mainFrameModel to set
	 */
	public void setMainFrame(Model<? extends JFrame> mainFrameModel) {
		this.mainFrameModel = mainFrameModel;
	}
	
	
	
}
