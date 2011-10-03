package br.com.sapereaude.teste;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class MaskedEditText extends EditText implements TextWatcher {

	private String mask;
	private char charRepresentation;
	private int[] rawToMasked;
	private String rawText;
	private boolean editingBefore;
	private boolean editingOnChanged;
	private boolean editingAfter;
	private int[] maskedToRaw;
	private char[] charsInMask;
	private int selection;
	private boolean initialized;
	
//	public MaskedEditText(Context context) {
//		super(context);
//		init();
//		mask = "";
//		charRepresentation = "";
//	}
	
	public MaskedEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialized = false;
		init();
		
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText);
		mask = attributes.getString(R.styleable.MaskedEditText_mask);
		String representation = attributes.getString(R.styleable.MaskedEditText_char_representation);
		
		if(representation == null) {
			charRepresentation = '#';
		}
		else {
			charRepresentation = representation.charAt(0);
		}
		
		generatePositionArrays();
		
		rawText = "";
//		completeWithWhitespaces("", mask.length());
		selection = rawToMasked[0];
		this.setText(mask.replace(charRepresentation, ' '));
		initialized = true;
	}

	private String completeWithWhitespaces(String string, int size) {
		while(string.length() < size) {
			string = string.concat(" ");
		}
		return string;
	}

	private void generatePositionArrays() {
		int[] aux = new int[mask.length()];
		maskedToRaw = new int[mask.length()];
		String charsInMaskAux = "";
		
		int charIndex = 0;
		for(int i = 0; i < mask.length(); i++) {
			char currentChar = mask.charAt(i);
			if(currentChar == charRepresentation) {
				aux[charIndex] = i;
				maskedToRaw[i] = charIndex++;
			}
			else {
				String charAsString = Character.toString(currentChar);
				if(!charsInMaskAux.contains(charAsString)) {
					charsInMaskAux = charsInMaskAux.concat(charAsString);
				}
				maskedToRaw[i] = -1;
			}
		}
		charsInMask = charsInMaskAux.toCharArray();
		
		rawToMasked = new int[charIndex];
		for (int i = 0; i < charIndex; i++) {
			rawToMasked[i] = aux[i];
		}
	}
	
	private void init() {
		addTextChangedListener(this);
		editingAfter = false;
		editingBefore = false;
		editingOnChanged = false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		if(!editingBefore) {
			editingBefore = true;
			StringRange range = calculateRange(start, start + count);
			if(range.getStart() != -1) {
				rawText = range.subtractFromString(rawText);
			}
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(!editingOnChanged && editingBefore) {
			editingOnChanged = true;
			if(count > 0) {
				StringRange range = new StringRange();
				range.setStart(maskedToRaw[nextValidPosition(start)]);
				String addedString = s.subSequence(start, start + count).toString();
				rawText = range.addToString(rawText, clear(addedString));
				if(initialized) {
					selection = nextValidPosition(start + count);
				}
			}
		}
	}

	private int nextValidPosition(int currentPosition) {
		while(maskedToRaw[currentPosition] == -1) {
			currentPosition++;
		}
		return currentPosition;
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(!editingAfter && editingBefore && editingOnChanged) {
			editingAfter = true;
			setText(makeMaskedText());
			
			setSelection(selection);
			editingBefore = false;
			editingOnChanged = false;
			editingAfter = false;
		}
	}
	
	private String makeMaskedText() {
		char[] maskedText = mask.replace(charRepresentation, ' ').toCharArray();
		for(int i = 0; i < rawToMasked.length; i++) {
			if(i < rawText.length()) {
				maskedText[rawToMasked[i]] = rawText.charAt(i);
			}
			else {
				maskedText[rawToMasked[i]] = ' ';
			}
		}
		return new String(maskedText);
	}

	private StringRange calculateRange(int start, int end) {
		StringRange range = new StringRange();
		for(int i = start; i < start + end && i < mask.length(); i++) {
			if(maskedToRaw[i] != -1) {
				if(range.getStart() == -1) {
					range.setStart(maskedToRaw[i]);
				}
				range.setEnd(maskedToRaw[i]);
			}
		}
		return range;
	}
	
	private String clear(String string) {
		for(char c : charsInMask) {
			string = string.replace(Character.toString(c), "");
		}
		return string;
	}
}
