package hu.molti.specialevents;

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

import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.service.PersonService;

public class SavePersonDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {
    private PersonService personService;
    private PersonEntity person;

    public SavePersonDialogFragment() {
        personService = PersonService.getService();
    }

    @Override
    public void setArguments(Bundle bundle) {
        person = personService.get(bundle.getString("id"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.new_person_dialog, null);
        final EditText nameText = myView.findViewById(R.id.new_person_name_text);
        if (person != null) {
            nameText.setText(person.getName());
        }

        builder.setView(myView)
                .setTitle(R.string.save_person_dialog_title)
                .setPositiveButton(R.string.save_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (person == null) {
                            personService.add(new PersonEntity(nameText.getText().toString()));
                        } else {
                            person.setName(nameText.getText().toString());
                            personService.update(person);
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
