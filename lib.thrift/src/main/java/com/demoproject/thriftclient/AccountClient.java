package com.demoproject.thriftclient;

import com.demoproject.framework.thrift.client.TClientManager;
import com.demoproject.framework.thrift.client.TClientObject;
import com.demoproject.thrift.account.AccountService;
import com.demoproject.thrift.account.AccountService.Client;
import com.demoproject.thrift.account.TAccount;
import com.demoproject.thrift.account.TAccountListResult;
import com.demoproject.thrift.account.TAccountResult;
import com.demoproject.thrift.tcommon.EStatusResult;
import com.demoproject.thrift.tcommon.TResponseInfo;
import java.nio.ByteBuffer;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountClient implements AccountService.Iface {

    private static final Logger logger = LoggerFactory.getLogger(AccountClient.class);
    private final TClientManager clientManager;

    public AccountClient(String section) {
        clientManager = TClientManager.getInstance(section, AccountService.class);
    }

    @Override
    public boolean ping() {
        boolean result = false;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            System.out.println("ping true");
            if (tco != null) {
                result = tco.client.ping();
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("ping error", ex);
            clientManager.invalidate(tco);
            result = false;
        }
        return result;
    }

    @Override
    public boolean create(TAccount value) {
        boolean result = false;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.create(value);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("create error", ex);
            clientManager.invalidate(tco);
            result = false;
        }
        return result;
    }

    @Override
    public boolean update(TAccount value) {
        boolean result = false;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.update(value);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("update error", ex);
            clientManager.invalidate(tco);
            result = false;
        }
        return result;
    }

    @Override
    public boolean remove(int id) {
        boolean result = false;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.remove(id);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("remove error", ex);
            clientManager.invalidate(tco);
            result = false;
        }
        return result;
    }

    @Override
    public TAccountResult getById(String id) {
        TAccountResult result = null;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.getById(id);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("getById error", ex);
            clientManager.invalidate(tco);
            result = new TAccountResult();
        }
        return result;
    }

    @Override
    public TAccountResult getByName(String name) {
        TAccountResult result = null;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.getByName(name);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("getByName error", ex);
            clientManager.invalidate(tco);
            result = new TAccountResult();
        }
        return result;
    }

    @Override
    public TAccountListResult getListkeys(List<String> listKeys) {
        TAccountListResult result = null;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.getListkeys(listKeys);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("getListkeys error", ex);
            clientManager.invalidate(tco);
            result = new TAccountListResult(EStatusResult.FAIL, null, 0);
        }
        return result;
    }

    @Override
    public TAccountListResult getAll() {
        TAccountListResult result = null;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.getAll();
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("getAll error", ex);
            clientManager.invalidate(tco);
            result = new TAccountListResult(EStatusResult.FAIL, null, 0);
        }
        return result;
    }

    @Override
    public TAccountListResult getMulti(String whereClause, int offset, int count) {
        TAccountListResult result = null;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.getMulti(whereClause, offset, count);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("getMulti error", ex);
            clientManager.invalidate(tco);
            result = new TAccountListResult(EStatusResult.FAIL, null, 0);
        }
        return result;
    }

    @Override
    public boolean checkExistUserName(String userName) {
        Boolean result = false;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.checkExistUserName(userName);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("checkExistUserName error", ex);
            clientManager.invalidate(tco);
        }
        return result;
    }

    @Override
    public boolean checkExistEmail(String email) {
        Boolean result = false;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.checkExistEmail(email);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("checkExistEmail error", ex);
            clientManager.invalidate(tco);
        }
        return result;
    }

    @Override
    public TResponseInfo pushMessage(String token, int moduleId, String playerId, int msgType, int length, ByteBuffer msgData) {
        TResponseInfo result = null;
        TClientObject<Client> tco = clientManager.borrow();
        try {
            if (tco != null) {
                result = tco.client.pushMessage(token, moduleId, playerId, msgType, length, msgData);
                clientManager.giveBack(tco);
                return result;
            }
        } catch (TException ex) {
            logger.error("pushMessage error", ex);
            clientManager.invalidate(tco);
        }
        return result;
    }
}
