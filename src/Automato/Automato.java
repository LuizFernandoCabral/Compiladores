package Automato;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Automato {
	
	protected Scanner sc;
	protected List<Maquina> automatos;
	protected Maquina MaqAux;

	public Automato(String FileName) {
		MaqAux = new Maquina("");
		automatos = new ArrayList<>();
		try {
			sc = new Scanner(new File(FileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
	}

	public void Construct() {
		String[] words;
		String line;
		
		while (sc.hasNext()){
			line = sc.nextLine();
			words = line.split(" ");
			CreateAuto(words);
		}
	}
	
	private void CreateAuto (String[] words) {
		switch (words[0]){
			case "A:":
				MaqAux = new Maquina(words[1]);
				break;
				
			case "S:":
				for (String word : words) {
					String state = word.replace(",","");
					if (!state.contains("S:")){
						MaqAux.addState(word);
					}
				}
				break;
				
			case "I:":
				MaqAux.setInicial(words[1]);
				break;
				
			case "F:":
				for (String word : words) {
					String fSt = word.replace(",", "");
					if (!fSt.contains("F:")){
						//System.out.println(word);
						MaqAux.addFinal(fSt);
					}
				}
				break;
				
			case "R:":
				for (int i = 1; i < words.length; i+=3) {
					String StEnt, SyEnt, out;
					StEnt = Buildrule (words[i]);
					SyEnt = Buildrule (words[i+1]);
					out = Buildrule (words[i+2]);

					if (!out.contains("EMP") && !out.contains("RET"))
						MaqAux.addRule(new Regras(StEnt, SyEnt, out));
					else
						MaqAux.addRule(new Regras(StEnt, SyEnt, out, true));
				}
				addAuto(MaqAux);
				break;
		}
		
	}
	
	private String Buildrule(String word) {		
		String str = word.replace("(", "");
		str = new String(str.replace(",", ""));
		str = new String(str.replace("{", ""));
		str = new String(str.replace(")", ""));
		str = new String(str.replace("}", ""));
		str = new String(str.replace(";", ""));
		return str;
	}
	
	private void addAuto(Maquina auto) {
		automatos.add(auto);
	}
	
	public Maquina AutoIni(){
		return automatos.get(0);
	}
	
}
