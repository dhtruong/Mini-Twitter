package miniTwitter;

import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdminPanel {

	private static AdminPanel pointer;
	private Admin admin;
	private JFrame frame;
	private JPanel mainPanel;
	private String input, updatedUser;
	private TreeView jtree;
	private JLabel labelUpdated;

	// Singleton
	private AdminPanel() {
	};

	public static AdminPanel getInstance() {
		if (pointer == null) {
			pointer = new AdminPanel();
		}
		return pointer;
	}

	public void init() {
		admin = new Admin();
		jtree = new TreeView();
		admin.setTree(jtree);
		setFrame(700, 600);
		setLayout(new GridBagLayout());
		displayFrame();
	}

	public void setFrame(int x, int y) {
		frame = new JFrame();
		frame.setTitle("Mini Twitter");
		frame.setSize(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setLayout(LayoutManager layout) {
		
		frame.getContentPane().setLayout(layout);

		mainPanel = new JPanel();
		createTreeView(layout);
		createControlPanel(layout);
		
		frame.add(mainPanel);
	}

	// Creating TreeView to display groups and users
	public void createTreeView(LayoutManager layout) {
		
		//Create panel and adjust layout
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(350, 500));
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		leftPanel.add(jtree);
		mainPanel.add(leftPanel);
	}

	// Creating control panel to display buttons 
	public void createControlPanel(LayoutManager layout) {
		
		//Create panel and adjust layout
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(300, 500));
		rightPanel.setLayout(new GridLayout(2, 0));

		JPanel topRightPanel = new JPanel(new GridLayout(2, 0));

		labelUpdated = new JLabel("Last updated user: ");
		
		JButton openUserViewButton = new JButton("Open User View");

		openUserViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!jtree.isUserGroup()) {
					admin.getSelectedUserView(jtree.getSelectedUserNode());
				}
			}
		});

		topRightPanel.add(openUserViewButton);

		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(4, 2));

		
		// Create a button to add user(s) to the groups onto tree view
		JButton showAddUserButton = new JButton("Add User");

		showAddUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input = (String) JOptionPane.showInputDialog(frame, "Enter User Name",
						"Add New User", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					if (!input.equals("")) {
						admin.addNewUser(input);
					} else {
						JOptionPane.showMessageDialog(frame, "Please input a user name.");
					}
				}
			}
		});
		
		// Create a button to add group(s) onto the tree view
		JButton showAddGroupButton = new JButton("Add Group");

		showAddGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input = JOptionPane.showInputDialog(frame, "Enter Group Name",
						"Add New Group", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					if (!input.equals("")) {
						admin.addNewUserGroup(input);
					} else {
						JOptionPane.showMessageDialog(frame, "Please input a user group.");
					}
				}
			}
		});
		
		// Create a button to show total users on the tree view
		JButton showUserButton = new JButton("Show User Total");

		showUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Total number of users: "
						+ admin.getTotalUsers(), "Total Users",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		// Create a button to show total groups on the tree view
		JButton showGroupButton = new JButton("Show Group Total");

		showGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Total number of groups: "
						+ admin.getTotalUserGroups(), "Total Groups", 
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		// Create a button to show total messages added to the user's news feed
		JButton showMessagesButton = new JButton("Show Messages Total");
		
		showMessagesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Total number of messages: "
						+ admin.getTotalMessages(), "Total Messages",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		// Create a button to show positive percentage by checking users' messages
		JButton showPositiveButton = new JButton("Show Positive Percentage");
		
		showPositiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Total number of positive messages: "
						+ admin.getTotalPositiveMessages() + "%", "Positive Percentage",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		
		// A3: Create a button to validate User(s)/Group(s) ID by displaying true or false
		JButton showValidation = new JButton("Show Validation");
		showValidation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "ID Validation Status: " + jtree.isValidation(),
						"Show ID Validation Status", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		// A3: Create a button to show the last user ID who updated their news feed
		JButton showLastUpdated = new JButton("Last Updated User");
		showLastUpdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "Last updated user: "
						+ updatedUser, "Show Last Updated User",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		// Adding all the buttons to the panel
		bottomRightPanel.add(showAddUserButton);
		bottomRightPanel.add(showAddGroupButton);
		bottomRightPanel.add(showUserButton);
		bottomRightPanel.add(showGroupButton);
		bottomRightPanel.add(showMessagesButton);
		bottomRightPanel.add(showPositiveButton);
		bottomRightPanel.add(showValidation);
		bottomRightPanel.add(showLastUpdated);

		rightPanel.add(topRightPanel);
		rightPanel.add(bottomRightPanel);

		mainPanel.add(rightPanel);
	}

	public void displayFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// Gets the latest user added
	public void setLastUpdated(String userID) {
		updatedUser = userID;
	}

}