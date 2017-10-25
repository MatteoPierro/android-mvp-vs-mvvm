package com.matteopierro.login.viewmodel;

import com.example.matteopierro.login.R;

public enum FieldError {
    NO_ERROR(R.string.empty),
    REQUIRED(R.string.error_field_required),
    INVALID_USERNAME(R.string.error_invalid_username),
    WRONG_PASSWORD(R.string.error_wrong_password);

    private int stringId;

    FieldError(int stringId) {
        this.stringId = stringId;
    }

    public int stringId() {
        return stringId;
    }

    public boolean isVisible() {
        return stringId != R.string.empty;
    }
}
