package com.example.loginandregister;

import com.google.android.material.textfield.TextInputLayout;

public class ControleSaisie {


    public ControleSaisie() {

        private boolean validLogin(TextInputLayout strLogin) {
//            strLogin = login.getEditText().getText().toString().trim().toLowerCase();
            if(strLogin.isEmpty()) {
                login.setError("Le login doit être renseigné");
                login.requestFocus();
                return false;
            } else if (strLogin.length() > 10) {
                login.setError("Le login est trop long");
                return false;
            } else {
                login11.setError(null);
                return true;
            }
        }
    }
}
