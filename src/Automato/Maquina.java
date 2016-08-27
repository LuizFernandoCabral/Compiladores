package Automato;

import java.util.List;
import java.util.ArrayList;

import org.omg.CosNaming.NamingContextPackage.NotFound;

public class Maquina {

	private final List<String> states;
	private final List<String> sFinals;
	private final List<Regras> rules;
	private final String name;
	private String inicial;
	
	public Maquina(String nome) {
		name = nome;
		states = new ArrayList<>();
		sFinals = new ArrayList<>();
		rules = new ArrayList<>();
	}
	
	public void addState(String estado) {
		states.add(estado);
	}
	
	public void addFinal(String estado) {
		sFinals.add(estado);
	}
	
	public void addRule(Regras R) {
		rules.add(R);
	}
	
	public Regras searchRule(String StEnt, String SyEnt) throws NotFound {
		Regras R = new Regras(StEnt, SyEnt, "");
		
		if (rules.contains(R)){
			return rules.get(rules.indexOf(R));
		}
		throw new NotFound();
	}
	
	public boolean isFinal (String state){
		return sFinals.contains(state);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || Maquina.class.isAssignableFrom(o.getClass())) {
			return false;
		}
		
		final Maquina other = (Maquina) o;
		
		return (this.name == other.name);
	}
	
	public String getInicial(){
		return inicial;
	}

	public String getName() {
		return name;
	}

	public void setInicial(String inicial) {
		this.inicial = inicial;
	}
}
