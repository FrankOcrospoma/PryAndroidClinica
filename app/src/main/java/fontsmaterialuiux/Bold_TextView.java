package fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.textfield.TextInputEditText;
import android.util.AttributeSet;

public class Bold_TextView extends AppCompatTextView {
    public Bold_TextView(Context context) {
        super(context);
        init();
    }

    public Bold_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bold_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLineSpacing(0, 0.9f);
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Bold.ttf");
            setTypeface(tf);
        }
    }
}