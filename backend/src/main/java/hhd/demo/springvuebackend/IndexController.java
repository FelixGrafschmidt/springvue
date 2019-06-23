package hhd.demo.springvuebackend;

import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hhd.demo.springvuebackend.models.Stats;

/**
 * IndexController
 */
@RestController
public class IndexController {

	@Autowired
	Environment env;
	
	@Value("classpath:stats.json")
	Resource statsFile;

	@GetMapping(path = "/")
	public ModelAndView index(Map<String, Object> model) {
		model.put("testString", "Generated with Java!");
		if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
			model.put("common", "js/chunk-common.js");
			model.put("vendors", "js/chunk-vendors.js");
			model.put("index", "js/index.js");
		} else {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				Stats stats = objectMapper.readValue(statsFile.getInputStream(), Stats.class);

				if (stats.getAssetsByChunkName().containsKey("index")) {
					model.put("index", stats.getAssetsByChunkName().get("index").get(0));
				} else {
					model.put("index", "");
					// log error
				}
				if (stats.getAssetsByChunkName().containsKey("chunk-vendors")) {
					model.put("vendors", stats.getAssetsByChunkName().get("chunk-vendors").get(0));
				} else {
					model.put("vendors", "");
					// log error
				}
				if (stats.getAssetsByChunkName().containsKey("chunk-common")) {
					model.put("common", stats.getAssetsByChunkName().get("chunk-common").get(0));
				} else {
					model.put("common", "");
					// log error
				}
			} catch (Exception e) {
				System.out.println(e);
				model.put("index", "");
				model.put("vendors", "");
				model.put("common", "");
			}
		}
		return new ModelAndView("index", model);
	}

	// @GetMapping(path = "/")
	// @Profile("prod")
	// public ModelAndView index(Map<String, Object> model) {
	// System.out.println("prod");

	// try {
	// File statsFile = ResourceUtils.getFile("classpath:stats.json");

	// ObjectMapper objectMapper = new ObjectMapper();
	// Stats stats = objectMapper.readValue(statsFile, Stats.class);

	// if (stats.getAssetsByChunkName().containsKey("index")) {
	// model.put("index", stats.getAssetsByChunkName().get("index").get(0));
	// } else {
	// model.put("index", "");
	// // log error
	// }
	// if (stats.getAssetsByChunkName().containsKey("chunk-vendors")) {
	// model.put("vendors",
	// stats.getAssetsByChunkName().get("chunk-vendors").get(0));
	// } else {
	// model.put("vendors", "");
	// // log error
	// }
	// if (stats.getAssetsByChunkName().containsKey("chunk-common")) {
	// model.put("common", stats.getAssetsByChunkName().get("chunk-common").get(0));
	// } else {
	// model.put("common", "");
	// // log error
	// }
	// } catch (Exception e) {
	// System.out.println(e);
	// model.put("index", "");
	// model.put("vendors", "");
	// model.put("common", "");
	// }

	// return new ModelAndView("index", model);
	// }

	// @GetMapping(path = "/index")
	// @Profile("dev")
	// public ModelAndView indexDev(Map<String, Object> model) {
	// System.out.println("dev");

	// model.put("common", "js/chunk-common.js");
	// model.put("vendors", "js/chunk-vendors.js");
	// model.put("index", "js/index.js");

	// return new ModelAndView("index", model);
	// }

	@GetMapping(path = "/error")
	public ModelAndView error(Map<String, Object> model) {
		System.out.println("error");

		return new ModelAndView("error", model);
	}
}
