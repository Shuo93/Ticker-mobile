package com.ticker.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.ticker.common.model.Box;
import com.ticker.common.service.Area;
import com.ticker.common.service.BoxCalculator;
import com.ticker.common.service.PocketCalculator;
import com.ticker.common.util.DataLoader;
import com.ticker.mobile.model.SpinnerItem;

public class BoxActivity extends AppCompatActivity {

    private static final String PACKAGE_NAME = "com.ticker.mobile";

    private static final String RES_TYPE = "string";

    public static final String MESSAGE = "com.ticker.mobile.box.type";

    private BoxCalculator calculator;

    private Box.Menu menu = DataLoader.getInstance().getMenu().getBox();

    private EditText lengthEditText;
    private EditText widthEditText;
    private EditText heightEditText;
    private EditText numEditText;
    private CheckBox knifeCheckBox;
    private CheckBox embossCheckBox;
    private Spinner sizeSpinner;
    private Spinner materialSpinner;
    private Spinner weightSpinner;
    private CheckBox printCheckBox;
    private CheckBox corrugateCheckBox;
    private CheckBox laminateCheckBox;
    private Switch goldSwitch;
    private EditText goldEditText;
    private Spinner goldSpinner;
    private Switch uvSwitch;
    private EditText uvEditText;
    private Spinner uvSpinner;
    private EditText scaleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);
        Intent intent = getIntent();
        String type = intent.getStringExtra(MESSAGE);
        if ("box".equals(type)) {
            calculator = new BoxCalculator();
            setTitle(R.string.box);
        } else if ("pocket".equals(type)) {
            calculator = new PocketCalculator();
            setTitle(R.string.pocket);
        } else {
            calculator = new BoxCalculator();
            setTitle(R.string.box);
        }
        init();
    }

    private void init() {
        lengthEditText = findViewById(R.id.lengthEditText);
        widthEditText = findViewById(R.id.widthEditText);
        heightEditText = findViewById(R.id.heightEditText);
        numEditText = findViewById(R.id.numEditText);
        knifeCheckBox = findViewById(R.id.knifeCheckBox);
        knifeCheckBox.setChecked(true);
        embossCheckBox = findViewById(R.id.embossCheckBox);

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
        corrugateCheckBox = findViewById(R.id.corrugateCheckBox);
        laminateCheckBox = findViewById(R.id.laminateCheckBox);
        goldSwitch = findViewById(R.id.goldSwitch);
        goldEditText = findViewById(R.id.goldEditText);

        goldSpinner = findViewById(R.id.goldSpinner);
        ArrayAdapter<SpinnerItem> goldAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getGold()) {
            goldAdapter.add(new SpinnerItem(id, getResStr(id)));
        }
        goldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goldSpinner.setAdapter(goldAdapter);

        uvSwitch = findViewById(R.id.uvSwitch);
        uvEditText = findViewById(R.id.uvEditText);

        uvSpinner = findViewById(R.id.uvSpinner);
        ArrayAdapter<SpinnerItem> uvAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (String id : menu.getUv()) {
            uvAdapter.add(new SpinnerItem(id, getResStr(id)));
        }
        uvAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uvSpinner.setAdapter(uvAdapter);

        scaleEditText = findViewById(R.id.scaleEditText);
    }

    private String getSpinnerSelectedItemId(Spinner spinner) {
        return ((SpinnerItem) spinner.getSelectedItem()).getId();
    }

    private String getResStr(String id) {
        return getString(getResources().getIdentifier(id, RES_TYPE, PACKAGE_NAME));
    }

    public void calculate(View view) {
        Area area = new Area();
        area.setHeight(Double.parseDouble(heightEditText.getText().toString().isEmpty()
                ? "0" : heightEditText.getText().toString()));
        area.setWidth(Double.parseDouble(widthEditText.getText().toString().isEmpty()
                ? "0" : widthEditText.getText().toString()));
        area.setDepth(Double.parseDouble(lengthEditText.getText().toString().isEmpty()
                ? "0" : lengthEditText.getText().toString()));
        calculator.setArea(area);
        calculator.setNumber(Integer.parseInt(numEditText.getText().toString()));
        calculator.setEmboss(embossCheckBox.isChecked());
        calculator.setSize(getSpinnerSelectedItemId(sizeSpinner));
        calculator.setMaterial(getSpinnerSelectedItemId(materialSpinner));
        calculator.setWeight(getSpinnerSelectedItemId(weightSpinner));
        calculator.setPrint(printCheckBox.isChecked());
        calculator.setCorrugate(corrugateCheckBox.isChecked());
        calculator.setLaminate(laminateCheckBox.isChecked());
        calculator.setGold(goldSwitch.isChecked());
        if (goldSwitch.isChecked()) {
            calculator.setGoldArea(Double.parseDouble(goldEditText.getText().toString().isEmpty()
                    ? "0" : goldEditText.getText().toString()));
            calculator.setGoldSize(getSpinnerSelectedItemId(goldSpinner));
        }
        calculator.setUv(uvSwitch.isChecked());
        if (uvSwitch.isChecked()) {
            calculator.setUvArea(Double.parseDouble(uvEditText.getText().toString().isEmpty()
                    ? "0" : uvEditText.getText().toString()));
            calculator.setUvSize(getSpinnerSelectedItemId(uvSpinner));
        }
        calculator.setScale(Double.parseDouble(scaleEditText.getText().toString().isEmpty()
                ? "0" : scaleEditText.getText().toString()));
        double result = calculator.calculate();
        ResultDialogFragment dialog = new ResultDialogFragment();
        dialog.setResult(result);
        dialog.show(getSupportFragmentManager(), "result");
    }
}
