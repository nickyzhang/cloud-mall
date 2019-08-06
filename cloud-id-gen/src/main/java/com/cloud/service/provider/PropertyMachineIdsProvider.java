package com.cloud.service.provider;

public class PropertyMachineIdsProvider implements MachineIdsProvider {

    private long[] machineIds;

    private int currentIndex;

    @Override
    public long getNextMachineId() {
        return getMachineId();
    }

    @Override
    public long getMachineId() {
        return machineIds[currentIndex++%machineIds.length];
    }

    public void setMachineIds(long[] machineIds) {
        this.machineIds = machineIds;
    }
}
