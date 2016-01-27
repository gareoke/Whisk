package windylabs.com.whisk.views.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by g.anderson on 1/27/16.
 */
public class CreateTitleTextView extends TextView {
    public CreateTitleTextView(Context context) {
        super(context);

        Typeface face=Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Bold.ttf");
        setTypeface(face);
    }

    public CreateTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CreateTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
