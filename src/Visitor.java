package miniTwitter;

public interface Visitor {

	public void visitUser(User user);
	public void visitUserGroups(UserGroup userGroup);
	public void visitMessages(String messages);
	public void visitPercentage();

}