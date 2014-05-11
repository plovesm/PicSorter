/**
 * 
 */
package com.tallkids.picsorter.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tallkids.picsorter.util.FileSearch;

/**
 * @author plovesm
 *
 */
public class PicSorterApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 FileSearch fs = new FileSearch();
        
        String homeDir = System.getProperty("user.home");
		String separator = System.getProperty("file.separator");
		
		String dir1 = homeDir + separator + "Desktop" + separator + "dir1";
		String dir2 = homeDir + separator + "Desktop" + separator + "dir2";
        
		//create GribBagLayout and the GridBagLayout Constraints
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.fill = GridBagConstraints.NORTH;
        
		JFrame frame = new JFrame("Backup Sorter");
		frame.setSize(500, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 50);
		
		JPanel mainPanel = new JPanel(gridBag);
		
		JPanel buttonPanel = new JPanel(gridBag);
				
		frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		JLabel labelContent = new JLabel("content");
		cons.gridx = 0;
		cons.gridy = 0;
		mainPanel.add(labelContent, cons);
		
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		JLabel labelButtons = new JLabel("button");
		cons.gridx = 0;
		cons.gridy = 0;
		buttonPanel.add(labelButtons, cons);
		
		
        /*
        String homeDir = System.getProperty("user.home");
        String searchDir = homeDir + "/Desktop"; //"/Pictures/iPhoto Library/Originals";
        // /Users/ott1982/Pictures/iPhoto Library/Originals
        System.out.println(searchDir);
        
        File root = new File(searchDir);
        
        System.out.println(searchDir.isEmpty());
        */
		fs.searchDirectory(dir1, dir2);
        System.out.println("Total missing files: " + fs.getMissingFileCount());


	}

}
