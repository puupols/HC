package rest;

import org.springframework.data.repository.CrudRepository;

public interface temperatureRepository extends CrudRepository<Temperature, Long>{
	
}