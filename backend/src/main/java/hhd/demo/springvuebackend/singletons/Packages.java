package hhd.demo.springvuebackend.singletons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import hhd.demo.springvuebackend.models.Stats;

/**
 * Packages
 */
@ApplicationScope
@Component
public class Packages {

	@Autowired
	Environment env;

	@Value("classpath:stats.json")
	Resource statsFile;

	public Map<String, List<Map<String, String>>> getPackage(String template) {
		List<Map<String, String>> scripts = new ArrayList<Map<String, String>>();
		List<Map<String, String>> sourceMaps = new ArrayList<Map<String, String>>();
		List<Map<String, String>> styles = new ArrayList<Map<String, String>>();
		if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				Stats stats = objectMapper.readValue(statsFile.getInputStream(), Stats.class);

				ArrayList<String> assets = (ArrayList<String>) stats.getNamedChunkGroups().get(template).get("assets");

				assets.forEach((asset) -> {
					if (asset.endsWith(".js")) {
						Map<String, String> script = new HashMap<>();
						script.put("script", asset);
						scripts.add(script);
					} else if(asset.endsWith(".map")) {
						Map<String, String> map = new HashMap<>();
						map.put("map", asset);
						sourceMaps.add(map);
					} else if(asset.endsWith(".css")) {
						Map<String, String> style = new HashMap<>();
						style.put("style", asset);
						styles.add(style);
					}
				});
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// Map<String, String> common = new HashMap<>();
			// Map<String, String> vendors = new HashMap<>();
			Map<String, String> main = new HashMap<>();

			// common.put("script", "js/chunk-common.js");
			// vendors.put("script", "js/chunk-vendors.js");
			main.put("script", "js/" + template + ".js");

			// scripts.add(common);
			// scripts.add(vendors);
			scripts.add(main);
		}
		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
		result.put("scripts", scripts);
		result.put("sourceMaps", sourceMaps);
		result.put("styles", styles);
		return result;
	}
}
