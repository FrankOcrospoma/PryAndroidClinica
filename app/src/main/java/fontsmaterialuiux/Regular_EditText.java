package fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.textfield.TextInputEditText;
import android.util.AttributeSet;

public class Regular_EditText extends AppCompatEditText
{
    public Regular_EditText(Context context) {
        super(context);
        init();
    }

    public Regular_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Regular_EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLineSpacing(0, 0.9f);
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Regular.ttf");
            setTypeface(tf);
        }
    }

}
