package thesis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.entities.FileEntity;
import thesis.repositories.FilesRepository;

@Service
public class FilesService {

	@Autowired
	private FilesRepository filesRepository;

	public void save(FileEntity fileEntity) {
		filesRepository.save(fileEntity);
	}
	
	public List<FileEntity> findAll() {
		return (List<FileEntity>) filesRepository.findAll();
	}
	
	public FileEntity findFileById(int id) {
		Optional<FileEntity> fileEntity = filesRepository.findById(id);
		if(fileEntity.isPresent()) {
			return fileEntity.get();
		}
		return new FileEntity();
	}
}
