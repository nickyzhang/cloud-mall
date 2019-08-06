package com.cloud.service.populater;

import com.cloud.api.model.Id;
import com.cloud.service.model.IdMeta;
import com.cloud.service.model.IdType;
import com.cloud.util.TimeUtils;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicIdPopulator implements IdPopulator, ResetPopulator {

    class Variant {

        private long sequence = 0;
        private long lastTimestamp = -1;

    }

    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());

    public AtomicIdPopulator() {
        super();
    }

    @Override
    public void populateId(Id id, IdMeta idMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;

        while (true) {

            // Save the old variant
            varOld = variant.get();

            // populate the current variant
            timestamp = TimeUtils.genTime(IdType.parse(id.getType()));
            TimeUtils.validateTimestamp(varOld.lastTimestamp, timestamp);

            sequence = varOld.sequence;

            if (timestamp == varOld.lastTimestamp) {
                sequence++;
                sequence &= idMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = TimeUtils.tillNextTimeUnit(varOld.lastTimestamp, IdType.parse(id.getType()));
                }
            } else {
                sequence = 0;
            }

            // Assign the current variant by the atomic tools
            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                id.setSeq(sequence);
                id.setTime(timestamp);

                break;
            }

        }
    }

    @Override
    public void reset() {
        variant = new AtomicReference<Variant>(new Variant());
    }

}
