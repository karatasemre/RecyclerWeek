package emre.com.weeklydatepicker;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class HelperManager {

    public static int getDeviceWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }
}
