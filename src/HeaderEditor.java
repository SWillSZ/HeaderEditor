import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



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
	private String currentDirectory;

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
	frame.add(editHeaders);
	
    changeDirectory = new JButton("Choose Directory");
	changeDirectory.setBounds(BORDER,90,(FRAME_WIDTH-2*BORDER)/2+30,22);
	changeDirectory.addActionListener(changeListener);
	frame.add(changeDirectory);
	
	
	
	String[] selections = {"Placeholder 1", "Placeholder 2" };
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
		int chosen = fc.showOpenDialog(changeDirectory);
			if (chosen == 0)
			{
			toOpen.setText(fc.getSelectedFile().getAbsolutePath());
			changeDirectory.setText("Change Directory");	
			}
		}
		// We populate the JList with the files in the directory
	}

	private changeButtonListener changeListener = new changeButtonListener();


}
