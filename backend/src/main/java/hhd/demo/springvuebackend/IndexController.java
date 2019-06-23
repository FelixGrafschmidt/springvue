package hhd.demo.springvuebackend;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hhd.demo.springvuebackend.singletons.Packages;

/**
 * IndexController
 */
@RestController
public class IndexController {

	@Autowired
	private Packages packages;

	@GetMapping(path = "/")
	public ModelAndView index(Map<String, Object> model) {
		model.put("testString", "Generated with Java, yay!");
		model.put("scripts", packages.getPackage("index").get("scripts"));
		model.put("sourceMaps", packages.getPackage("index").get("sourceMaps"));
		model.put("styles", packages.getPackage("index").get("styles"));
		return new ModelAndView("index", model);
	}

	// @GetMapping(path = "/error")
	// public ModelAndView error(Map<String, Object> model) {
	// 	System.out.println("error");

	// 	return new ModelAndView("error", model);
	// }
}
