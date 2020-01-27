package com.secl.eservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Map<String, Object> entity = getUser(username);
		Collection<GrantedAuthority> grantedAuthoritiesList = getRoles(username);
		return new AuthUser(username, String.valueOf(entity.get("password")),
			String.valueOf(entity.get("status")).equalsIgnoreCase("Active") ? true : false,
			String.valueOf(entity.get("account_expired")).equalsIgnoreCase("Yes") ? false : true,
			String.valueOf(entity.get("credentials_expired")).equalsIgnoreCase("Yes") ? false : true,
			String.valueOf(entity.get("account_locked")).equalsIgnoreCase("Yes") ? false : true,
			grantedAuthoritiesList);
	}
	
	
	private Map<String, Object> getUser(String username) throws UsernameNotFoundException {
		Map<String, Object> entity = null;
		String query = "select password, status, account_expired, credentials_expired, account_locked"
			+ " from " + Table.LOGIN 
			+ " where 1 = 1 and username = ?";
		try {
			entity = jdbcTemplate.queryForMap(query, new Object[] { username });
		} catch (Exception e) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		
		return entity;
	}
	
	private Collection<GrantedAuthority> getRoles(String username) throws UsernameNotFoundException {
		List<String> roles = null;
		String query = "SELECT r.oid"
			+ " from " + Table.LOGIN + " l, "
			+ Table.ROLE + " r "
			+ " where 1 = 1"
			+ " and r.oid = l.roleOid"
			+ " and l.username = ?";
		try {
			roles = jdbcTemplate.queryForList(query, new Object[] { username }, String.class);
			roles = ListUtils.emptyIfNull(roles);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Role not found for user " + username + " in the database");
		}
		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<GrantedAuthority>();
		for(String role : roles) {
			grantedAuthoritiesList.add(new SimpleGrantedAuthority(role));
		}
		return grantedAuthoritiesList;
	}
	
}
