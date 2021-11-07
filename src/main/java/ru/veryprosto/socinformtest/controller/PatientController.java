package ru.veryprosto.socinformtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.veryprosto.socinformtest.model.Patient;
import ru.veryprosto.socinformtest.repo.PatientRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class PatientController {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /*@GetMapping("/patient")
    public String getPatients(Model model) {
        Iterable<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patient";
    }*/

    @GetMapping("/patient")
    public String getPatients2(@RequestParam(required = false, defaultValue = "") String filtername,
                               @RequestParam(required = false, defaultValue = "") String filterpassport,
                               Model model) {
        Iterable<Patient> patients;
        if (filtername==null||filtername.isEmpty()){
            if (filterpassport==null||filterpassport.isEmpty()){
                patients=patientRepository.findAll();
            }else{
                patients=patientRepository.findByPassport(filterpassport);
            }
        }else{
            if (filterpassport==null||filterpassport.isEmpty()){
                patients=patientRepository.findByName(filtername);
            }else{
                patients=patientRepository.findByNameAndPassport(filtername, filterpassport);
            }
        }

        model.addAttribute("patients", patients);
        return "patient";
    }

    @GetMapping("/patient/add")
    public String patientAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    @PostMapping("/patient/add")
    public String createPatient(@RequestParam String date, Patient patient) {
        try {
            patient.setBirthday(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        patientRepository.save(patient);
        return "redirect:/patient";
    }

    /*@PostMapping("/patient/add")
    public String createPatient(@RequestParam String name,
                                @RequestParam Map<String, String> form) {
        Patient patient = new Patient();
        patient.setName(name);
        patient.setPassport(form.get("passport"));
        //patient.setBirthday(form.get("birthday"));
        System.out.println(form.get("sex"));

        patientRepository.save(patient);
        return "redirect:/patient";
    }*/
}
