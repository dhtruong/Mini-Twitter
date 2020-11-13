package miniTwitter;

public class Admin {

	private User user;
	private UserGroup group;
	private EntryVisitor entryVisitor;
	private TreeView jtree = new TreeView();

	// Initialize adminVisitor
	public Admin() {
		group = new UserGroup();
		entryVisitor = new EntryVisitor();
		group.accept(entryVisitor);
	}

	// Add a new user
	public void addNewUser(String userID) {
		
		// Check if the user exists else add a new user
		if (jtree.nodeExists(userID)) {
			throw new IllegalArgumentException("This user already exists"); 
		} 
		else {
			user = new User();
			group = jtree.getSelectedUserGroupNode();
			user.addUser(user, userID, group.getGroupID());
			group.addUserToGroup(user);
			jtree.addObject(user.getUserID(), user);
			user.accept(entryVisitor);
		}
	}

	// Add a new user group
	public void addNewUserGroup(String groupID) {
		
		if (jtree.nodeExists(groupID)) {
			throw new IllegalArgumentException("This group already exists"); 
		} 
		else {
			UserGroup parentGroup = group;
			group = new UserGroup();
			if (jtree.getParentGroup() == null) {
				group.setGroup(groupID, parentGroup);
			} 
			else {
				group.setGroup(groupID, (UserGroup) jtree.getParentGroup());
			}
			jtree.addObject(group.getGroupID(), group);
			group.accept(entryVisitor);
		}
	}

	// Create User UI for User
	public void getSelectedUserView(User user) {
		if (user != null) {
			UserPanel newUserUI = new UserPanel(user, jtree);
			user.setUserUI(newUserUI);
		}
	}

	public int getTotalUsers() {
		return entryVisitor.getTotalUser();
	}

	public int getTotalUserGroups() {
		return entryVisitor.getTotalGroup();
	}

	public int getTotalMessages() {
		return entryVisitor.getTotalMessages();
	}

	public double getTotalPositiveMessages() {
		return entryVisitor.getPositivePercentage();
	}

	public void setTree(TreeView jtree) {
		this.jtree = jtree;
	}
	
}