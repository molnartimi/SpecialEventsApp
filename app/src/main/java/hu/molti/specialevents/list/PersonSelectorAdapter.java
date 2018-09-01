package hu.molti.specialevents.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.SpinnerHelper;

public class PersonSelectorAdapter extends BaseListAdapter {
    private List<String> personIds;

    public PersonSelectorAdapter() {
        super();
        personIds = new ArrayList<>();
        _addOne();
    }

    public class SelectorViewHolder extends BaseListAdapter.ViewHolder implements AdapterView.OnItemSelectedListener {
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

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public void setPersonDropDown(int personIdx) {
            this.spinner.setSelection(personIdx);
        }
    }

    @Override
    protected SelectorViewHolder getNewViewHolder(View itemView) {
        return new SelectorViewHolder(itemView);
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.person_selector_row;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SelectorViewHolder selectorHolder = (SelectorViewHolder) viewHolder;
        selectorHolder.setIdx(position);
        selectorHolder.setPersonDropDown(personService.getIdx(personIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return personIds.size();
    }

    public List<String> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<String> personIds) {
        this.personIds = new ArrayList<>(personIds);
        notifyDataSetChanged();
    }

    public void addOne() {
        _addOne();
        notifyDataSetChanged();
    }

    public void removeOne() {
        personIds.remove(personIds.size() - 1);
        notifyDataSetChanged();
    }

    private void _addOne() {
        personIds.add(personService.get(0).getId());
    }
}
