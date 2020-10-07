package seedu.address.model.country;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class CountryManagerTest {
    //TODO: Add more tests if decide to include checking for 3-letter Country Code
    private static final String[] countryCodes = Locale.getISOCountries();

    @Test
    public void getCountryFromCode_validCode_validCountry() {
        CountryManager countryManager = new CountryManager();

        for (String countryCode : countryCodes) {
            Country country = countryManager.getCountryFromCode(countryCode);
            assertNotNull(country);
        }
    }

    @Test
    public void getCountryFromCode_invalidCode_throwsNullPointerException() {
        CountryManager countryManager = new CountryManager();
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("1"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("12"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("123"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("ab"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("ZZ"));
    }
}
