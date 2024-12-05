package it.sicilia.regione.gekoddd.geko.programmazione.domain.command;

public class Command {
	private String role;
	private String label;
	private String uri;
	
	
	public Command(String role, String label) {
		super();
		this.role = role;
		this.label = label;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	@Override
	public String toString() {
		return "Command [uri=" + uri + "]";
	}
	
	

}
