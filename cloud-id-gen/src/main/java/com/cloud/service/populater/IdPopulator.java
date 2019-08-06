package com.cloud.service.populater;

import com.cloud.api.model.Id;
import com.cloud.service.model.IdMeta;

public interface IdPopulator {

    void populateId(Id id, IdMeta idMeta);

}
