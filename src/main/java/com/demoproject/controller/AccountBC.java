package com.demoproject.controller;

import com.demoproject.framework.gearman.GClientManager;
import com.demoproject.framework.gearman.JobEnt;
import com.demoproject.framework.util.JsonUtil;
import com.demoproject.framework.util.UuidUtil;
import com.demoproject.helper.ConfigInfo;
import com.demoproject.thrift.account.EJobType;
import com.demoproject.thrift.account.TAccount;
import com.demoproject.thrift.account.TAccountListResult;
import com.demoproject.thrift.account.TAccountResult;
import java.util.List;
import org.gearman.client.GearmanJob;

public class AccountBC {

    private static AccountBC instance = null;

    public static AccountBC getInstance() {
        if (instance == null) {
            instance = new AccountBC();
        }

        return instance;
    }

    public boolean create(TAccount value) {
        System.out.println("AccountBC create " + value);
        boolean indexRedis = true;
        //push data to redis

        if (indexRedis) {
            JobEnt job = new JobEnt();
            job.uuid = UuidUtil.makeString();
            job.data = JsonUtil.serialize(value);
            job.type = EJobType.CREATE;

            if (!GClientManager.getInstance(ConfigInfo.GEARMAN_SERVER).push(job, ConfigInfo.FUNCTION_ACCOUNT, GearmanJob.JobPriority.NORMAL)) {
                //delete key Redis
                return false;
            }
        }
        return true;
    }

    public boolean update(TAccount value) {
        boolean indexRedis = true;
        //push data to redis

        if (indexRedis) {
            JobEnt job = new JobEnt();
            job.uuid = UuidUtil.makeString();
            job.data = JsonUtil.serialize(value);
            job.type = EJobType.UPDATE;

            if (!GClientManager.getInstance(ConfigInfo.GEARMAN_SERVER).push(job, ConfigInfo.FUNCTION_ACCOUNT, GearmanJob.JobPriority.NORMAL)) {
                //delete key Redis
                return false;
            }
        }
        return true;
    }

    public boolean remove(int id) {
        boolean removeRedis = true;
        //push data to redis

        if (removeRedis) {
            JobEnt job = new JobEnt();
            job.uuid = UuidUtil.makeString();
            job.data = JsonUtil.serialize(id);
            job.type = EJobType.DELETE;

            if (!GClientManager.getInstance(ConfigInfo.GEARMAN_SERVER).push(job, ConfigInfo.FUNCTION_ACCOUNT, GearmanJob.JobPriority.NORMAL)) {
                //delete key Redis
                return false;
            }
        }
        return true;
    }

    public TAccountResult getById(String id) {
        return null;
    }

    public TAccountResult getByName(final String name) {
        return null;
    }

    public TAccountListResult getListkeys(final List<String> listKeys) {
        return null;
    }

    public TAccountListResult getAll() {
        return null;
    }

    public TAccountListResult getMulti(final String whereClause, final int offset, final int count) {
        return null;
    }

    public boolean checkExistUserName(String displayName) {
        return false;
    }

    public boolean checkExistEmail(String email) {
        return false;
    }

}
