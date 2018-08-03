package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.SpinnerHelper;
import hu.molti.specialevents.service.PersonService;

public class PersonSelectorAdapter extends RecyclerView.Adapter<PersonSelectorAdapter.SelectorViewHolder> {
    private PersonService personService;
    private List<String> personIds;

    public PersonSelectorAdapter() {
        personService = PersonService.getService();
        personIds = new ArrayList<>();
        _addOne();
    }

    public void addOne() {
        _addOne();
        dataSetChanged();
    }

    public List<String> getPersonIds() {
        return personIds;
    }

    public void removeOne() {
        personIds.remove(personIds.size() - 1);
        dataSetChanged();
    }

    public void setPersons(List<String> personIds) {
        this.personIds = new ArrayList<>(personIds);
        dataSetChanged();
    }

    public class SelectorViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        public Spinner spinner;
        public int idx = 0;

        SelectorViewHolder(View view) {
            super(view);
            spinner = SpinnerHelper.createSpinner(view, R.id.person_selector_spinner, personService.getAll());
            spinner.setOnItemSelectedListener(this);
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            int pos = spinner.getSelectedItemPosition();
            personIds.set(idx, personService.get(pos).getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    @NonNull
    @Override
    public SelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_selector_row, parent, false);

        return new SelectorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectorViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.idx = position;
        holder.spinner.setSelection(personService.getIdx(personIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return personIds.size();
    }

    private void _addOne() {
        personIds.add(personService.get(0).getId());
    }

    private void dataSetChanged() {
        super.notifyDataSetChanged();
    }
}
