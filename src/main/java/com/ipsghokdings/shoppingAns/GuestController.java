package com.ipsghokdings.shoppingAns;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("guest/*")
public class GuestController {
	
	@Autowired
	GuestDAO guestdao;
	GuestDTO guestdto;
	
	@Autowired
	SqlSession sqlsession;
	
//		ゲストブック一覧取得　and (header.htmlのゲストブックリンク)
		@RequestMapping("guestlist")
		public ModelAndView guestlist(@RequestParam(defaultValue = "") ModelAndView mv,
				HttpServletRequest request) {
			HttpSession session = request.getSession();
			LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
			if(logindto == null) {
				System.out.println("エラーです。ログインしてください。");
				mv.addObject("errmsg", "エラーです。ログインしてください。");
				mv.setViewName("login");
				return mv;
			}
			// list.htmlに送る
			mv.setViewName("guestbooklist");
			mv.addObject("logindto", logindto.getName());
			// Daoから取得した商品情報リストをModelAndviewに[list]という名前で格納
			mv.addObject("guestlist", guestdao.guestlist());

			System.out.println("遷移先：" + mv.getViewName());

			return mv;
			}
		
		
//		ゲストブック一件登録
		@RequestMapping("guestinsert")
		public ModelAndView guestinsert(@Validated GuestDTO guestdto,
				BindingResult bindingResult,
				ModelAndView mv,
				HttpServletRequest request) {
			System.out.println("guestinsert入場");
			
			HttpSession session = request.getSession(false);
			LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
			if(logindto == null) {
				System.out.println("エラーです。ログインしてください。");
				mv.addObject("errmsg", "エラーです。ログインしてください。");
				mv.setViewName("login");
				return mv;
			}
			
			if(bindingResult.hasErrors()) {
				System.out.println("bindingResult");
	            return viewinsert(guestdto, mv, request);
	        }
			
			guestdao.guestinsert(guestdto);
			mv.setViewName("redirect:/guest/guestlist");
			return mv;
			}
		
		
//		ゲストブック詳細表示
		@RequestMapping("guestdetail/{idx}")
		public ModelAndView guestdetail(@PathVariable("idx") int idx, HttpServletRequest request, ModelAndView mv) {	
			
			HttpSession session = request.getSession(false);
			LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
			if(logindto == null) {
				System.out.println("エラーです。ログインしてください。");
				mv.addObject("errmsg", "エラーです。ログインしてください。");
				mv.setViewName("login");
				return mv;
			}
		
			GuestDTO guestdto = guestdao.guestdetail(idx);
			
			boolean namecheck = false;
			if(logindto.getName().equals(guestdto.getName())) {
				namecheck = true;	
			}
			mv.addObject("flag", namecheck);
			mv.addObject("guestdetail", guestdto);
			mv.setViewName("guestbookdetail");
			return mv;
			}
		
		
//		ゲストブック一件更新
		@RequestMapping("guestupdate")
		public String guestupdate(@RequestParam String name, String content, String email, int idx) {
			GuestDTO dto = new GuestDTO();
			dto.setEmail(email);
			dto.setContent(content);
			dto.setName(name);
			dto.setIdx(idx);
			guestdao.guestupdate(dto);
			return "redirect:/guest/guestlist";
			}
		
//		ゲストブック一件削除
		@RequestMapping("guestdelete")
		public String guestdelete(@RequestParam int idx) {
			guestdao.guestdelete(idx);
			return "redirect:/guest/guestlist";
			}
		
		
		
//		ゲストブック作成画面表示(guestbooklist.htmlの記事作成ボタン)
		@RequestMapping("viewinsert")
		public ModelAndView viewinsert(@ModelAttribute GuestDTO guestdto, ModelAndView mv,
				HttpServletRequest request) {
			System.out.println("作成画面表示");
			HttpSession session = request.getSession(false);
			LoginDTO logindto = (LoginDTO)session.getAttribute("LoggedInUser");
			mv.addObject("LoggedInUserName", logindto);
			mv.setViewName("guestbookjoin");
			return mv;
			}
		
	
	
	
	
}
