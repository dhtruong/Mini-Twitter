package miniTwitter;

import java.awt.Color;  
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreeView extends JPanel{

	private DefaultMutableTreeNode rootNode;
	private DefaultMutableTreeNode parentNode;
	private DefaultTreeModel treeModel;
	private JTree tree;
	private TreePath parentPath;
	private Users currentNode;
	private User currentUserNode;
	private DefaultMutableTreeNode node;
	private UserGroup rootGroup, currentUserGroupNode;

	public TreeView() {
		
		// Initializes tree and root node
		rootGroup = new UserGroup();
		rootGroup.setGroup("Root", null);
		rootNode = new DefaultMutableTreeNode(rootGroup);
		treeModel = new DefaultTreeModel(rootNode);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new TreeModel());

		// Listener to get node's value if it changes
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0){
				node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				parentPath = tree.getSelectionPath();
				parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
				if (node == null) {
					return;
				}
				Object thisNode = node.getUserObject();
				if (thisNode instanceof User) {
					currentNode = (User) thisNode;
				} 
				else if (thisNode instanceof UserGroup) {
					currentNode = (UserGroup) thisNode;
				}
			}
		});

		setBackground(Color.WHITE);
		add(tree);
	}

	public DefaultMutableTreeNode addObject(String name, Object child) {
		parentNode = null;
		parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} 
		else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, name, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, String name){
		return addObject(parent, child, name, false);
	}

	// Adds the User or UserGroup in a recursive call
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, String name, 
			boolean shouldBeVisible) {
		
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		if (parent.getAllowsChildren()) {
			if (child instanceof User && parent.getAllowsChildren()) {
				childNode.setAllowsChildren(false);
				treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
			} 
			else if (child instanceof UserGroup) {
				childNode.setAllowsChildren(true);
				treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
			}
		}

		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}
	
	// Gets the parent group node
	public Object getParentGroup() {
		return parentNode.getUserObject();
	}

	// Checks if user exists in tree view
	public boolean nodeExists(String id) {
		
		User thisUser;
		UserGroup thisUserGroup;
		String thisID = null;
		Enumeration en = rootNode.breadthFirstEnumeration();
		
		while (en.hasMoreElements()){
			
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
			Object thisNode = node.getUserObject();
			
			if (thisNode instanceof User) {
				thisUser = (User) thisNode;
				thisID = thisUser.getUserID();
			} 
			else if (thisNode instanceof UserGroup) {
				thisUserGroup = (UserGroup) thisNode;
				thisID = thisUserGroup.getGroupID();
			}
			if (thisID.equals(id)) {
				return true;
			}
		}
		return false;
	}

	// Gets the user by respective userID
	public User getUserFrom(String userID) {
		
		User thisUser = null;
		Enumeration en = rootNode.breadthFirstEnumeration();
		
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof User){
				thisUser = (User) nodeInfo;
				if (userID.equals(thisUser.getUserID())){
					return thisUser;
				}
			}
		}
		return thisUser;
	}

	// Gets selected user's node
	public User getSelectedUserNode() {
		if (currentNode instanceof User){
			currentUserNode = (User) currentNode;
		}
		return currentUserNode;
	}

	// Gets selected user group 
	public UserGroup getSelectedUserGroupNode() {
		if ((currentNode != null) && (currentNode instanceof UserGroup)){
			currentUserGroupNode = (UserGroup) currentNode;
		} 
		else {
			currentUserGroupNode = rootGroup;
		}
		return currentUserGroupNode;
	}

	// Checks if node allows children from user group
	public boolean isUserGroup() {
		if (node.getAllowsChildren()){
			return true;
		}
		return false;
	}

}