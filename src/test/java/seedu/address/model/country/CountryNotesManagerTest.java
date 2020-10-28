package seedu.address.model.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.note.CountryNote;

public class CountryNotesManagerTest {

    //TODO: Add more tests if decide to include checking for 3-letter Country Code
    private static final String[] COUNTRY_CODES = Locale.getISOCountries();
    private CountryNotesManager countryNotesManager;

    @BeforeEach
    public void setUp() {
        countryNotesManager = new CountryNotesManager();
    }

    @Test
    public void hasCountryNote_duplicateNote_returnTrue() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            CountryNote genericNote = new CountryNote("generic note", country);
            countryNotesManager.addCountryNote(genericNote);
            assertTrue(countryNotesManager.hasCountryNote(genericNote));
        }
    }

    @Test
    public void hasCountryNote_notDuplicateNote_returnFalse() {
        for (String countryCode : COUNTRY_CODES) {
            CountryNote genericNote = new CountryNote("generic note", new Country(countryCode));
            assertFalse(countryNotesManager.hasCountryNote(genericNote));
        }
    }

    @Test
    public void addCountryNote_validNote_updatesCorrectly() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            CountryNote genericNote = new CountryNote("generic note", country);
            assertFalse(countryNotesManager.hasCountryNote(genericNote));
            countryNotesManager.addCountryNote(genericNote);
            assertTrue(countryNotesManager.hasCountryNote(genericNote));
        }
    }

    @Test
    public void addCountryNote_countryNoteWithNullCountry_throwsAssertionError() {
        CountryNote countryNote = new CountryNote("country note 1", Country.NULL_COUNTRY);
        assertThrows(AssertionError.class, () -> countryNotesManager.addCountryNote(countryNote));
    }

    @Test
    public void addCountryNote_duplicateNote_notAdded() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            CountryNote genericNote = new CountryNote("generic note", country);
            countryNotesManager.addCountryNote(genericNote);
            assertEquals(1, countryNotesManager.asUnmodifiableObservableList()
                    .stream()
                    .filter(x -> x.equals(genericNote))
                    .count());
            countryNotesManager.addCountryNote(genericNote);
            assertEquals(1, countryNotesManager.asUnmodifiableObservableList()
                    .stream()
                    .filter(x -> x.equals(genericNote))
                    .count());
        }
    }

    @Test
    public void deleteCountryNote_nullCountryNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> countryNotesManager.deleteCountryNote(null));
    }

    @Test
    public void deleteCountryNote_countryNoteNotExist_assertionError() {
        CountryNote countryNote = new CountryNote("random", new Country("SG"));
        assertThrows(AssertionError.class, () -> countryNotesManager.deleteCountryNote(countryNote));
    }

    @Test
    public void deleteCountryNote_countryNoteExists_deletesCountryNote() {
        CountryNote countryNote = new CountryNote("random", new Country("SG"));
        countryNotesManager.addCountryNote(countryNote);
        assertTrue(countryNotesManager.hasCountryNote(countryNote));
        countryNotesManager.deleteCountryNote(countryNote);
        assertFalse(countryNotesManager.hasCountryNote(countryNote));
    }

}
