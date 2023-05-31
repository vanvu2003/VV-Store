package poly.vanvu.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;

@Service
public class UserServiceImp implements UserService{
	@Autowired
	UserRespository userRespository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	HttpSession session;
	
	
	@Override
	public User save(RegisterDto registerDto) {
		User user = new User();
		
		user.setUsername(registerDto.getUsername());
		user.setFullname(registerDto.getFullname());
		user.setPhone(registerDto.getPhone());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setEmail(registerDto.getEmail());
		user.setAddress(registerDto.getAddress());
		user.setCreateDate(new Date());
		user.setImage(registerDto.getImage().getOriginalFilename());
		user.setRole("user");
		user.setStatus(true);
		
		return userRespository.save(user);
	}
	
	@Override
	public boolean authenticate(String username, String password,HttpSession session) {
		User user = userRespository.findById(username).orElse(null);
		
		if (user != null) {
			session.setAttribute("user", user);
			return passwordEncoder.matches(password, user.getPassword());
		}
		return false;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> lisUsers = userRespository.findByUsername(username);
		
		if(lisUsers.size()>0) {
			User user = lisUsers.get(0);
			session.setAttribute("user", user);
			Set<GrantedAuthority> auth = new HashSet<>();
			auth.add(new SimpleGrantedAuthority(user.getRole()));
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auth);
		}else {
			throw new UsernameNotFoundException("Không tìm thấy username này!!");
			
		}
	}

}
