package com.ticker.mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.ticker.common.model.Book;
import com.ticker.common.service.BookCalculator;
import com.ticker.common.util.DataLoader;
import com.ticker.mobile.model.SpinnerItem;

public class BookActivity extends AppCompatActivity {

    private static final String PACKAGE_NAME = "com.ticker.mobile";

    private static final String RES_TYPE = "string";

    private Spinner areaSpinner;
    private EditText pageEditText;
    private EditText bookEditText;
    private Spinner sizeSpinner;
    private Spinner materialSpinner;
    private Spinner weightSpinner;
    private CheckBox printCheckBox;
    private CheckBox laminateCheckBox;
    private Switch bindSwitch;
    private Spinner bindTypeSpinner;
    private Spinner bindPriceSpinner;
    private EditText scaleEditText;

    private Book.Menu menu = DataLoader.getInstance().getMenu().getBook();

    private BookCalculator calculator = new BookCalculator();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        context = this;
        areaSpinner = findViewById(R.id.areaSpinner);
        ArrayAdapter<SpinnerItem> areaAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getK()) {
            String text = id.substring(0, id.length() - 1)
                    + getResStr(id.substring(id.length() - 1));
            areaAdapter.add(new SpinnerItem(id, text));
        }
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        pageEditText = findViewById(R.id.pageEditText);
        bookEditText = findViewById(R.id.bookEditText);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        ArrayAdapter<SpinnerItem> sizeAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getSize()) {
            sizeAdapter.add(new SpinnerItem(id, getResStr(id)));
        }
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        materialSpinner = findViewById(R.id.materialSpinner);
        ArrayAdapter<SpinnerItem> materialAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getMaterial()) {
            materialAdapter.add(new SpinnerItem(id, getResStr(id)));
        }
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(materialAdapter);

        weightSpinner = findViewById(R.id.weightSpinner);
        ArrayAdapter<SpinnerItem> weightAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getWeight()) {
            String text = id.substring(0, id.length() - 1)
                    + getResStr(id.substring(id.length() - 1));
            weightAdapter.add(new SpinnerItem(id, text));
        }
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);

        printCheckBox = findViewById(R.id.printCheckBox);
        laminateCheckBox = findViewById(R.id.laminateCheckBox);
        bindSwitch = findViewById(R.id.bindSwitch);

        bindTypeSpinner = findViewById(R.id.bindTypeSpinner);
        bindPriceSpinner = findViewById(R.id.bindPriceSpinner);
        ArrayAdapter<SpinnerItem> bindTypeAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getBind()) {
            bindTypeAdapter.add(new SpinnerItem(id, getResStr(id)));
        }
        bindTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bindTypeSpinner.setAdapter(bindTypeAdapter);
        bindTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getItemAtPosition(position);
                ArrayAdapter<SpinnerItem> bindPriceAdapter =
                        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
                for (String key : menu.getBindKey().get(item.getId())) {
                    bindPriceAdapter.add(new SpinnerItem(key, key));
                }
                bindPriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bindPriceSpinner.setAdapter(bindPriceAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        scaleEditText = findViewById(R.id.scaleEditText);
    }

    public void calculate(View view) {
        calculator.setK(getSpinnerSelectedItemId(areaSpinner));
        calculator.setPageNum(Integer.parseInt(pageEditText.getText().toString().isEmpty()
                ? "0" : pageEditText.getText().toString()));
        calculator.setBookNum(Integer.parseInt(bookEditText.getText().toString().isEmpty()
                ? "0" : bookEditText.getText().toString()));
        calculator.setSize(getSpinnerSelectedItemId(sizeSpinner));
        calculator.setMaterial(getSpinnerSelectedItemId(materialSpinner));
        calculator.setWeight(getSpinnerSelectedItemId(weightSpinner));
        calculator.setPrint(printCheckBox.isChecked());
        calculator.setLaminate(laminateCheckBox.isChecked());
        calculator.setBind(bindSwitch.isChecked());
        if (bindSwitch.isChecked()) {
            calculator.setBindType(getSpinnerSelectedItemId(bindTypeSpinner));
            calculator.setBindKey(getSpinnerSelectedItemId(bindPriceSpinner));
        }
        calculator.setScale(Double.parseDouble(scaleEditText.getText().toString().isEmpty()
                ? "0" : scaleEditText.getText().toString()));
        double result = calculator.calculate();
        ResultDialogFragment dialog = new ResultDialogFragment();
        dialog.setResult(result);
        dialog.show(getSupportFragmentManager(), "result");
    }

    private String getSpinnerSelectedItemId(Spinner spinner) {
        return ((SpinnerItem) spinner.getSelectedItem()).getId();
    }

    private String getResStr(String id) {
        return getString(getResources().getIdentifier(id, RES_TYPE, PACKAGE_NAME));
    }
}
