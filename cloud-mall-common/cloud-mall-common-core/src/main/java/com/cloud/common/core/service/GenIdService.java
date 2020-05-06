package com.cloud.common.core.service;

import com.cloud.service.IdGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenIdService {

    @Autowired
    private IdGenService idGenService;

    public Long genId() {

        return idGenService.genId();
    }
}
