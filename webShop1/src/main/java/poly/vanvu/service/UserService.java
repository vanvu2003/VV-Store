package poly.vanvu.service;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetailsService;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.entity.User;

public interface UserService extends UserDetailsService{
	User save(RegisterDto registerDto);
	boolean authenticate(String username,String password,HttpSession session);
}
