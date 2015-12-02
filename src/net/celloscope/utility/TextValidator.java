package net.celloscope.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class TextValidator implements TextWatcher {
	private final TextView textView;

	public TextValidator(TextView textView) {
		this.textView = textView;
	}

	public abstract void validate(TextView textView, String text);

	@Override
	final public void afterTextChanged(Editable s) {
		String text = textView.getText().toString();
		validate(textView, text);
	}

	@Override
	final public void beforeTextChanged(CharSequence s, int start, int count,
			int after) { /* Don't care */
	}

	@Override
	final public void onTextChanged(CharSequence s, int start, int before,
			int count) { /* Don't care */
	}
	
	// Have to call this this way
	
//	editText.addTextChangedListener(new TextValidator(editText) {
//	    @Override public void validate(TextView textView, String text) {
//	       /* Validation code here */
//	    }
//	});
//	
	
}