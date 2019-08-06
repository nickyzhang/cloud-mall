package com.cloud.api.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Id implements Serializable{
    private long machine;
    private long seq;
    private long time;
    private long genMethod;
    private long type;
    private long version;

    public Id(){

    }

    public Id(long machine, long seq, long time, long genMethod, long type, long version) {
        this.machine = machine;
        this.seq = seq;
        this.time = time;
        this.genMethod = genMethod;
        this.type = type;
        this.version = version;
    }
}
