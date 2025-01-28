package gr.aueb.cf.mobile_contacts.validation;

import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;

public class ValidationUtil {

    /**
     * No instances of this class should be available
     */

    private ValidationUtil() {

    }

    public static String validateDTO(MobileContactInsertDTO insertDTO) {
        String errorResponse = "";

        if (insertDTO.getPhoneNumber().length() <= 5)
            errorResponse += "Phone number must have 5 or more digits";
        if (insertDTO.getFirstname().length() < 2)
            errorResponse += "Firstname must have at least more than 2 letters";
        if (insertDTO.getLastname().length() < 2)
            errorResponse += "Lastname must have at least more than 2 letters";
        return errorResponse;
    }

    public static String validateDTO(MobileContactUpdateDTO updateDTO) {
        String errorResponse = "";

        if (updateDTO.getPhoneNumber().length() <= 5)
            errorResponse += "Phone number must have 5 or more digits";
        if (updateDTO.getFirstname().length() < 2)
            errorResponse += "Firstname must have at least more than 2 letters";
        if (updateDTO.getLastname().length() < 2)
            errorResponse += "Lastname must have at least more than 2 letters";
        return errorResponse;
    }
}
