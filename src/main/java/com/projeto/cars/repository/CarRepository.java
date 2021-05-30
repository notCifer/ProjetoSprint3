package com.projeto.cars.repository;

import java.util.List;
import com.projeto.cars.model.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Cars, String> {

    Cars findBychassi(String chassi);

    List<Cars> findBynome(String NOME);

    List<Cars> findBycor(String COR);

    List<Cars> findBymarca(String MARCA);

    @Query(value = "SELECT * FROM Cars ORDER BY valor ASC", nativeQuery = true)
    List<Cars> OrderByvalor();

    @Query(value = "SELECT * FROM Cars ORDER BY ano_fabricacao ASC", nativeQuery = true)
    List<Cars> OrderByano();

    @Query(value = "SELECT * FROM Cars ORDER BY nome ASC", nativeQuery = true)
    List<Cars> OrderBynome();

    @Query(value = "SELECT * FROM Cars WHERE valor = (SELECT MAX(valor) FROM Cars)",nativeQuery = true)
    Cars findByMaisCaro();

    @Query(value = "SELECT * FROM Cars WHERE valor = (SELECT MIN(valor) FROM Cars)",nativeQuery = true)
    Cars findByMaisBarato();

}