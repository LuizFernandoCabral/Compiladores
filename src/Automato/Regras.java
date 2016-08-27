package Automato;

public class Regras {
	
	private final String StEnt;
	private String SyEnt;
	private final String out;
	private String Aut;

	public Regras(String StEnt, String SyEnt, String out) {
		this.StEnt = StEnt;
		this.SyEnt = SyEnt;
		this.Aut = "";
		this.out = out;
	}
	
	public Regras(String StEnt, String automato, String out, boolean Automato) {
		this.StEnt = StEnt;
		this.SyEnt = "";
		this.Aut = automato;
		this.out = out;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !Regras.class.isAssignableFrom(o.getClass())) {
			return false;
		}

		final Regras other = (Regras) o;
		
		return (this.StEnt.equals(other.StEnt) && (this.SyEnt.equals(other.SyEnt) || !this.Aut.isEmpty()));
	}

	public String getOut() {
		return out;
	}
	
	public String toString(){
		String str = "(" + StEnt + ", " + SyEnt + Aut + ") -> " +out; 
		return str;
	}

}
