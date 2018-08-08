package Util.donny;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.widget.Button;

/**
 * Created by donny on 5/24/18.
 */

public class CommonFunc {

    public static String getHexColorRGB(Context context, int resid) {
        return String.format("#%06x", ContextCompat.getColor(context, resid) & 0xffffff);
    }

    public static String getHexColorARGB(Context context, int resid) {
        return String.format("#%08x", ContextCompat.getColor(context, resid));
    }

    public static void setStateButton(Button btn, String name, String state, String color) {
        btn.setText(Html.fromHtml(name + ": " + "<font color='" + color + "'>" + state + "</font>"));
    }

    static int BytesToInt32(byte bytes[], int off) {
        return (int) (
                (bytes[off + 0] << 24) & 0xff000000 |
                        (bytes[off + 1] << 16) & 0x00ff0000 |
                        (bytes[off + 2] << 8) & 0x0000ff00 |
                        (bytes[off + 3] << 0) & 0x000000ff
        );
    }

    public static short BytesToInt16(byte bytes[], int off) {
        return (short) (
                (bytes[off + 1] << 8) & 0xff00 |
                        (bytes[off]) & 0x00ff
        );
    }

}
