package gr.aueb.cf.mobile_contacts.dao;


import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.ArrayList;
import java.util.List;

public class MobileContactDAOImpl implements IMobileContactDAO {
    private static final List<MobileContact> contacts = new ArrayList<>();
    private static Long id = 1L;

    @Override
    public MobileContact insert(MobileContact mobileContact) {
        mobileContact.setId(id++);
        contacts.add(mobileContact);
        return mobileContact;
    }

    @Override
    public MobileContact update(Long id, MobileContact mobileContact) {
        contacts.set(getIndexById(id), mobileContact);
        return mobileContact;
    }

    @Override
    public void deleteById(Long id) {
        contacts.removeIf(contact -> contact.getId().equals(id));
    }

    @Override
    public MobileContact getById(Long id) {
        int positionToReturn = getIndexById(id);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public List<MobileContact> getAll() {
        return contacts;
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {

    }

    @Override
    public MobileContact getByPhoneNumber(String phoneNumber) {
        int positionToReturn = getIndexByPhoneNumber(phoneNumber);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public boolean userIdExists(Long id) {
        int position = getIndexById(id);
        return position != -1;
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        return false;
    }

    private int getIndexById(Long id) {
        int positionToReturn = -1;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                //return i;
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
        //return -1;
    }

    private int getIndexByPhoneNumber(String phoneNumber) {
        int positionToReturn = -1;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhoneNumber().equals(phoneNumber)) {
                //return i;
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
        //return -1;
    }
}