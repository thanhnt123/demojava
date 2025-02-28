package com.demoproject.thrift;

import com.demoproject.thrift.account.TAccount;
import com.demoproject.thriftclient.AccountClient;

public class AccountServiceAdapter {

    private static final AccountClient accountClient = new AccountClient("thrift_account_service");

    public static boolean createAccount(TAccount tAccount) {
        System.out.println("accountClient " + accountClient.ping());
        return accountClient.create(tAccount);
    }

    public static boolean updateAccount(TAccount tAccount) {
        return accountClient.update(tAccount);
    }

    public static boolean removeAccount(int accountId) {
        return accountClient.remove(accountId);
    }
}
