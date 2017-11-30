package com.roi.supplies.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-29T22:26:38")
@StaticMetamodel(SupplyOrder.class)
public class SupplyOrder_ { 

    public static volatile SingularAttribute<SupplyOrder, Long> serviceEndPointId;
    public static volatile SingularAttribute<SupplyOrder, Long> clientId;
    public static volatile SingularAttribute<SupplyOrder, Integer> payDay;
    public static volatile SingularAttribute<SupplyOrder, Long> id;
    public static volatile SingularAttribute<SupplyOrder, Integer> volumeContracted;
    public static volatile SingularAttribute<SupplyOrder, Date> supplyingStart;

}