package de.dlsa.api.responses;

/**
 * Response-Klasse zur Darstellung einer Aktion (Action).
 * Wird verwendet, um Aktionsdaten (z. B. für die Anzeige im Frontend) strukturiert bereitzustellen.
 * Enthält grundlegende Informationen sowie die zugehörige Kontaktperson als {@link MemberResponse}.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class ActionResponse {

    /**
     * Eindeutige ID der Aktion.
     */
    private Long id;

    /**
     * Jahr, in dem die Aktion stattfindet.
     */
    private String year;

    /**
     * Beschreibung der Aktion.
     */
    private String description;

    /**
     * Verknüpfte Kontaktperson für die Aktion.
     */
    private MemberResponse contact;

    // --- Getter & Setter (mit Fluent API) ---

    public Long getId() {
        return id;
    }

    public ActionResponse setId(Long id){
        this.id = id;
        return this;
    }

    public String getYear() {
        return year;
    }

    public ActionResponse setYear(String year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActionResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public MemberResponse getContact() {
        return contact;
    }

    public ActionResponse setContact(MemberResponse contact) {
        this.contact = contact;
        return this;
    }
}
