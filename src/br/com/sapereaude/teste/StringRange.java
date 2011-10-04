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
		
		if(start > 0 && start <= string.length()) {
			firstPart = string.substring(0, start);
		}
		if(end >= 0 && end < string.length()) {
			lastPart = string.substring(end, string.length());
		}
		return firstPart.concat(lastPart);
	}

	public String addToString(String string, String what, int maxLength) {
		String firstPart = "";
		String lastPart = "";
		
		if(start > 0) {
			firstPart = string.substring(0, start);
		}
		if(start >= 0 && start < string.length()) {
			lastPart = string.substring(start, string.length());
		}
		if(string.length() + what.length() > maxLength) {
			what = what.substring(0, maxLength - string.length());
		}
		return firstPart.concat(what).concat(lastPart);		
	}
}
