package com.demoproject.function;

import com.demoproject.model.AccountDA;
import com.demoproject.framework.gearman.JobEnt;
import com.demoproject.framework.util.EncryptUtil;
import com.demoproject.framework.util.JsonUtil;
import com.demoproject.framework.util.LogUtil;
import com.demoproject.thrift.account.EJobType;
import com.demoproject.thrift.account.TAccount;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import org.apache.log4j.Logger;
import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.util.ByteUtils;
import org.gearman.worker.AbstractGearmanFunction;

public class AccountFunction extends AbstractGearmanFunction {

    private static final Logger logger = LogUtil.getLogger(AccountFunction.class);

    @Override
    public GearmanJobResult executeFunction() {
        String returnMsg = "";
        boolean result = true;
        try {
            String jobData = EncryptUtil.decodeUTF8((byte[]) this.data);
            logger.info("AccountFunction=" + jobData);

            Type type = new TypeToken<JobEnt<EJobType>>() {
            }.getType();
            JobEnt job = JsonUtil.deserialize(jobData, type);
            TAccount value = JsonUtil.deserialize(job.data, TAccount.class);

            switch ((EJobType) job.type) {
                case CREATE -> {
                    if (!value.getUsername().isEmpty()) {
                        result = AccountDA.getInstance().insert(value);
                    }
                }
                case UPDATE -> {
                    if (!value.getUsername().isEmpty()) {
                        result = AccountDA.getInstance().update(value);
                    }
                }
                case DELETE -> {
                    if (!value.getUsername().isEmpty()) {
                        result = AccountDA.getInstance().remove(value.getUsername());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            returnMsg = e.getMessage();
            result = false;
        }

        // return gearman job result
        byte[] returnData = result ? (byte[]) this.data : new byte[0];
        return new GearmanJobResultImpl(this.jobHandle, result, new byte[0], returnData, ByteUtils.toUTF8Bytes(returnMsg), 0, 0);
    }
}
