package br.com.sapereaude.teste;

public class StringRange {
	private int start;
	private int end;
	
	public StringRange() {
		start = -1;
		end = -1;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}

	public String subtractFromString(String string) {
		String firstPart = "";
		String lastPart = "";
		
		if(start - 1 > 0) {
			firstPart = string.substring(0, start - 1);
		}
		if(end < string.length() - 1) {
			lastPart = string.substring(end, string.length() - 1);
		}
		return firstPart.concat(lastPart);
	}

	public String addToString(String string, String what) {
		String firstPart = "";
		String lastPart = "";
		
		if(start - 1 > 0) {
			firstPart = string.substring(0, start - 1);
		}
		if(start < string.length() - 1) {
			lastPart = string.substring(start, string.length() - 1);
		}
		return firstPart.concat(what).concat(lastPart);		
	}
}
