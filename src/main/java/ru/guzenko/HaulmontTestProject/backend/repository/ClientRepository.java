package ru.guzenko.HaulmontTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c " +
            "where lower(c.lastName) like lower(concat('%', :searchTerm, '%')) " +
            "and c.bank.id = 1")
    List<Client> search(@Param("searchTerm") String searchTerm/*, @Param("bankId") Long bankId*/);

    List<Client> findAllByBank_Id(Long id);


}
