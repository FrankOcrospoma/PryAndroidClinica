package fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

public class CustomTextInputEditText extends TextInputEditText {


    public CustomTextInputEditText(Context context) {
        super(context);
        init();

    }

    public CustomTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CustomTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setLineSpacing(0, 0.9f);
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Medium.ttf");
            setTypeface(tf);
        }
    }
}
