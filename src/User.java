package miniTwitter;

import java.util.ArrayList;  
import java.util.Observer;
import java.util.Observable;

public class User extends Observable implements Users, Observer {

	private User user;
	private Visitor visitor;
	private String userID;
	private String userGroupID;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<String> messages;
	private ArrayList<String> newsFeed;
	public UserPanel userUI;
	private long creationTime, lastUpdateTime;

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserUI(UserPanel userUI) {
		this.userUI = userUI;
	}

	public void setUserGroupID(String groupID) {
		this.userGroupID = groupID;
	}

	public String getUserGroupID() {
		return userGroupID;
	}

	// Add user
	public void addUser(User user, String userID, String groupID) {
		this.user = user;
		this.setUserID(userID);
		this.setUserGroupID(groupID);
		this.followers = new ArrayList<User>();
		this.followings = new ArrayList<User>();
		this.newsFeed = new ArrayList<String>();
		this.messages = new ArrayList<String>();
		user.addObserver(user);
		setTimeStamp();
	}

	// Add user to the respective follower's list
	public void addFollower(User follower) {
		if (userExists(followers, follower) == false) {
			followers.add(follower);
		}
	}

	// Add user to the respective following list
	public void addFollowing(User following) {
		if (!userExists(followings, following)) {
			followings.add(following);
			following.addObserver(user);
		}
	}

	// Gets a list of users and their followers
	public ArrayList<User> getFollowing() {
		return followings;
	}

	// Add a new message to news feed and notify the followers
	public void addNews(String message) {
		newsFeed.add(user.userID + " : " + message);
		visitor.visitMessages(message);
		setChanged();
		notifyObservers(newsFeed.get(newsFeed.size() - 1));
	}

	// Gets latest messages
	public ArrayList<String> getLatestMessage() {
		if (messages.size() > 0) {
			return messages;
		}
		return null;
	}

	// Checks if user is already being followed or is following
	public boolean userExists(ArrayList<User> followers, User user) {
		for (User u : followers) {
			if (u.equals(user)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(Observable observable, Object object) {
		messages.add(object.toString());
		lastUpdateTime = System.currentTimeMillis();
		userUI.refresh();
	}

	@Override
	public void accept(Visitor visitor) {
		this.visitor = visitor;
		visitor.visitUser(this);
	}

	@Override
	public void setTimeStamp() {
		creationTime = System.currentTimeMillis();
	}

	@Override
	public long getTimeStamp() {
		return creationTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	
}