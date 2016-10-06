import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;




public class HeaderEditor 
{
	
	private int FRAME_WIDTH = 280;
	private int FRAME_HEIGHT = 520;
	private int BORDER = 30;
	private JFrame frame;
	private JList<String> list;
	private JLabel simpleLabel; 
	private JTextField toOpen;
	private JButton editHeaders;
	private JButton changeDirectory;
	
	private File selected;
	private Vector<File> filesToChange;
	private Vector<String> fileNamesToChange;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeaderEditor window = new HeaderEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HeaderEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
	filesToChange = new Vector<File>();
	frame = new JFrame();
	frame.getContentPane().setLayout(null);
	frame.setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);
	frame.setResizable(false);
	frame.setTitle("HeaderEditor");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	simpleLabel = new JLabel("Directory Selected:");
	simpleLabel.setBounds(BORDER+2,5,FRAME_WIDTH-2*BORDER,22);
	frame.add(simpleLabel);
	

	toOpen = new JTextField("No Directory Selected");
	toOpen.setBounds(BORDER-3,30,FRAME_WIDTH-2*BORDER+6,22);
	toOpen.setEditable(false);
	frame.add(toOpen);
	
    editHeaders = new JButton("Edit Headers");
	editHeaders.setBounds(BORDER,60,(FRAME_WIDTH-2*BORDER)/2+30,22);
	editHeaders.addActionListener(editHeadersListener);
	frame.add(editHeaders);
	
    changeDirectory = new JButton("Choose Directory");
	changeDirectory.setBounds(BORDER,90,(FRAME_WIDTH-2*BORDER)/2+30,22);
	changeDirectory.addActionListener(changeListener);
	frame.add(changeDirectory);
	
	
	
	String[] selections = {""};
    list = new JList<String>(selections);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setBounds(BORDER,120,FRAME_WIDTH-2*BORDER,350);
    
	
	frame.add(scrollPane);
	}
	
	class changeButtonListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Select a Folder with Files to Edit");
		// We customize the JFileChooser
		Component[] fileChooserComponents = fc.getComponents();
		Component[] componentsToModify = ((JPanel)fileChooserComponents[4]).getComponents();
		componentsToModify[0].setVisible(false);
		// We actually use the JFileChooser
	    // We update all the GUI elements when a folder is chosen
		int chosen = fc.showOpenDialog(changeDirectory);
			if (chosen == 0)
			{
			selected = fc.getSelectedFile();
					if (selected.exists())
					{
					toOpen.setText(selected.getAbsolutePath());
					File[] contentFiles = selected.listFiles();
					filesToChange=new Vector<File>();
					fileNamesToChange=new Vector<String>();
						for (int count=0; count <contentFiles.length; count++)
						{
						// We find the .docx files, and save those
							if (contentFiles[count].getName().length() > 5 && contentFiles[count].getName().substring(contentFiles[count].getName().length()-5).equals(".docx"))
							{
							filesToChange.addElement(contentFiles[count]);
							fileNamesToChange.addElement(contentFiles[count].getName());
							}
						}
					list.setListData(fileNamesToChange);
					}
					else
					{
					JOptionPane.showMessageDialog(frame, "The Selected Directory Does Not Exist");
					}
			}
		}
		// We populate the JList with the files in the directory
	}
	

	private changeButtonListener changeListener = new changeButtonListener();
	
	
	// When we edit the headers. Does a lot of things
	class editHeadersButtonListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if (filesToChange.size() == 0)
			{
			JOptionPane.showMessageDialog(frame, "No files to edit");
			}
			else
			{
				for (int count = 0;count<filesToChange.size();count++)
				{
				FileHeaderData extractedData = FileEditorTools.returnHeader(filesToChange.get(count));
					if (extractedData != null)
					{
					System.out.println(extractedData.numHeaders);	
					System.out.println(extractedData.foundIn.getName());
						for (int count2 = 0;count2<extractedData.numHeaders;count2++)
						{
						System.out.println("    "+extractedData.headersFound[count2]);
						}
					}
					else
					{
					System.out.println(filesToChange.get(count).getName()+" does not have a header. Please only include documents with headers in the folder");
					break;
					}
				}
			}
		}
	}
	
	private editHeadersButtonListener editHeadersListener = new editHeadersButtonListener();

}
