package com.cloud.service.provider;

public interface MachineIdsProvider extends MachineIdProvider {

    long getNextMachineId();

}
