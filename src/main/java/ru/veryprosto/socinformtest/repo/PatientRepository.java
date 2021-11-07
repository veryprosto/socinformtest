package ru.veryprosto.socinformtest.repo;

import org.springframework.data.repository.CrudRepository;
import ru.veryprosto.socinformtest.model.Patient;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByName(String name);
    List<Patient> findByPassport(String passport);
    List<Patient> findByNameAndPassport(String name, String passport);

}