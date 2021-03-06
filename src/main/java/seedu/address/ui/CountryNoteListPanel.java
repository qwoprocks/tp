package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;

/**
 * Panel containing the list of country notes.
 */
public class CountryNoteListPanel extends UiPart<Region> {

    private static final String FXML = "CountryNoteListPanel.fxml";
    private static final String HEADER_ALL_COUNTRIES_TEXT = "All Country Notes";

    private final ObservableList<CountryNote> countryNoteObservableList;
    private boolean displayAllCountries = true;
    @FXML
    private Label header;
    @FXML
    private VBox countryNoteListView;

    /**
     * Initializes a {@code CountryNoteListPanel} with a countryNoteObservableList.
     */
    public CountryNoteListPanel(ObservableList<CountryNote> countryNoteObservableList) {
        super(FXML);
        this.countryNoteObservableList = countryNoteObservableList;
        header.setText("Country Notes");

        updateCountryNoteListView(countryNoteObservableList);
        countryNoteObservableList.addListener((ListChangeListener<CountryNote>) c -> {
            if (c.next()) {
                updateCountryNoteListView(countryNoteObservableList);
            }
        });
    }

    private void updateCountryNoteListView(ObservableList<CountryNote> countryNoteObservableList) {
        countryNoteListView.getChildren().clear();
        // This index is used to keep track of how many country notes have been displayed so far.
        int noteIndex = 0;
        if (!displayAllCountries) {
            initCountryNoteListViewFromCountryNotes(countryNoteListView, countryNoteObservableList, noteIndex);
            return;
        }

        Country currCountry = null;
        CountryNoteListSubPanel countryNoteListSubPanel = new CountryNoteListSubPanel();
        for (CountryNote countryNote : countryNoteObservableList) {
            if (currCountry == null) {
                currCountry = countryNote.getCountry();
            }
            if (!currCountry.equals(countryNote.getCountry())) {
                countryNoteListSubPanel.header.setText(currCountry + " notes");
                countryNoteListView.getChildren().add(countryNoteListSubPanel.getRoot());
                currCountry = countryNote.getCountry();
                countryNoteListSubPanel = new CountryNoteListSubPanel();
            }
            countryNoteListSubPanel.addNoteListCard(new NoteListCard(countryNote, noteIndex + 1));
            noteIndex++;
        }
        if (countryNoteObservableList.size() != 0) {
            countryNoteListSubPanel.header.setText(currCountry + " notes");
            countryNoteListView.getChildren().add(countryNoteListSubPanel.getRoot());
        }
    }

    /**
     * Given a list of country notes and the VBox to display them in, adds the country notes to the viewbox.
     */
    private static void initCountryNoteListViewFromCountryNotes(VBox countryNoteListView,
            ObservableList<CountryNote> countryNoteObservableList, int startIndex) {
        for (int i = 0; i < countryNoteObservableList.size(); i++) {
            CountryNote countryNote = countryNoteObservableList.get(i);
            Node node = new NoteListCard(countryNote, i + startIndex + 1).getRoot();
            countryNoteListView.getChildren().add(node);
        }
    }

    /**
     * Sets the header of the CountryNoteListPanel.
     *
     * @param country The country to be displayed.
     */
    public void setHeader(Country country) {
        if (country.equals(Country.NULL_COUNTRY)) {
            countryNoteListView.setSpacing(10.0);
            header.setText(HEADER_ALL_COUNTRIES_TEXT);
            displayAllCountries = true;
        } else {
            countryNoteListView.setSpacing(20.0);
            header.setText(country + " notes");
            displayAllCountries = false;
        }
        updateCountryNoteListView(countryNoteObservableList);
    }

    /**
     * Sub-panel which is used for viewing all countries' notes, only used when {@code displayAllCountries} is true.
     */
    private static class CountryNoteListSubPanel extends UiPart<Region> {
        private static final String FXML = "CountryNoteListPanel.fxml";

        @FXML
        private VBox countryNoteListContainer;
        @FXML
        private Label header;
        @FXML
        private ScrollPane countryNoteScrollPane;
        @FXML
        private VBox countryNoteListView;

        /**
         * Initializes a {@code CountryNoteListSubPanel}.
         */
        public CountryNoteListSubPanel() {
            super(FXML);
            countryNoteListContainer.setStyle("-fx-border-color: #FF3333; -fx-border-radius: 20; -fx-padding: 10;");
            countryNoteScrollPane.setMinHeight(30.0);
            countryNoteScrollPane.setPrefHeight(200.0);
        }

        public void addNoteListCard(NoteListCard noteListCard) {
            countryNoteListView.getChildren().add(noteListCard.getRoot());
        }
    }

}
