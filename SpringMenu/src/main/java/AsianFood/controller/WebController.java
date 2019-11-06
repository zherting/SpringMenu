package AsianFood.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import AsianFood.beans.Menu;
import AsianFood.repository.MenuRepository;

@Controller
public class WebController {
	@Autowired
	MenuRepository repo;

	// view all menu
	@GetMapping("/viewAll")
	public String viewAllMenu(Model model) {
		model.addAttribute("menu", repo.findAll());
		return "results";
	}

	// add item in menu
	@GetMapping("/inputMenu")
	public String addNewMenu(Model model) {
		Menu c = new Menu();
		model.addAttribute("newMenu", c);
		return "input";
	}

	@PostMapping("/inputMenu")
	public String addNewMenu(@ModelAttribute Menu c, Model model) {
		repo.save(c);
		model.addAttribute("menu", repo.findAll());
		return "results";
	}

	// delete item in menu
	@GetMapping("/delete/{id}")
	public String deleteMenu(@PathVariable("id") long id, Model model) {
		Menu c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		repo.delete(c);
		model.addAttribute("menu", repo.findAll());
		return "results";
	}

	// edit item in menu
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Menu c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("menu", c);
		return "update";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid Menu c, BindingResult result, Model model) {
		if (result.hasErrors()) {
			c.setId(id);
			return "update";
		}

		repo.save(c);
		model.addAttribute("menu", repo.findAll());
		return "results";
	}
}
