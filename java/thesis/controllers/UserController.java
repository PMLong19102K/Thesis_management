package thesis.controllers;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import thesis.configs.models.structures.MyFile;
import thesis.entities.FileEntity;
import thesis.services.AccountEntityService;
import thesis.services.FilesService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AccountEntityService accountService;

	@Autowired
	private FilesService filesService;

	@GetMapping(value = "/dashboard")
	public String dashboard(Model model) {
		return "layouts/user/pages/dashboard";
	}

	@GetMapping(value = "/uploadFile")
	public String uploadPage(Model model) {
		model.addAttribute("myFile", new MyFile());
		return "layouts/user/pages/upload-form";
	}

	@PostMapping(value = "/result")
	public String uploadFile(Model model, MyFile myFile) {
		try {
			MultipartFile multipartFile = myFile.getMultipartFile();
			String fileName = multipartFile.getOriginalFilename();

			File file = new File(this.getFolderUpload(), fileName);
			multipartFile.transferTo(file);

			// save file to DB
			FileEntity fileEntity = new FileEntity();
			fileEntity.setCreateDate(new Date());
			fileEntity.setUrl(fileName);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				fileEntity.setAccount(accountService.findOneByEmail(auth.getName()));
			}

			filesService.save(fileEntity);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Upload failed");
		}
		return "redirect:/download";
	}

	public File getFolderUpload() {
		File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}

	@ModelAttribute("name")
	public String modelDisplayname() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return accountService.findOneByEmail(auth.getName()).getFullName();
		}
		return "Long Pham";
	}
}
