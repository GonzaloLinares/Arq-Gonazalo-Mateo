package com.roi.pipelinemonitor.domain;

import com.roi.pipelinemonitor.domain.Pipeline;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-29T22:26:34")
@StaticMetamodel(Sensor.class)
public class Sensor_ { 

    public static volatile SingularAttribute<Sensor, Long> id;
    public static volatile SingularAttribute<Sensor, Double> pressureValue;
    public static volatile SingularAttribute<Sensor, Date> valueDate;
    public static volatile SingularAttribute<Sensor, Pipeline> pipelineId;

}