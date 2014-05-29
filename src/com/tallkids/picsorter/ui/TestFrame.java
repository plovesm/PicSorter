/**
 * 
 */
package com.tallkids.picsorter.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.tallkids.picsorter.constants.StyleConstants;
import com.tallkids.picsorter.model.SearchModel;
import com.tallkids.picsorter.util.FileSearchUtil;


/**
 * @author Paul
 *
 */
public class TestFrame {

	private SearchModel 	searchModel = new SearchModel();
    
	
	/**
	 * 
	 */
	public TestFrame() {
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
            	try 
            	{
            		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } 
            	catch (UnsupportedLookAndFeelException ex) 
                {
                 	ex.printStackTrace();
                } 
            	catch (ClassNotFoundException e) 
            	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            	catch (InstantiationException e) 
            	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            	catch (IllegalAccessException e) 
            	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                 JFrame frame = new JFrame("P Testing");
                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 frame.setLayout(new BorderLayout());
                 frame.add(new TestPane());
                 frame.pack();
                 frame.setLocationRelativeTo(null);
                 frame.setVisible(true);
            }
            
        });

	
	}

	public class TestPane extends JPanel {

        private JProgressBar 	pbProgress;
        private JButton 		start;
        private FileSearchUtil 	fileSearchUtil = new FileSearchUtil();
        private JFileChooser 	sourceChooser;
        private JFileChooser 	targetChooser;
        private JPanel 			mainPanel = new JPanel(new GridBagLayout());
    	
        public TestPane() {

        	sourceChooser = new JFileChooser();
    		sourceChooser.setCurrentDirectory(new java.io.File("."));
    		sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	    
    		
    	    // Button to launch the file dialog
    	    JButton btnOpenSourceLocation = new JButton("Open Source Location");
    	    btnOpenSourceLocation.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent event) {
    				int returnVal = sourceChooser.showOpenDialog(mainPanel);
    			    if(returnVal == JFileChooser.APPROVE_OPTION) {

    			    	String sourceDir = sourceChooser.getSelectedFile().getAbsolutePath();
    			    	   
    			    	System.out.println("Your source folder is: " + sourceDir);
    			    	   
    			    	// Set the source file
    			    	searchModel.setSourceFile(sourceChooser.getSelectedFile());
    			    }
    			}
    		});
    	    
    	    targetChooser = new JFileChooser();
    	    targetChooser.setCurrentDirectory(new java.io.File("."));
    	    targetChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	    
    	     
    	    // Button to launch the file dialog
    	    JButton btnOpenTargetLocation = new JButton("Open Target Location");
    	    btnOpenTargetLocation.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent event) {
    				int returnVal = targetChooser.showOpenDialog(mainPanel);
    			    if(returnVal == JFileChooser.APPROVE_OPTION) {
    			    	   
    			    	String targetDir = targetChooser.getSelectedFile().getAbsolutePath();
    			    	   
    			    	System.out.println("Your target folder is: " + targetDir);
    			    	   
    			    	searchModel.setTargetFile(targetChooser.getSelectedFile());
    			    	   
    			    }
    			}
    		});
        	
        	add(mainPanel, BorderLayout.NORTH);
        	add(btnOpenSourceLocation);
        	add(btnOpenTargetLocation);
        	
        	setBorder(new EmptyBorder(10, 10, 10, 10));
            pbProgress = new JProgressBar();
            pbProgress.setStringPainted(true);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 4, 4, 4);
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(pbProgress, gbc);

            start = new JButton("Start");
            gbc.gridy++;
            add(start, gbc);

            start.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    start.setEnabled(false);
                    
                    // Populate the lists and then do the search
    				FileSearchUtil.populateSourceAndTargetLists(searchModel);
    				
                    ProgressWorker pw = new ProgressWorker();
                    pw.addPropertyChangeListener(new PropertyChangeListener() 
                    {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            String name = evt.getPropertyName();
                            if (name.equals("progress")) {
                                int progress = (Integer) evt.getNewValue();
                                System.err.println("Progress: " + progress);
                                pbProgress.setValue(progress);
                                repaint();
                            } else if (name.equals("state")) {
                                SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                                switch (state) {
                                    case DONE:
                                        start.setEnabled(true);
                                        break;
                                }
                            }
                        }

                    });
                    pw.execute();
                    
                   
                }
            });

        }
    }

    public class ProgressWorker extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {
            int i = 0;
            int max = searchModel.getTotalSourceFiles(); //2000;
            
            // TODO null checks
            
            // Search the target directory for backups
	    	//FileSearchUtil.searchDirectory(searchModel);
	    	System.err.println("Max: " + max);
            
            while (i <= max) {
                System.err.println("Index Before: " + i);
                
                if(FileSearchUtil.isFileBackedUp(searchModel.getSourceFileList().get(i), searchModel))
                {
                	//increment the number of files
                	searchModel.setTotalMissingFiles(searchModel.getTotalMissingFiles() + 1);
                }
                
                i = searchModel.getCurrentFileIndex() + 1;//+= 10;
                searchModel.setCurrentFileIndex(i);
                System.err.println("Index After: " + i);
                
                int progress = Math.round(((float)i / (float)max) * 100f);
                
                setProgress(progress);
                try {
                    Thread.sleep(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            System.out.println("Total missing files: " + searchModel.getTotalMissingFiles());
            return null;
        }
    }
}
