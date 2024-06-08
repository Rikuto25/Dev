package com.ipsghokdings.shoppingAns;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class GuestDAO {

	@Autowired
	SqlSession sqlsession;

//	ゲストブック一覧取得
	public List<GuestDTO> guestlist(){
		//selectList()メソッドの第一引数にはnamespace.idを、第２引数にはMapperに渡す変数
		return sqlsession.selectList("memo.guestlist", "");
	}


//	ゲストブック一件登録
	public void  guestinsert(GuestDTO dto) {
		 sqlsession.insert("memo.guestinsert", dto);
	}


//	ゲストブック詳細取得
	public GuestDTO guestdetail(int idx) {
		return sqlsession.selectOne("memo.guestdetail", idx);
	}


//	ゲストブック一件更新
	public void  guestupdate(GuestDTO dto) {
		 sqlsession.update("memo.guestupdate", dto);
		 }


//	ゲストブック一件削除
	public void  guestdelete(int idx) {
		 sqlsession.delete("memo.guestdelete", idx);
	}
}
