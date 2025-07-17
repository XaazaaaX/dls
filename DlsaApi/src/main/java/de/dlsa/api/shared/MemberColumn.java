package de.dlsa.api.shared;

/**
 * Aufzählung zur Repräsentation der veränderbaren Spalten eines Mitglieds in der Änderungsverfolgung.
 *
 * Wird verwendet, um festzuhalten, welche Eigenschaften eines {@code Member}-Objekts sich im Zeitverlauf geändert haben
 */
public enum MemberColumn {

    /**
     * Gibt an, ob das Mitglied als aktiv oder inaktiv markiert ist.
     */
    ACTIVE,

    /**
     * Die Gruppen, denen das Mitglied zugeordnet ist.
     */
    GROUP,

    /**
     * Das Eintrittsdatum des Mitglieds.
     */
    ENTRYDATE,

    /**
     * Das Austrittsdatum des Mitglieds.
     */
    LEAVINGDATE
}