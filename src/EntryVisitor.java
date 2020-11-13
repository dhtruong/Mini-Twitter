package miniTwitter;

public class EntryVisitor implements Visitor {

	private int totalUser, totalGroup;
	private int totalMessages = 0;
	private double totalPositive = 0.0;
	private String[] positiveWords = { "friendly", "kind", "beautiful", "happy" , "good"};

	@Override
	public void visitUser(User user) {
		totalUser++;
	}

	public int getTotalUser() {
		return totalUser;
	}

	@Override
	public void visitUserGroups(UserGroup userGroup) {
		totalGroup++;
	}

	public int getTotalGroup() {
		return totalGroup;
	}

	// Separate messages into arrays, while checking for positive messages to get positive percentage
	@Override
	public void visitMessages(String messages) {
		boolean isPositive = false;
		String[] messageArray = messages.toLowerCase().split(" ");
		for (String s : messageArray) {
			for (String t : positiveWords) {
				if (s.equals(t)) {
					isPositive = true;
				}
			}
		}
		if (isPositive) {
			visitPercentage();
		}
		totalMessages++;
	}

	public int getTotalMessages() {
		return totalMessages;
	}

	@Override
	public void visitPercentage() {
		totalPositive++;
	}

	public double getPositivePercentage() {
		if (totalMessages == 0) {
			return 0;
		}
		return totalPositive / totalMessages * 100;
	}

}