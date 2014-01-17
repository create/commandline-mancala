import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/** 
	This class demonstrates the basics of setting up a Java Swing GUI uisng the
	BorderLayout. You should be able to use this program to drop in other
	components when building a GUI 
*/
public class BasicGui{
	// Initialize all swing objects.
    private JFrame f = new JFrame("Basic GUI"); //create Frame
    private JPanel pnlNorth = new JPanel(); // North quadrant 
    private JPanel pnlSouth = new JPanel(); // South quadrant
    private JPanel pnlEast = new JPanel(); // East quadrant
    private JPanel pnlWest = new JPanel(); // West quadrant
    private JPanel pnlCenter = new JPanel(); // Center quadrant

	// Buttons some there is something to put in the panels
    private JButton btnNorth = new JButton("North");
    private JButton btnSouth = new JButton("South");
    private JButton btnEast = new JButton("East");
    private JButton btnWest = new JButton("West");
    private JButton btnCenter = new JButton("Center");

    // Menu
    private JMenuBar mb = new JMenuBar(); // Menubar
    private JMenu mnuFile = new JMenu("File"); // File Entry on Menu bar
    private JMenuItem mnuItemQuit = new JMenuItem("Quit"); // Quit sub item
    private JMenu mnuHelp = new JMenu("Help"); // Help Menu entry
    private JMenuItem mnuItemAbout = new JMenuItem("About"); // About Entry

    /** Constructor for the GUI */
    public BasicGui(){
		// Set menubar
        f.setJMenuBar(mb);
        
		//Build Menus
        mnuFile.add(mnuItemQuit);  // Create Quit line
        mnuHelp.add(mnuItemAbout); // Create About line
        mb.add(mnuFile);        // Add Menu items to form
        mb.add(mnuHelp);

        // Add Buttons
        pnlNorth.add(btnNorth);
        pnlSouth.add(btnSouth);
        pnlEast.add(btnEast);
        pnlWest.add(btnWest);
        pnlCenter.add(btnCenter);
        
        // Setup Main Frame
        f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		f.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        f.getContentPane().add(pnlEast, BorderLayout.EAST);
		f.getContentPane().add(pnlWest, BorderLayout.WEST);
        f.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        
		// Allows the Swing App to be closed
        f.addWindowListener(new ListenCloseWdw());
		
		//Add Menu listener
        mnuItemQuit.addActionListener(new ListenMenuQuit());
    }
	
    public class ListenMenuQuit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);         
        }
    }
	
    public class ListenCloseWdw extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);         
        }
    }
	
    public void launchFrame(){
        // Display Frame
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack(); //Adjusts panel to components for display
        f.setVisible(true);
    }

}