package hhd.demo.springvuebackend;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * IndexController
 */
@RestController
public class IndexController {

	@GetMapping(path = "/")
	public ModelAndView index(Map<String, Object> model) {
		String test = "Hello World!";

		model.put("test", test);

		return new ModelAndView("index", model);
	}
}
