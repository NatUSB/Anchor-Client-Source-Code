package com.anchorclient.client.account;

import java.util.concurrent.CopyOnWriteArrayList;

public class AccountManager {

    public CopyOnWriteArrayList<Account> accounts = new CopyOnWriteArrayList<>();

    public AccountManager() {
        load();
    }

    public void load() {

    }

    public void save() {

    }

}
