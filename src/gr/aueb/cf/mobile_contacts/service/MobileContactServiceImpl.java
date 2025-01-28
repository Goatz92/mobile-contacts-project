 package gr.aueb.cf.mobile_contacts.service;

import gr.aueb.cf.mobile_contacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobile_contacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService {

    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO dto)
            throws PhoneNumberAlreadyExistsException {
        MobileContact mobileContact;
        try {
            if (dao.phoneNumberExists(dto.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number: " +
                        dto.getPhoneNumber() + "already exists!");
            }
            mobileContact = mapInsertDtoToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: %s was inserted", mobileContact);
            return dao.insert(mobileContact);
        } catch (PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: Contact with phone number: %s already exists", dto.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto)
            throws PhoneNumberAlreadyExistsException, ContactNotFoundException {
        MobileContact mobileContact;
        MobileContact newContact;
        try {
            if (!dao.userIdExists(dto.getId())) {
                throw new ContactNotFoundException("Contact with id: " + dto.getId() + "Not Found for update");
            }
            mobileContact = dao.getById(dto.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(dto.getPhoneNumber());
            boolean isPhoneNumberExists = dao.phoneNumberExists(dto.getPhoneNumber());
            if (isPhoneNumberExists && !isPhoneNumberOurOwn) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number: " + dto.getPhoneNumber() + ", Already exists!");
            }
            newContact = mapUpdateDtoToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: %s was updated with new info: %s.", mobileContact, newContact);
            return dao.update(dto.getId(), newContact);
        } catch (ContactNotFoundException | PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        try {
            if (!dao.userIdExists(id)) {
                throw new ContactNotFoundException("Contact with ID: " + id + ", Not found");
            }
            System.err.printf("MobileContactServiceImpl Logger: Contact with ID %s was deleted.", id);
            dao.deleteById(id);
        } catch (ContactNotFoundException e) {
            System.err.println("MobileContactServiceImpl Logger: Contact with ID: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotFoundException {
        MobileContact mobileContact;
        try {
            mobileContact = dao.getById(id);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Contact with ID: " + id + ", does not exist");
            }
            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.println("Contact with ID: " + id + ", was not found to get returned");
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    @Override
    public MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        MobileContact mobileContact;
        try {
            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + ", does not exist");
            }
            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.println("Contact with phone number: " + phoneNumber + ", was not found to get returned");
            throw e;
        }
    }

    @Override
    public void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        try {
            if (!dao.phoneNumberExists(phoneNumber)) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + " not found for delete.");
            }

            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s was deleted.\n", phoneNumber);
            dao.deleteByPhoneNumber(phoneNumber);
        } catch (ContactNotFoundException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }


    private MobileContact mapInsertDtoToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    private MobileContact mapUpdateDtoToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }
}
