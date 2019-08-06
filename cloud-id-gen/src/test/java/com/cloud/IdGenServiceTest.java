package com.cloud;

import com.cloud.service.impl.IdGenServiceImpl;
import com.cloud.service.provider.PropertyMachineIdProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

public class IdGenServiceTest {

    public void genId() {
        IdGenServiceImpl idGenService = new IdGenServiceImpl();
        idGenService.setMachineIdProvider(propertyMachineIdProvider());
        idGenService.init();
        for (int i = 0; i < 100; i++)
            System.out.println(idGenService.genId());
    }

    public static void main(String[] args) {
        new IdGenServiceTest().genId();
    }

    public PropertyMachineIdProvider propertyMachineIdProvider() {
        Environment environment = new StandardEnvironment();
        PropertyMachineIdProvider machineIdProvider = new PropertyMachineIdProvider();
        String machineCount = environment.getProperty("vesta.machine");
        if (StringUtils.isBlank(machineCount)) {
            machineIdProvider.setMachineId(1L);
            return machineIdProvider;
        }
        machineIdProvider.setMachineId(Long.valueOf(machineCount));
        return machineIdProvider;
    }
}
