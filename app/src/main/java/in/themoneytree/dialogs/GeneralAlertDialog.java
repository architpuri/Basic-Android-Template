package in.themoneytree.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class GeneralAlertDialog extends android.support.v4.app.DialogFragment {

    public GeneralAlertDialog() {
    }

    public static GeneralAlertDialog newInstance(String message) {

        Bundle args = new Bundle();
        args.putString(message, "Message");
        GeneralAlertDialog fragment = new GeneralAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("Message");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                Intent data = new Intent();
                data.putExtra("markAttendance", true);
                getActivity().setResult(Activity.RESULT_OK, data);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alertDialogBuilder.create();
    }

}
