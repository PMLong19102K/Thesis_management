package thesis.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.entities.FileEntity;
import thesis.services.AccountEntityService;
import thesis.services.FilesService;

@Controller
public class HomeController {

	@Autowired
	private FilesService filesService;

	@Autowired
	private AccountEntityService accountService;

	@GetMapping(value = { "/", "/home" })
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			for (GrantedAuthority item : auth.getAuthorities()) {
				if (item.getAuthority().contentEquals("ROLE_ADMIN")) {
					return "redirect:/admin/dashboard";
				}
			}

			return "redirect:/user/dashboard";
		} else {
			return "redirect:/sign-in";
		}
	}

	@GetMapping(value = "/download")
	public String downloadPage(Model model) {
		model.addAttribute("files", filesService.findAll());
		return "layouts/download";
	}

	@ModelAttribute("name")
	public String modelDisplayname() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return accountService.findOneByEmail(auth.getName()).getFullName();
		}
		return "Long Pham";
	}

	@GetMapping(value = "/download-file")
	public void downloadFile(@RequestParam(value = "fileId") int fileId, HttpServletResponse response) {
		FileEntity fileEntity = filesService.findFileById(fileId);
		try {
			File file = ResourceUtils.getFile(System.getProperty("user.home") + "/Uploads/" + fileEntity.getUrl());
			byte[] data = FileUtils.readFileToByteArray(file);
			// Thiết lập thông tin trả về
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
