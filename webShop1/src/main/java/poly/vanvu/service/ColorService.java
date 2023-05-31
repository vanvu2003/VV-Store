package poly.vanvu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import poly.vanvu.entity.Color;
import poly.vanvu.repository.ColorRepository;

@Service
public class ColorService {
	@Autowired
	ColorRepository colorRepository;
	
	public Color save(Color color) {
		return colorRepository.save(color);
	}
	
	public void fillColor(Model model) {
		List<Color> listColor = colorRepository.findAll();
		model.addAttribute("listColor",listColor);
	}
}
