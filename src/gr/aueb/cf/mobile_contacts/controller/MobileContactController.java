package gr.aueb.cf.mobile_contacts.controller;

import gr.aueb.cf.mobile_contacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobile_contacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.model.MobileContact;
import gr.aueb.cf.mobile_contacts.service.IMobileContactService;
import gr.aueb.cf.mobile_contacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobile_contacts.validation.ValidationUtil;

public class MobileContactController {

    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO insertDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            //Validate input data (DTO)
            String errorVector = ValidationUtil.validateDTO(insertDTO);
            if (!errorVector.isEmpty()) {
                return "Error." + "Validation error\n" + errorVector;
            }
            //If Validation is OK -> insert contact
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = mapMobileContactToDTO(mobileContact);
            return "Ok\n" + serializeDTO(readOnlyDTO);
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error\n" + e.getMessage() + "\n";
        }
    }

    private MobileContactReadOnlyDTO mapMobileContactToDTO (MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO
                (mobileContact.getId(),
                mobileContact.getFirstname(),
                mobileContact.getLastname(),
                mobileContact.getPhoneNumber());
    }

    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
        return "ID: " + readOnlyDTO +
                ", Firstname: " + readOnlyDTO.getFirstname() +
                ", Lastname: " + readOnlyDTO.getLastname() +
                ", Phone number: " + readOnlyDTO.getPhoneNumber();
    }
}
