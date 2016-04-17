package friends.friendPage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import derekhsieh.derekhsiehapp.R;

/**
 * Created by phoenix on 3/30/16.
 * Custom dialog that has a rating bar to get the user's rating of and image.
 */
public class ImageRatingDialog extends Dialog {

    OnImageRatingDialogResult mDialogResult;

    public ImageRatingDialog(Context context) {
        super(context);
    }

    // Allows the dialog to communicate data to the activity. Need to implement this method in the activity.
    public interface OnImageRatingDialogResult {
        void finish(Float result);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_rate_image);
        setTitle("Rate Image");

        final RatingBar imageRatingBar = (RatingBar) findViewById(R.id.imageRatingBar);
        Button rateImageDialogCancelButton = (Button) findViewById(R.id.rateImageDialogCancelButton);

        // if cancel button is clicked, just close dialog
        rateImageDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRatingDialog.this.dismiss();
            }
        });

        Button rateImageDialogOKButton = (Button) findViewById(R.id.rateImageDialogOKButton);
        // if ok button is clicked, pass the rating
        rateImageDialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogResult.finish(imageRatingBar.getRating());
                ImageRatingDialog.this.dismiss();
            }
        });
    }

    // method for calling the interface
    public void setDialogResult(OnImageRatingDialogResult dialogResult){
        mDialogResult = dialogResult;
    }


}
