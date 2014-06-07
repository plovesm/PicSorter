/**
 * 
 */
package com.tallkids.picsorter.ui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * @author ott1982
 *
 */
public class BackupInspectorMainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public BackupInspectorMainFrame(String arg0) throws HeadlessException 
	{
		super(arg0);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
