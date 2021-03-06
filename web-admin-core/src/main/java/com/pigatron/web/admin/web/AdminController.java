package com.pigatron.web.admin.web;

import com.pigatron.web.admin.config.SubModules;
import com.pigatron.web.admin.config.WebResources;
import com.pigatron.web.admin.menu.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

	private static final String VIEW_ADMIN = "admin/pages/admin";

	@Value("${url.admin}")
	private String adminUrl;

	@Value("${pigatron.revision}")
	private String revision;

	@Autowired
	private MenuItem adminMenu;

	@Autowired
	private SubModules adminSubmodules;

	@Autowired
	private WebResources webResources;

	@ModelAttribute("adminUrl")
	public String getAdminUrl() {
		return adminUrl;
	}

	@ModelAttribute("adminMenu")
	public MenuItem getAdminMenu() {
		return adminMenu;
	}

	@ModelAttribute("submodules")
	public String getSubmodules() {
		//TODO pass back list and create js array in template
		String s = adminSubmodules.getSubmodules().toString();
		s = s.substring(1, s.length()-1);
		s = s.replaceAll("\\s","");
		return s;
	}

	@ModelAttribute("resources")
	public WebResources getResources() {
		return webResources;
	}

	@ModelAttribute("metadata")
	public Map<String, String> getMetadata(HttpServletRequest request) {
		Map<String, String> metadata = new HashMap<>();
		return metadata;
	}

	@ModelAttribute("revision")
	public String getRevision() {
		return revision;
	}

	@RequestMapping(value = "/${url.admin}/**", method = RequestMethod.GET)
	public String admin() {
		return VIEW_ADMIN;
	}

}
