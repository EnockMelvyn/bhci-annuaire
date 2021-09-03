package com.example.AnnuaireBHCI.service;

import com.example.AnnuaireBHCI.helper.CSVHelper;
import com.example.AnnuaireBHCI.helper.ExcelHelper;
import com.example.AnnuaireBHCI.model.Contact;
import com.example.AnnuaireBHCI.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public List<Contact> createContact(List<Contact> contact) {
        List<Contact> mouvEnregistres = new ArrayList<>();
        for (Contact contact1 : contact) {
            if (contactRepository.findByNumOrdre(contact1.getNumOrdre()).isPresent()) {
                Contact contactExistant = contactRepository.findByNumOrdre(contact1.getNumOrdre()).orElseThrow();
                contact1.setId(contactExistant.getId());
            }
            mouvEnregistres.add(contact1);
        }
        return contactRepository.saveAll(mouvEnregistres);
    }

    public Contact updateContact(Long idContact, Contact contactUpdated){
        Contact contactToUpdate = contactRepository.findById(idContact).orElseThrow();
        contactToUpdate.setNumOrdre(contactUpdated.getNumOrdre());
        contactToUpdate.setDirection(contactUpdated.getDirection());
        contactToUpdate.setFonction(contactUpdated.getFonction());
        contactToUpdate.setNom(contactUpdated.getNom());
        contactToUpdate.setPosteTel(contactUpdated.getPosteTel());
        contactToUpdate.setMatricule(contactUpdated.getMatricule());
        return contactRepository.save(contactToUpdate);
    }

    public void deleteContact(Long idContact){
        Contact contactToDelete = contactRepository.findById(idContact).orElseThrow();
        contactRepository.delete(contactToDelete);
    }

    public void saveCSV(MultipartFile file) {
        try {
            List<Contact> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            contactRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("Echec de l'enregistrement des données csv: " + e.getMessage());
        }
    }

    public void saveExcel(MultipartFile file) {
        try {
            List<String> postesOQP= new ArrayList<>();
            List<Contact> contacts = ExcelHelper.excelToContacts(file.getInputStream());
            for (Contact contact : contacts) {
               if (contactRepository.findByNumOrdre(contact.getNumOrdre()).isPresent()) {
                   Contact contactExistant = contactRepository.findByNumOrdre(contact.getNumOrdre()).orElseThrow();
                   contact.setId(contactExistant.getId());
               }
            }

            contactRepository.saveAll(contacts);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

}
