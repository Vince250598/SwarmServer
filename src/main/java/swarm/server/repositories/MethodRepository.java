package swarm.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import swarm.server.domains.Method;
import swarm.server.domains.Session;
import swarm.server.domains.Type;

@RepositoryRestResource(collectionResourceRel = "methods", path = "methods")
public interface MethodRepository extends JpaRepository<Method, Long> {

	@Query("Select m From Method m Where m.type.id = :typeId")
	List<Method> findByTypeId(@Param("typeId") Long typeId);
	
	List<Method> findByType(@Param("type") Type type);

	@Query("Select count(m) From Method m Where m.type.session = :session")
	int countBySession(@Param("session") Optional<Session> session);
	
	@Query("select m from Method m, Type t, Session s where m.type = t and t.session = s and s = :session " +
		   "and m not in (select i.invoked from Invocation i group by i.invoked) order by m.id")
	List<Method> getStartingMethods(@Param("session") Optional<Session> session);

	@Query("select m from Method m, Type t, Session s where m.type = t and t.session = s and s = :session " +
		   "and m not in (select i.invoking from Invocation i group by i.invoking) order by m.id")
	List<Method> getEndingMethods(@Param("session") Optional<Session> session);
}