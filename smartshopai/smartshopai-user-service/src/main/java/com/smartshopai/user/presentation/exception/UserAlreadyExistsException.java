package com.smartshopai.user.presentation.exception;

import com.smartshopai.common.exception.BusinessException;

/**
 * Thrown when attempting to create a user that already exists (by username or e-mail).
 */
public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
