package com.cloud.service.converter;

import com.cloud.api.model.Id;

public interface IdConverter {

    public long convert(Id id);

    public Id convert(long id);

}
