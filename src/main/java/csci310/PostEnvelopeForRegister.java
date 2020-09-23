package csci310;

public class PostEnvelopeForRegister {
	private String username;
	private String password;
	
	public PostEnvelopeForRegister(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
}
