package com.ticker.mobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.math.BigDecimal;

/**
 * Created by Shuo on 2017/12/31.
 */

public class ResultDialogFragment extends DialogFragment {

    private double result;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BigDecimal bigDecimal = new BigDecimal(result);
        String displayResult = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_result).setMessage(displayResult)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    public void setResult(double result) {
        this.result = result;
    }
}
