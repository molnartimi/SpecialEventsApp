package hu.molti.specialevents.activity.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.entity.GiftEntity;
import hu.molti.specialevents.service.GiftService;

public class SaveGiftDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {
    private GiftService giftService;
    private GiftEntity gift;

    public SaveGiftDialogFragment() {
        giftService = GiftService.getService();
    }

    @Override
    public void setArguments(Bundle bundle) {
        gift = giftService.get(bundle.getString("id"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.new_gift_dialog, null);
        final EditText nameText = myView.findViewById(R.id.new_gift_name_text);
        if (gift != null) {
            nameText.setText(gift.getName());
        }

        builder.setView(myView)
                .setTitle((gift == null) ? R.string.save_gift_dialog_title : R.string.edit_gift_dialog_title)
                .setPositiveButton(R.string.save_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (gift == null) {
                            giftService.add(new GiftEntity(giftService.getPersonId(), nameText.getText().toString()));
                        } else {
                            gift.setName(nameText.getText().toString());
                            giftService.update(gift);
                        }

                    }
                })
                .setNegativeButton(R.string.cancel_something, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
