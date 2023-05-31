package poly.vanvu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.bytebuddy.utility.RandomString;
import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;

@Controller
public class ForgetPasswordController {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	UserRespository userRespository;
	
	@GetMapping("/forgetPassword")
	public String forgetpass() {
		return "forgetPassword";
	}
	@GetMapping("/reset_password")
	public String showFormReset() {
		return "resetPassword";
	}
	@PostMapping("/forgetPassword")
	public String forget(HttpServletRequest request,Model model,HttpSession session,@RequestParam("email")String email) {
		String token = RandomString.make(45);
		
		List<User> listUser = userRespository.findByEmail(email);
		
		if (listUser.size()>0) {
			User user = listUser.get(0);
			updateToken(user, token);
			String resetLink = "http://localhost:8080/reset_password?username=" + token;
			try {
				sendMail(email, resetLink);
				model.addAttribute("success","Vui lòng check mail của bạn");
			} catch (UnsupportedEncodingException | MessagingException e) {
				model.addAttribute("error","Lỗi trong quá trình gửi mail");
			}
		}else {
			model.addAttribute("error","Không tìm thấy tài khoản nào!!");
		}
		return "forgetPassword";
	}
	
	@PostMapping("/resetPassword")
	public String resetPass() {
		
		
		return "login";
	}
	
	private void sendMail(String email, String resetLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("vanvu19102003@gmail.com", "V&V SHOP");
		helper.setTo(email);
		
		String subject = "XÁC NHẬN THAY ĐỔI MẬT KHẨU";
		String content = "<p>Chào bạn!</p>"
				+ "<p>Bạn muốn thay đổi mật khẩu phải không?</p>"
				+ "<p>Hãy kích vào link phía dưới để thay đổi mật khẩu mới nhé</p>"
				+"<p><a href=\"" + resetLink + "\">Thay đổi mật khẩu</a></p>";
		helper.setSubject(subject);
		helper.setText(content,true);
		
		mailSender.send(message);
	}
	
	private void updateToken(User user, String token) {
		user.setToken(token);
		
		userRespository.save(user);
	}
}
