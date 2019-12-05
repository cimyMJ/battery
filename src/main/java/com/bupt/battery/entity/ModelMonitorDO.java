package com.bupt.battery.entity;

import com.bupt.battery.entity.baseEntity.BaseEntity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "model_monitoring", schema = "baas", catalog = "")
public class ModelMonitorDO extends BaseEntity<Long> implements Serializable {
    @Column(name = "model_id")
    private String modelId;
    private String creator;
    @Column(name = "create_time")
    private Timestamp createTime;
    private String status;
    @Column(name = "port_id")
    private Integer portId;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;

}