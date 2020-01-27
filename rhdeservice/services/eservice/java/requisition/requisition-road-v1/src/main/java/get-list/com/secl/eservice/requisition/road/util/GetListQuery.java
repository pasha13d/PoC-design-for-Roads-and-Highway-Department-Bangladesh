package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetListQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getRoadSql(){
		String sql = "select oid, " + 
				"(select divisionnamebn from "+ Table.DIVISION +" where oid= rr.divisionoid ) as division," + 
				"(select  districtnamebn from "+ Table.DISTRICT +" where oid= rr.districtoid ) as district," + 
				"(select upazillanamebn from " + Table.UPAZILLA +" where oid= rr.upazillaoid ) as upazilla,"+
				"postalcode, location, startPoint, endPoint, purpose, isriverOrWaterBodyNear, status, nextStep" +
				" from "+Table.ROAD+" rr where 1 = 1 order by createdon desc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}
