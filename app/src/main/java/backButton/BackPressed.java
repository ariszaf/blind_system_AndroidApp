package backButton;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import function.MainActivityFunction;

//http://stacktips.com/tutorials/android/android-dialog-example
//http://www.journaldev.com/9463/android-alertdialog

/**
 * The type Back pressed Create a graphic dialog with user.
 */
public class BackPressed {
    private Activity mainActivity;
    private  MainActivityFunction mainActivityFunction;

    //create a lisener for yes
    /**
     * for yes button exit from application
     * */

    private final class YesListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mainActivityFunction.exitFromTheApplication();
        }
    }

    /**
     * for the no button cancel the dialog stay in application
     * */

    //create a lisener for cancel
    private final class NoListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /**
     * Instantiates a new Back pressed.
     *
     * @param mainActivity         the main activity
     * @param mainActivityFunction the main activity function
     */
    public BackPressed(Activity mainActivity, MainActivityFunction mainActivityFunction) {
        this.mainActivity = mainActivity;
        this.mainActivityFunction = mainActivityFunction;
    }

    /**
     * Create exit dialog.
     * set message.
     * set cancelable the Dialog.
     * Inisialize the button that will show.
     */
    public void createExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new YesListener());
        builder.setNegativeButton("Cancel", new NoListener());
        AlertDialog alert = builder.create();
        alert.show();
    }
}