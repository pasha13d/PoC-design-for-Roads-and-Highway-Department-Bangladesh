package com.secl.eservice.master.document.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.document.request.UploadRequestBody;
import com.secl.eservice.util.constant.Constant;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class UploadQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> uploadDocumentSql(AuthUser user, UploadRequestBody body, String fileName){

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("qc_report_path = ?", "updated_by = ?", "updated_on = " + now.getLeft());
		List<Object> data = Lists.newArrayList(fileName, user.getUsername(), now.getRight());

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.ROAD + Table.ROAD + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
//		data.add(body.getOid());
//		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
		}
	}
