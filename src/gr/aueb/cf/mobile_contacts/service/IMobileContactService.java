package gr.aueb.cf.mobile_contacts.service;

import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobile_contacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.List;

public interface IMobileContactService {

    MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException;
    MobileContact updateMobileContact(MobileContactUpdateDTO dto)
            throws PhoneNumberAlreadyExistsException, ContactNotFoundException;

    void deleteContactById(Long id) throws ContactNotFoundException;
    MobileContact getContactById(Long id) throws ContactNotFoundException;
    List<MobileContact>getAllContacts();
}
