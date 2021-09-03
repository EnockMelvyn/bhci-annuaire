package com.example.AnnuaireBHCI.controller;

import com.example.AnnuaireBHCI.helper.CSVHelper;
import com.example.AnnuaireBHCI.helper.ExcelHelper;
import com.example.AnnuaireBHCI.message.ResponseMessage;
import com.example.AnnuaireBHCI.model.Contact;
import com.example.AnnuaireBHCI.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping("/uploadCSV")
    public ResponseEntity<ResponseMessage> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                contactService.saveCSV(file);

                message = "Fichier uploadé avec succès: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Le fichier: " + file.getOriginalFilename() + " n'a pas pu être uploadé!";
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Veuillez uploader un fichier CSV SVP!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping("/uploadExcel")
    public ResponseEntity<ResponseMessage> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                contactService.saveExcel(file);

                message = "Fichier uploadé avec succès: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Le fichier: " + file.getOriginalFilename() + " n'a pas pu être uploadé!";
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Veuillez uploader un fichier CSV SVP!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            List<Contact> contacts = contactService.getAllContacts();

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<List<Contact>> createContact(@RequestBody List<Contact> contact) {
        return new ResponseEntity<>(contactService.createContact(contact), HttpStatus.CREATED);
    }

    @PutMapping("/{idContact}")
    public ResponseEntity<Contact> updateContact(@PathVariable("idContact") Long idContact,@RequestBody Contact contact) {
        return new ResponseEntity<>(contactService.updateContact(idContact,contact), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{idContact}")
    public ResponseEntity<ResponseMessage> updateContact(@PathVariable("idContact") Long idContact) {
        contactService.deleteContact(idContact);
        String message = "Contact supprimé!";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
