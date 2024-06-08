package com.ipsghokdings.guestbook;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO {

	@Autowired
	SqlSession sqlsession;

	// ログイン	
	public LoginDTO getPasswdByIdx(LoginDTO dto) {
		return sqlsession.selectOne("memo.login", dto);
	}

	// 一件登録
	public void insert(LoginDTO dto) {
		sqlsession.insert("memo.memberinsert", dto);
	}
}
