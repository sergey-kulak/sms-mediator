package fi.netorek.smsmediator.msgrouting.transform.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import fi.netorek.smsmediator.msgrouting.config.PropertyResolverRouting;
import fi.netorek.smsmediator.msgrouting.exception.IncorrectPhoneNumberFormatException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PhoneNumberParserImpl implements PhoneNumberParser {

    private PropertyResolverRouting propertyResolverRouting;

    @Override
    public Phonenumber.PhoneNumber parse(String rawText) {
        try {
            return PhoneNumberUtil.getInstance().parse(rawText, propertyResolverRouting.getProperty("phone.default.region"));
        } catch (NumberParseException ex) {
            throw new IncorrectPhoneNumberFormatException(ex);
        }
    }
}
