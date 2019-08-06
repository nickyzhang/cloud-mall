package com.cloud.common.core.config;

import com.cloud.api.service.IdGenService;
import com.cloud.service.factory.IdServiceFactoryBean;
import com.cloud.service.provider.PropertyMachineIdProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DistributedIdGenConfig {

    @Autowired
    Environment environment;

    @Bean
    public IdGenService idGenService() {
        IdServiceFactoryBean factory = new IdServiceFactoryBean();
        factory.setProviderType(IdServiceFactoryBean.Type.PROPERTY);
        factory.setMachineId(propertyMachineIdProvider().getMachineId());
        factory.init();
        return factory.getIdGenService();
    }


    @Bean
    public PropertyMachineIdProvider propertyMachineIdProvider(){
        PropertyMachineIdProvider machineIdProvider = new PropertyMachineIdProvider();
        String machineCount = environment.getProperty("cloud.machine");
        if (StringUtils.isBlank(machineCount)) {
            machineIdProvider.setMachineId(1L);
            return machineIdProvider;
        }
        machineIdProvider.setMachineId(Long.valueOf(machineCount));
        return machineIdProvider;
    }
}
