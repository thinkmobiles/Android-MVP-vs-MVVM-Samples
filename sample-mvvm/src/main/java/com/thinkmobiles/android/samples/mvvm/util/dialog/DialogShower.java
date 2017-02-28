package com.thinkmobiles.android.samples.mvvm.util.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public final class DialogShower implements IDialogShower {

    private ProgressDialog progressDialog;

    @Override
    public void showProgress(Context _context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(_context);
            progressDialog.setMessage("Loading");
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
