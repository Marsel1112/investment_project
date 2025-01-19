package org.invest.exception.UserResours;

import lombok.Getter;
import org.invest.dto.LoginUser;

@Getter
public class NotFoundUserException extends RuntimeException{
    private LoginUser loginUser;

    public NotFoundUserException(String message, LoginUser loginUser) {
        super(message);
        this.loginUser = loginUser;
    }
}
