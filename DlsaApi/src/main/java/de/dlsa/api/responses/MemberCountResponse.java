package de.dlsa.api.responses;

/**
 * Antwortstruktur zur Darstellung verschiedener Mitgliederanzahlen.
 * Enthält Gesamtzahl, aktive, passive und DLS-pflichtige Mitglieder.
 *
 * @version 05/2025
 */
public class MemberCountResponse {

    /** Gesamtanzahl aller Mitglieder. */
    private long allMembers;

    /** Anzahl aktiver Mitglieder. */
    private long activeMembers;

    /** Anzahl passiver Mitglieder. */
    private long passiveMembers;

    /** Anzahl der Mitglieder mit DLS-Verpflichtung. */
    private long dlsRequiredMembers;

    // --- Getter & Fluent Setter ---

    public long getAllMembers() {
        return allMembers;
    }

    public MemberCountResponse setAllMembers(long allMembers) {
        this.allMembers = allMembers;
        return this;
    }

    public long getActiveMembers() {
        return activeMembers;
    }

    public MemberCountResponse setActiveMembers(long activeMembers) {
        this.activeMembers = activeMembers;
        return this;
    }

    public long getPassiveMembers() {
        return passiveMembers;
    }

    public MemberCountResponse setPassiveMembers(long passiveMembers) {
        this.passiveMembers = passiveMembers;
        return this;
    }

    public long getDlsRequiredMembers() {
        return dlsRequiredMembers;
    }

    public MemberCountResponse setDlsRequiredMembers(long dlsRequiredMembers) {
        this.dlsRequiredMembers = dlsRequiredMembers;
        return this;
    }
}
