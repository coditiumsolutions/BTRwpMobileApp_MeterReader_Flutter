package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class bystring extends Activity {
    public static final String DB_NAME = "mrs.sqlite";
    public static final String TABLE_NAME = "Sheet";

    private EditText etSearch;
    private Spinner spSearchField;
    private TextView tvRecordCount;
    private ListView lvRecords;
    private SQLiteDatabase database;
    private final List<RowData> rows = new ArrayList<>();
    private RowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bystring);

        etSearch = (EditText) findViewById(R.id.etSearch);
        spSearchField = (Spinner) findViewById(R.id.spSearchField);
        tvRecordCount = (TextView) findViewById(R.id.tvRecordCount);
        lvRecords = (ListView) findViewById(R.id.lvRecords);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Both", "Refrence", "Meter"}
        );
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearchField.setAdapter(filterAdapter);
        spSearchField.setSelection(0);

        applyDoneActionToEditableFields(findViewById(android.R.id.content));

        adapter = new RowAdapter(rows);
        lvRecords.setAdapter(adapter);
        lvRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowData row = rows.get(position);
                Intent intent = new Intent(bystring.this, bySerial.class);
                intent.putExtra("ROW_ID", row.id);
                startActivity(intent);
            }
        });
    }

    private void applyDoneActionToEditableFields(View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            if (editText.isEnabled()) {
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean isDoneAction = actionId == EditorInfo.IME_ACTION_DONE
                                || (actionId == EditorInfo.IME_NULL && event != null
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                        if (isDoneAction) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            }
                            v.clearFocus();
                            return true;
                        }
                        return false;
                    }
                });
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                applyDoneActionToEditableFields(group.getChildAt(i));
            }
        }
    }

    public void searchRecords(View view) {
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        rows.clear();

        String query = etSearch.getText().toString().trim();
        Cursor c;
        if (TextUtils.isEmpty(query)) {
            c = database.rawQuery(
                    "SELECT ID, Refrence, Meter, Plot, Street FROM " + TABLE_NAME + " ORDER BY Refrence ASC",
                    null
            );
        } else {
            String like = "%" + query + "%";
            String selectedFilter = spSearchField.getSelectedItem().toString();
            if ("Refrence".equals(selectedFilter)) {
                c = database.rawQuery(
                        "SELECT ID, Refrence, Meter, Plot, Street FROM " + TABLE_NAME
                                + " WHERE Refrence LIKE ? ORDER BY Refrence ASC",
                        new String[]{like}
                );
            } else if ("Meter".equals(selectedFilter)) {
                c = database.rawQuery(
                        "SELECT ID, Refrence, Meter, Plot, Street FROM " + TABLE_NAME
                                + " WHERE Meter LIKE ? ORDER BY Refrence ASC",
                        new String[]{like}
                );
            } else {
                c = database.rawQuery(
                        "SELECT ID, Refrence, Meter, Plot, Street FROM " + TABLE_NAME
                                + " WHERE Refrence LIKE ? OR Meter LIKE ? ORDER BY Refrence ASC",
                        new String[]{like, like}
                );
            }
        }

        int sr = 1;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    RowData row = new RowData();
                    row.id = c.getLong(c.getColumnIndex("ID"));
                    row.sr = String.valueOf(sr++);
                    row.reference = c.getString(c.getColumnIndex("Refrence"));
                    row.meter = c.getString(c.getColumnIndex("Meter"));
                    row.plot = c.getString(c.getColumnIndex("Plot"));
                    row.street = c.getString(c.getColumnIndex("Street"));
                    rows.add(row);
                } while (c.moveToNext());
            }
            c.close();
        }

        tvRecordCount.setText(rows.size() + " record(s)");
        adapter.notifyDataSetChanged();
    }

    private static class RowData {
        long id;
        String sr;
        String reference;
        String meter;
        String plot;
        String street;
    }

    private class RowAdapter extends BaseAdapter {
        private final List<RowData> data;

        RowAdapter(List<RowData> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return data.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(bystring.this).inflate(R.layout.item_bystring_row, parent, false);
            }

            RowData row = data.get(position);
            ((TextView) view.findViewById(R.id.tvSr)).setText(row.sr);
            ((TextView) view.findViewById(R.id.tvRef)).setText(row.reference);
            ((TextView) view.findViewById(R.id.tvMeter)).setText(row.meter);
            ((TextView) view.findViewById(R.id.tvPlot)).setText(row.plot);
            ((TextView) view.findViewById(R.id.tvStreet)).setText(row.street);
            return view;
        }
    }
}
