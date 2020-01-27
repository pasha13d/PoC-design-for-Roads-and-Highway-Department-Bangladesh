package com.secl.eservice.master.document.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.master.document.request.UploadRequestBody;
import com.secl.eservice.master.document.util.UploadQuery;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.IdGenerator;

@Repository("masterDocumentV1UploadDao")
public class UploadDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Transactional
	public void uploadDocument(AuthUser user, UploadRequestBody body, String fileName) throws Exception {

		ImmutablePair<String, Object[]> query = UploadQuery.uploadDocumentSql(user, body, fileName);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		if(result != 1) {
			throw new Exception("No file uploaded!!!");
		}
	}
}
