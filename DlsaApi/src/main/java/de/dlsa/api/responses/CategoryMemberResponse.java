package de.dlsa.api.responses;

import java.util.Collection;

/**
 * Response-Klasse zur Darstellung einer Sparte (Kategorie)
 * zusammen mit allen zugeordneten Mitgliedern.
 *
 * Diese Klasse wird typischerweise im Kontext von
 * REST-Antworten verwendet, bei denen sowohl die
 * Sparte als auch die Mitglieder angezeigt werden sollen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class CategoryMemberResponse {

    /**
     * Eindeutige ID der Sparte.
     */
    private Long id;

    /**
     * Bezeichnung der Sparte.
     */
    private String categoryName;

    /**
     * Liste der zugeordneten Mitglieder.
     */
    private Collection<MemberResponse> member;

    // --- Getter & Setter (Fluent-API) ---

    public Long getId() {
        return id;
    }

    public CategoryMemberResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CategoryMemberResponse setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Collection<MemberResponse> getMember() {
        return member;
    }

    public CategoryMemberResponse setMember(Collection<MemberResponse> member) {
        this.member = member;
        return this;
    }
}
