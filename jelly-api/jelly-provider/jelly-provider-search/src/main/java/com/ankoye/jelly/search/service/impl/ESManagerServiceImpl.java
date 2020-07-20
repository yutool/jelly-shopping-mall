package com.ankoye.jelly.search.service.impl;

import com.ankoye.jelly.search.common.async.AsyncImportData;
import com.ankoye.jelly.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 */
@Component
public class ESManagerServiceImpl implements ESManagerService {
    @Autowired
    private AsyncImportData importData;

    @Override
    public void importSku() {
        importData.importSku();
    }
}
