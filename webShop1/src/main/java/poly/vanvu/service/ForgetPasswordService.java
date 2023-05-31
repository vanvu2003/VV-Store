package poly.vanvu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;

@Service
public class ForgetPasswordService {
	@Autowired
	UserRespository userRespository;
	
	
}
