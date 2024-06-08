package com.ipsghokdings.shoppingAns;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("login/*")
public class LoginController {

	@Autowired
	LoginDAO logindao;

	// ログイン画面遷移(localhost:8082/login/初期画面)
	@RequestMapping("/")
	public ModelAndView login(HttpServletRequest request,
			ModelAndView mv) {
		/*
		 * HttpSession session = request.getSession(false); LoginDTO logindto =
		 * (LoginDTO)session.getAttribute("LoggedInUser");
		 */
		mv.setViewName("login");
		return mv;
	}

	// ログイン画面遷移 and (header.htmlのログインリンク)
	@RequestMapping("login")
	public String login() {
		return "login";
	}

	// ログイン処理(login.htmlのログインボタン)
	@PostMapping("logindo")
	public String login(@RequestParam("idx") String idx, @RequestParam("passwd") String passwd, String name,
			HttpServletRequest request) {
		System.out.println("logindo入場");
		LoginDTO dto = new LoginDTO();
		dto.setIdx(idx);
		dto.setPasswd(passwd);
		dto.setName(name);
		LoginDTO storedPassword = logindao.getPasswdByIdx(dto);

		HttpSession session = request.getSession();
		if (storedPassword == null) {
			System.out.println("エラーではある。");
			return "login";
		}
		session.setAttribute("LoggedInUser", storedPassword);
		System.out.println("LoggedInUser: " + storedPassword);
		return "redirect:/memo/home";
	}

	// ログアウト処理
	@RequestMapping("logoutdo")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginDTO logindto = (LoginDTO) session.getAttribute("LoggedInUser");
		if (logindto != null) {
			session.invalidate();
		}
		return "login";
	}

	// 会員登録処理(registerrogin.htmlの登録ボタン)
	@RequestMapping("memberinsert")
	public ModelAndView memberinsert(@RequestParam String idx, String passwd, String name,
			HttpServletRequest request, HttpServletResponse response, ModelAndView mv)
			throws ServletException, IOException {
		System.out.println("会員登録処理入場");

		//		入力値のチェック
		boolean existsError = dataValidation(idx, passwd, name, request);
		//			エラーが有る場合
		if (existsError == true) {
			mv.addObject("idx", idx);
			mv.addObject("passwd", passwd);
			mv.addObject("name", name);
			mv.addObject("existsError", existsError);
			mv.setViewName("registerrogin");
			return mv;
		}

		LoginDTO logindto = new LoginDTO();
		logindto.setIdx(idx);
		logindto.setPasswd(passwd);
		logindto.setName(name);

		logindao.insert(logindto);

		System.out.println("loginまできてまあす？");
		mv.setViewName("redirect:/login/login");
		return mv;
	}

	private void registInputKeeper(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("idx", request.getParameter("idx"));
		request.setAttribute("passwd", request.getParameter("passwd").trim());
		request.setAttribute("name", request.getParameter("name").trim());
	}

	public boolean dataValidation(
			String passwd,
			String idx,
			String name,
			HttpServletRequest request) throws ServletException, IOException {
		boolean existsError = false;

		//		エラーチェック
		String errMsgPasswd = errMsgPasswd(passwd);
		if (!errMsgPasswd.isEmpty()) {
			request.setAttribute("errMsgPasswd", errMsgPasswd);
			existsError = true;
		}

		String errMsgIdx = errMsgIdx(idx);
		if (!errMsgIdx.isEmpty()) {
			request.setAttribute("errMsgIdx", errMsgIdx);
			existsError = true;
		}

		String errMsgName = errMsgName(name);
		if (!errMsgName.isEmpty()) {
			request.setAttribute("errMsgName", errMsgName);
			existsError = true;
		}

		return existsError;

	}

	private String errMsgPasswd(String passwd) throws ServletException, IOException {
		String errMsg = "";

		if (passwd == null || passwd.trim().length() == 0) {
			return errMsg = "パスワードを入力してください。";
		}

		if (passwd.length() > 50) {
			return errMsg = "パスワードは50文字以内で入力してください。";
		}

		return errMsg;
	}

	private String errMsgIdx(String idx) throws ServletException, IOException {
		String errMsg = "";

		if (idx == null || idx.trim().length() == 0) {
			return errMsg = "IDを入力してください。";
		}

		if (idx.length() > 50) {
			return errMsg = "IDは50文字以内で入力してください。";
		}

		return errMsg;
	}

	private String errMsgName(String name) throws ServletException, IOException {
		String errMsg = "";

		if (name == null || name.trim().length() == 0) {
			return errMsg = "名前を入力してください。";
		}

		if (name.length() > 50) {
			return errMsg = "名前は50文字以内で入力してください。";
		}

		return errMsg;
	}

}
