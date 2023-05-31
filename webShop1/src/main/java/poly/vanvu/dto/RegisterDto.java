package poly.vanvu.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegisterDto {
	private String username;
	private String fullname;
	private String email;
	private String phone;
	private String address;
	private String password;
	private String confirmPass;
	private MultipartFile image;
	
	
	public RegisterDto() {
		super();
	}

	public RegisterDto(String username, String fullname, String email, String phone, String address, String password,
			String confirmPass, MultipartFile image, String role) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.confirmPass = confirmPass;
		this.image = image;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	
}
