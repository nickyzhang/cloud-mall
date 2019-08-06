package com.cloud.service.populater;

import com.cloud.api.model.Id;
import com.cloud.service.model.IdMeta;

public class SyncIdPopulator extends BasePopulator {

    public SyncIdPopulator() {
        super();
    }

    @Override
    public synchronized void populateId(Id id, IdMeta idMeta) {
        super.populateId(id, idMeta);
    }

}
