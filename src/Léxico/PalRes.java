package Léxico;

public enum PalRes {
	A("PROGRAM"),
	B("END"),
	C("DECLARE"),
	D("num"),
	E("bool"),
	F("char"),
	G("void"),
	H("read"),
	I("print"),
	J("if"),
	K("else"),
	L("while"),
	M("goto"),
	N("call"),
	O("return"),
	P("true"),
	Q("false"),
	R("EE");
	
	private final String text;
	
	PalRes(String txt){
		text = txt;
	}
	
	public static boolean search (String other) {
		for (PalRes pal : values()) {
			if (pal.text.equals(other)){
				return true;
			}
		}
		return false;
	}
}
