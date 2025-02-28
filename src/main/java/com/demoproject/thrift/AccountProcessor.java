package com.demoproject.thrift;

import com.demoproject.controller.AccountBC;
import com.demoproject.thrift.account.AccountService;
import com.demoproject.thrift.account.TAccount;
import com.demoproject.thrift.account.TAccountListResult;
import com.demoproject.thrift.account.TAccountResult;
import com.demoproject.thrift.tcommon.TResponseInfo;
import java.nio.ByteBuffer;
import java.util.List;

public class AccountProcessor implements AccountService.Iface {

    @Override
    public boolean ping() {
        return true;
    }

    @Override
    public boolean create(final TAccount value) {
        System.out.println("create " + value);
        return AccountBC.getInstance().create(value);
    }

    @Override
    public boolean update(final TAccount value) {
        return AccountBC.getInstance().update(value);
    }

    @Override
    public boolean remove(final int id) {
        return AccountBC.getInstance().remove(id);
    }

    @Override
    public TAccountResult getById(final String id) {
        return AccountBC.getInstance().getById(id);
    }

    @Override
    public TAccountResult getByName(final String name) {
        return AccountBC.getInstance().getByName(name);
    }

    @Override
    public TAccountListResult getListkeys(List<String> listKeys) {
        return AccountBC.getInstance().getListkeys(listKeys);
    }

    @Override
    public TAccountListResult getAll() {
        return AccountBC.getInstance().getAll();
    }

    @Override
    public TAccountListResult getMulti(final String whereClause, final int offset, final int count) {
        return AccountBC.getInstance().getMulti(whereClause, offset, count);
    }

    @Override
    public boolean checkExistUserName(String displayName) {
        return AccountBC.getInstance().checkExistUserName(displayName);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return AccountBC.getInstance().checkExistEmail(email);
    }

    @Override
    public TResponseInfo pushMessage(String token, int moduleId, String playerId, int msgType, int length, ByteBuffer msgData) {
        return null;
    }
}
