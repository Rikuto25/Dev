package com.ipsghokdings.shoppingAns;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemoDAO {

	@Autowired
	SqlSession sqlsession;

	// メモ一覧取得
	public List<MemoDTO> list() {
		//selectList()メソッドの第一引数にはnamespace.idを、第２引数にはMapperに渡す変数
		return sqlsession.selectList("memo.list", "");
	}

	// メモ一件登録
	public void insert(MemoDTO dto) {
		sqlsession.insert("memo.insert", dto);
	}

	// メモ詳細取得
	public MemoDTO detail(int idx) {
		return sqlsession.selectOne("memo.detail", idx);
	}

	// メモ一件更新
	public void update(MemoDTO dto) {
		sqlsession.update("memo.update", dto);
	}

	// メモ一件削除
	public void delete(int idx) {
		sqlsession.delete("memo.delete", idx);
	}
}
