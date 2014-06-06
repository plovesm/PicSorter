/**
 * 
 */
package com.tallkids.picsorter.ui;

import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

import com.tallkids.picsorter.model.Model;
import com.tallkids.picsorter.model.SearchModel;

/**
 * @author Paul
 *
 */
public class BackupInspectorModelManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long 		serialVersionUID = 1L;
	private Model<? extends JFrame> 		mainFrameModel;
	private Model<? extends JFileChooser> 	inputSourceSelectorModel;
	private Model<? extends JFileChooser> 	inputTargetSelectorModel;
	private Model<? extends JTextPane> 		outputPanelModel;
	private Model<? extends JProgressBar> 	progressBarModel;
	private SearchModel						searchModel;
	
	/**
	 * @return the mainFrameModel
	 */
	public Model<? extends JFrame> getMainFrameModel() {
		return mainFrameModel;
	}
	/**
	 * @param mainFrameModel the mainFrameModel to set
	 */
	public void setMainFrameModel(Model<? extends JFrame> mainFrameModel) {
		this.mainFrameModel = mainFrameModel;
	}
	/**
	 * @return the inputSourceSelectorModel
	 */
	public Model<? extends JFileChooser> getInputSourceSelectorModel() {
		return inputSourceSelectorModel;
	}
	/**
	 * @param inputSourceSelectorModel the inputSourceSelectorModel to set
	 */
	public void setInputSourceSelectorModel(
			Model<? extends JFileChooser> inputSourceSelectorModel) {
		this.inputSourceSelectorModel = inputSourceSelectorModel;
	}
	/**
	 * @return the inputTargetSelectorModel
	 */
	public Model<? extends JFileChooser> getInputTargetSelectorModel() {
		return inputTargetSelectorModel;
	}
	/**
	 * @param inputTargetSelectorModel the inputTargetSelectorModel to set
	 */
	public void setInputTargetSelectorModel(
			Model<? extends JFileChooser> inputTargetSelectorModel) {
		this.inputTargetSelectorModel = inputTargetSelectorModel;
	}
	/**
	 * @return the outputPanelModel
	 */
	public Model<? extends JTextPane> getOutputPanelModel() {
		return outputPanelModel;
	}
	/**
	 * @param outputPanelModel the outputPanelModel to set
	 */
	public void setOutputPanelModel(Model<? extends JTextPane> outputPanelModel) {
		this.outputPanelModel = outputPanelModel;
	}
	/**
	 * @return the progressBarModel
	 */
	public Model<? extends JProgressBar> getProgressBarModel() {
		return progressBarModel;
	}
	/**
	 * @param progressBarModel the progressBarModel to set
	 */
	public void setProgressBarModel(Model<? extends JProgressBar> progressBarModel) {
		this.progressBarModel = progressBarModel;
	}
	/**
	 * @return the searchModel
	 */
	public SearchModel getSearchModel() {
		return searchModel;
	}
	/**
	 * @param searchModel the searchModel to set
	 */
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	
	
	
}
