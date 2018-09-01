package hu.molti.specialevents.activity.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import hu.molti.specialevents.R;

public class ConfirmDialogFragment extends DialogFragment {
    private String message;
    private DialogInterface.OnClickListener onOkListener;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOnOkListener(DialogInterface.OnClickListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage(message)
                .setPositiveButton(R.string.ok, onOkListener)
                .setNegativeButton(R.string.cancel_something, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }
}
