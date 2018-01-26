package fi.netorek.smsmediator.msgrouting.transform.phone;


import com.google.i18n.phonenumbers.Phonenumber;

public interface PhoneNumberParser {
    Phonenumber.PhoneNumber parse(String rawText);
}
