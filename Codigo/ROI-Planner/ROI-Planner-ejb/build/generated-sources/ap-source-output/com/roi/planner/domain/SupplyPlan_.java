package com.roi.planner.domain;

import com.roi.planner.domain.NetworkSection;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-29T22:26:36")
@StaticMetamodel(SupplyPlan.class)
public class SupplyPlan_ { 

    public static volatile SingularAttribute<SupplyPlan, Boolean> eliminated;
    public static volatile SingularAttribute<SupplyPlan, Integer> orderId;
    public static volatile ListAttribute<SupplyPlan, NetworkSection> sectionLst;
    public static volatile SingularAttribute<SupplyPlan, Long> id;

}