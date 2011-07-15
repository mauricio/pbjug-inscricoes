package br.com.pbjug;

public class Inscrito implements Comparable<Inscrito> {

	private int id;
	private String firstName;
	private String lastName;
	private String email;

	public String getInicial() {		
		return Utils.transliterate( this.firstName.toLowerCase() ).substring(0, 1).toUpperCase();		
	}
	
	@Override
	public int hashCode() {
		return this.getFullName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
	
		boolean result = false;
		
		if ( obj instanceof Inscrito) {
		
			Inscrito i = (Inscrito) obj;
			
			result = this.getId() == i.getId() && this.getFullName().equals( i.getFullName() ) && this.getEmail().equals( i.getEmail() );
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return String.format( "%s - %s - %s", this.getId(), this.getFullName(), this.getEmail() );
	}
	
	@Override
	public int compareTo(Inscrito other) {		
		return Utils.transliterate( this.getFullName().toLowerCase() ).compareToIgnoreCase( Utils.transliterate( other.getFullName().toLowerCase() ) );
	}
	
	public String getFullName() {
		return String.format( "%s %s", this.firstName, this.lastName ); 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
