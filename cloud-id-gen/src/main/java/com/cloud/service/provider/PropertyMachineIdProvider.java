package com.cloud.service.provider;

public class PropertyMachineIdProvider implements MachineIdProvider {
    private long machineId;

    @Override
    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
