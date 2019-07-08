package swarm.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import swarm.server.domains.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("Select distinct s.task from Session s where s.developer.id = :id")
	List<Task> findByDeveloperId(@Param("id") Long id);

	Iterable<Task> findByProductId(Long id);
	
}
