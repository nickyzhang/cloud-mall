package com.cloud.service.factory;

import com.cloud.api.service.IdGenService;
import com.cloud.service.impl.IdGenServiceImpl;
import com.cloud.service.provider.DbMachineIdProvider;
import com.cloud.service.provider.IpConfigurableMachineIdProvider;
import com.cloud.service.provider.PropertyMachineIdProvider;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

@Data
public class IdServiceFactoryBean implements FactoryBean<IdGenService> {

    protected final Logger log = LoggerFactory.getLogger(IdServiceFactoryBean.class);

    public enum Type {
        PROPERTY, IP_CONFIGURABLE, DB
    }

    private Type providerType;

    private long machineId;

    private String ips;

    private String dbUrl;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    private long genMethod = -1;
    private long type = -1;
    private long version = -1;

    private IdGenService idGenService;

    public void init() {
        if (providerType == null) {
            log.error("The type of Id service is mandatory.");
            throw new IllegalArgumentException(
                    "The type of Id service is mandatory.");
        }

        switch (providerType) {
            case PROPERTY:
                idGenService = constructPropertyIdService(machineId);
                break;
            case IP_CONFIGURABLE:
                idGenService = constructIpConfigurableIdService(ips);
                break;
            case DB:
                idGenService = constructDbIdService(dbUrl, dbName, dbUser, dbPassword);
                break;
        }
    }


    @Override
    public IdGenService getObject() throws Exception {
        return idGenService;
    }

    @Override
    public Class<?> getObjectType() {

        return IdGenService.class;
    }

    @Override
    public boolean isSingleton() {

        return true;
    }


    private IdGenService constructPropertyIdService(long machineId) {
        log.info("Construct Property IdGenService machineId {}", machineId);
        PropertyMachineIdProvider propertyMachineIdProvider = new PropertyMachineIdProvider();
        propertyMachineIdProvider.setMachineId(machineId);
        IdGenServiceImpl idGenService = new IdGenServiceImpl();
        idGenService.setMachineIdProvider(propertyMachineIdProvider);
        if (genMethod != -1) {
            idGenService.setGenMethod(genMethod);
        }

        if (type != -1) {
            idGenService.setType(type);
        }

        if (version != -1) {
            idGenService.setVersion(version);
        }
        idGenService.init();
        return idGenService;
    }


    private IdGenService constructIpConfigurableIdService(String ips) {
        log.info("Construct Ip Configurable IdService ips {}", ips);

        IpConfigurableMachineIdProvider ipConfigurableMachineIdProvider = new IpConfigurableMachineIdProvider(
                ips);

        IdGenServiceImpl idServiceImpl = new IdGenServiceImpl();
        idServiceImpl.setMachineIdProvider(ipConfigurableMachineIdProvider);
        if (genMethod != -1) {
            idServiceImpl.setGenMethod(genMethod);
        }
        if (type != -1) {
            idServiceImpl.setType(type);
        }
        if (version != -1) {
            idServiceImpl.setVersion(version);
        }
        idServiceImpl.init();

        return idServiceImpl;
    }

    private IdGenService constructDbIdService(String dbUrl, String dbName,
                                           String dbUser, String dbPassword) {
        log.info("Construct Db IdService dbUrl {} dbName {} dbUser {} dbPassword {}", dbUrl, dbName, dbUser, dbPassword);

        HikariDataSource dataSource = new HikariDataSource();
        String url = String.format("jdbc:mysql://%s/%s?useUnicode=true&amp;characterEncoding=utfmb4&amp;autoReconnect=true", dbUrl, dbName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        dataSource.setMaximumPoolSize(65);
        dataSource.setMinimumIdle(2);
        dataSource.setIdleTimeout(180000);
        dataSource.setMaxLifetime(180000);
        dataSource.setConnectionTimeout(30000);


        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setLazyInit(false);
        jdbcTemplate.setDataSource(dataSource);

        DbMachineIdProvider dbMachineIdProvider = new DbMachineIdProvider();
        dbMachineIdProvider.setJdbcTemplate(jdbcTemplate);
        dbMachineIdProvider.init();

        IdGenServiceImpl idServiceImpl = new IdGenServiceImpl();
        idServiceImpl.setMachineIdProvider(dbMachineIdProvider);
        if (genMethod != -1) {
            idServiceImpl.setGenMethod(genMethod);
        }
        if (type != -1) {
            idServiceImpl.setType(type);
        }
        if (version != -1) {
            idServiceImpl.setVersion(version);
        }
        idServiceImpl.init();

        return idServiceImpl;
    }
}
