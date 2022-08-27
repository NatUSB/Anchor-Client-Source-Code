package com.anchorclient.client.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private String uuid, username, email, password;
    private boolean checkedSkin;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
