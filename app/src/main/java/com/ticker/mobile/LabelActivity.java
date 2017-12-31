package com.ticker.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ticker.common.service.LabelCalculator;
import com.ticker.common.util.DataLoader;
import com.ticker.mobile.model.SpinnerItem;

import java.util.ArrayList;
import java.util.List;

public class LabelActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.ticker.mobile.label_message";

    private static final String PACKAGE_NAME = "com.ticker.mobile";

    private static final String RES_TYPE = "string";

    private DataLoader dataLoader = DataLoader.getInstance();

    private LabelCalculator calculator = new LabelCalculator();

    private Spinner spinner;

    private EditText areaEditText;

    private EditText scaleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        spinner = findViewById(R.id.typeSpinner);
        List<String> typeList = dataLoader.getMenu().getLabel().getType();
        List<SpinnerItem> itemList = new ArrayList<>();
        for (String type : typeList) {
            itemList.add(new SpinnerItem(type, getResStr(type)));
        }
        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        areaEditText = findViewById(R.id.goldEditText);
        scaleEditText = findViewById(R.id.scaleEditText);
    }

    public void calculate(View view) {
        calculator.setType(((SpinnerItem) spinner.getSelectedItem()).getId());
        calculator.setArea(Double.parseDouble(areaEditText.getText().toString()));
        calculator.setScale(Double.parseDouble(scaleEditText.getText().toString()));
        double result = calculator.calculate();
        ResultDialogFragment dialog = new ResultDialogFragment();
        dialog.setResult(result);
        dialog.show(getSupportFragmentManager(), "result");
    }

    private String getResStr(String id) {
        return getString(getResources().getIdentifier(id, RES_TYPE, PACKAGE_NAME));
    }
}
