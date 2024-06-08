package com.ipsghokdings.shoppingAns;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("memo/*")
public class MemoController {

	@Autowired
	MemoDAO memodao;
	MemoDTO memodto;
	
	@Autowired
	LoginDAO logindao;
	
	@Autowired
	SqlSession sqlsession;


	
//	ログイン画面遷移(localhost:8082/memo/初期画面)
	@RequestMapping("*")
	public String loginCheck(HttpServletRequest request) {
		System.out.println("loginCheck入りましたか？");
		String url = request.getRequestURI();
		HttpSession session = request.getSession();
			if(session == null) {
				System.out.println("エラーです。ログインしてください。"); 
				return "login"; 
				}
			return "redirect:/" + url;
		}
	
	

	
//	Home画面遷移(header.htmlのHomeリンク)
	@RequestMapping("home")
	public ModelAndView goHome(HttpServletRequest request,
			ModelAndView mv) {
		HttpSession session = request.getSession();
		LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
		if(logindto == null) {
			System.out.println("エラーです。ログインしてください。");
			mv.addObject("errmsg", "エラーです。ログインしてください。");
			mv.setViewName("login");
			return mv;
		}
		mv.setViewName("home");
		return mv;
		}
	
//	メモ一覧画面遷移(header.htmlのメモ帳リンク)
	@RequestMapping("memojoin")
	public ModelAndView memojoin(HttpServletRequest request, ModelAndView mv) {
		HttpSession session = request.getSession();
		LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
		if(logindto == null) {
			System.out.println("エラーです。ログインしてください。");
			mv.addObject("errmsg", "エラーです。ログインしてください。");
			mv.setViewName("login");
			return mv;
		}
		
		mv.setViewName("memojoin");
		mv.addObject("logindto", logindto.getName());
		return mv;
		}
	
//	会員登録画面遷移(rogin.htmlの会員登録ボタン)
	@RequestMapping("registerrogin")
	public String memberInsert() {
		System.out.println("会員登録画面行けますか？");
		return "registerrogin";
		}
	
	
	
	

//	メモ一覧取得
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(defaultValue = "") ModelAndView mv,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
		if(logindto == null) {
			System.out.println("エラーです。ログインしてください。");
			mv.setViewName("login");
			return mv;
		}
//		list.htmlに送る
		mv.setViewName("test");
//		Daoから取得した商品情報リストをModelAndviewに[list]という名前で格納
		mv.addObject("logindto", logindto.getName());
		mv.addObject("memolist", memodao.list());
		System.out.println(memodao.list());
		System.out.println("遷移先：" + mv.getViewName());

		return mv;
		}


	

//	メモ一件登録
	@RequestMapping("insert")
	public ModelAndView insert(@RequestParam String writer, String memo, ModelAndView mv,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
		if(logindto == null) {
			System.out.println("エラーです。ログインしてください。");
			mv.addObject("errmsg", "エラーです。ログインしてください。");
			mv.setViewName("login");
			return mv;
		}
		
		MemoDTO memodto = new MemoDTO();
		memodto.setWriter(writer);
		memodto.setMemo(memo);
		
		memodao.insert(memodto);
		mv.addObject("logindto", logindto.getName());
		mv.setViewName("memojoin");
		return mv;
		}

	
//	メモ詳細取得
	@RequestMapping("memodetail/{idx}")
	public String memodetail(@PathVariable("idx") int idx, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");

		MemoDTO memodto = memodao.detail(idx);
	
		boolean namecheck = false;
		if(logindto.getName().equals(memodto.getWriter())) {
			namecheck = true;
		}
		model.addAttribute("flag", namecheck);
		model.addAttribute("memodetail", memodto);
		return "memodetail";
		}

//	メモ一件更新
	@RequestMapping("memoupdate")
	public ModelAndView update(@RequestParam String writer, String memo, int idx, ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
		if(logindto == null) {
			System.out.println("エラーです。ログインしてください。");
			mv.addObject("errmsg", "エラーです。ログインしてください。");
			mv.setViewName("login");
			return mv;
		}
		MemoDTO memodto = new MemoDTO();
		memodto.setWriter(writer);
		memodto.setMemo(memo);
		memodto.setIdx(idx);

		memodao.update(memodto);
		mv.setViewName("memojoin");
		return mv;
		}
	
//	メモ一件削除
	@RequestMapping("memodelete")
	public String delete(@RequestParam int idx) {
		memodao.delete(idx);
		return "memojoin";
		}
	
}
