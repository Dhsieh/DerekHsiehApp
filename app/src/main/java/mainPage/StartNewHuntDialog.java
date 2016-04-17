package mainPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by derekhsieh on 4/13/16.
 */
public class StartNewHuntDialog extends AlertDialog {
    public StartNewHuntDialog(Context context) {
        super(context);
    }

    public interface StartNewHuntDialogResult{
        void finish(String friend);
    }

    @Override
    public void onCreate(Bundle savedInstance){
        setTitle("Pick a Friend!");

    }
}
