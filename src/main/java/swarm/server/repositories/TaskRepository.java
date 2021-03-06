package swarm.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import swarm.server.domains.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("Select distinct s.task from Session s where s.developer.id = :id")
	List<Task> findByDeveloperId(@Param("id") Long id);

	@Query("Select distinct s.task from Session s where s.developer.id = :developerId and s.task.done = false and s.task.product.id = :productId")
	List<Task> findActiveTasksByDeveloperIdAndProductId(@Param("developerId") Long developerId, @Param("productId") Long productId);

	//should that be distinct
	Iterable<Task> findByProductId(Long id);

	@Query("Select distinct s.task from Session s where s.developer.id = :id and s.task.done = false")
	List<Task> findActiveTasksByDeveloperId(@Param("id") Long id);

	@Query("Select distinct t from Task t where t.product.id = :id and t.done = false")
	List<Task> findActiveTasksByProductId(@Param("id") Long id);
}
