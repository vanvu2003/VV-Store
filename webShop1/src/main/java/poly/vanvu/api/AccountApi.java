package poly.vanvu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;

@RestController
@RequestMapping("/account/api")
public class AccountApi {
	@Autowired
	UserRespository userRespository;
	
	
	@GetMapping("/login")
	public ResponseEntity<List<User>> login() {
		List<User> user = userRespository.findAll();
		
		return ResponseEntity.ok(user);
	}
}
