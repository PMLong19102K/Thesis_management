package thesis.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import thesis.entities.FileEntity;

@Repository
public interface FilesRepository extends CrudRepository<FileEntity, Integer> {

}
