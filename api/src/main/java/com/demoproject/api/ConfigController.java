package com.demoproject.api;

import com.demoproject.helper.ConfigHelper;
import com.demoproject.utils.Libs;
import com.demoproject.entity.RequiredParams;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping(value = {"/master"}, produces = {"application/json; charset=utf-8"})
public class ConfigController {

    @CrossOrigin(origins = {"*"})
    @RequestMapping({"/config"})
    public String getConfig(RequiredParams requiredParams) {
        Map<String, Object> data = new HashMap<>();
        ConfigHelper config = new ConfigHelper(requiredParams.getVersion(), requiredParams.getDeviceid(), requiredParams.getPlatformid(), requiredParams.getToken());
        String strCheck = Libs.checkParam(requiredParams, new String[0]);
        if (!strCheck.isEmpty()) {
            return strCheck;
        }
        return Libs.cvDataString(config.dataReturn());
    }
    
    public Map<String, Object> dataReturn() {
        final Map<String, Object> data = new HashMap<>();
        final Map<String, Object> mapConfig = new HashMap<>();

        data.put("status", 1);
        data.put("data", mapConfig);
        return data;
    }
}
