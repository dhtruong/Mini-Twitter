package miniTwitter;

public interface Users {

	public void setTimeStamp();
	public long getTimeStamp();
	public void accept(Visitor visitor);

}