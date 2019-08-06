package com.cloud.service.impl;

import com.cloud.api.model.Id;
import com.cloud.service.model.IdType;
import com.cloud.service.populater.AtomicIdPopulator;
import com.cloud.service.populater.IdPopulator;
import com.cloud.service.populater.LockIdPopulator;
import com.cloud.service.populater.SyncIdPopulator;
import com.cloud.util.CommonUtils;
import lombok.Data;

@Data
public class IdGenServiceImpl extends AbstractIdGenServiceImpl {

    private static final String SYNC_LOCK_IMPL_KEY = "cloud.sync.lock.impl.key";

    private static final String ATOMIC_IMPL_KEY = "cloud.atomic.impl.key";

    protected IdPopulator idPopulator;

    public IdGenServiceImpl() {
        super();

        initPopulator();
    }

    public IdGenServiceImpl(String type) {
        super(type);

        initPopulator();
    }

    public IdGenServiceImpl(IdType type) {
        super(type);

        initPopulator();
    }

    public void initPopulator() {
        if(idPopulator != null){
            log.info("The " + idPopulator.getClass().getCanonicalName() + " is used.");
        } else if (CommonUtils.isPropKeyOn(SYNC_LOCK_IMPL_KEY)) {
            log.info("The SyncIdPopulator is used.");
            idPopulator = new SyncIdPopulator();
        } else if (CommonUtils.isPropKeyOn(ATOMIC_IMPL_KEY)) {
            log.info("The AtomicIdPopulator is used.");
            idPopulator = new AtomicIdPopulator();
        } else {
            log.info("The default LockIdPopulator is used.");
            idPopulator = new LockIdPopulator();
        }
    }

    @Override
    protected void populateId(Id id) {
        idPopulator.populateId(id, this.idMeta);
    }
}
