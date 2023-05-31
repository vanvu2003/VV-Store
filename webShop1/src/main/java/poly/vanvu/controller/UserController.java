package poly.vanvu.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.entity.User;
import poly.vanvu.service.UserService;
import poly.vanvu.service.UserServiceImp;

@Controller
public class UserController {

	@Autowired
	ServletContext app;
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	public String viewlogin() {
		return "login";
	}
//	@PostMapping("/login")
//	public String login(@RequestParam("username")String username,@RequestParam("password")String password,RedirectAttributes redirectAttributes,
//			HttpSession session) {
//		boolean checkLogin = userService.authenticate(username, password,session);
//		if (!checkLogin) {
//			redirectAttributes.addFlashAttribute("error", "Tên người dùng hoặc mật khẩu không đúng");
//	        return "redirect:/login";
//		}
//
//		return "redirect:/home";
//	}
	
	
	@ModelAttribute("user")
	public RegisterDto registerDto() {
		return new RegisterDto();
	}
	
	@RequestMapping("/register")
	public String viewRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("image")MultipartFile image,@ModelAttribute("user") RegisterDto registerDto) throws IllegalStateException, IOException {
		ResponseEntity<String> response = null;
		if(!image.isEmpty()) {	
			String filename= image.getOriginalFilename();
			File folder = new File(app.getRealPath("/upload"));
			if (!folder.exists()) {
				folder.mkdir();
			}
			File file = new File(app.getRealPath("/upload/"+ filename));
			image.transferTo(file);
		}
		
		User user = userService.save(registerDto);
//		if (user != null) {
//			response = ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
//		} else {
//
//		}
		return "register";
	}
}
