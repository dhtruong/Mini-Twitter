package miniTwitter;

import java.util.ArrayList; 

public class UserGroup implements Users {

	private String groupID;
	private UserGroup parentGroup;
	private ArrayList<User> userList;
	private long creationTime;

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setParentGroup(UserGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	public UserGroup getParentGroup() {
		return parentGroup;
	}

	public void setGroup(String id, UserGroup parent) {
		userList = new ArrayList<User>();
		setGroupID(id);
		setParentGroup(parent);
		setTimeStamp();
	}

	public void addUserToGroup(User user) {
		userList.add(user);
	}

	// Checks if the user exists in the respective group
	public boolean userExists(User user) {
		for (User u : userList) {
			if (u.equals(user)) {
				return true;
			}
		}
		return false;
	}

	// Gets user from group
	public User getUser(String user) {
		for (User u : userList) {
			if (u.getUserGroupID().equals(user)) {
				return u;
			}
		}
		return null;
	}

	// Get a list of users in the group
	public ArrayList<User> getUserList() {
		return userList;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitUserGroups(this);
	}

	@Override
	public void setTimeStamp() {
		creationTime = System.currentTimeMillis();
	}

	@Override
	public long getTimeStamp() {
		return creationTime;
	}

}