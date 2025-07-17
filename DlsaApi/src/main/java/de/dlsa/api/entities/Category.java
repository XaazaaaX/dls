package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Entität zur Repräsentation einer Sparte (z. B. "Fußball", "Tennis", "Verwaltung").
 * Eine Sparte kann mehreren Mitgliedern zugeordnet sein, und ein Mitglied kann mehreren Sparten angehören.
 *
 * Die Beziehung ist Many-to-Many und wird auf der Member-Seite aktiv verwaltet.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Sparten")
public class Category extends BaseEntity {

    /**
     * Name der Sparte.
     * Muss eindeutig sein (unique).
     */
    @Column(name = "spartenname", unique = true)
    private String categoryName;

    /**
     * Mitglieder, die dieser Sparte zugeordnet sind.
     * Die Verwaltung erfolgt über die `categories`-Liste in der Member-Klasse (inverse Seite).
     */
    @ManyToMany(mappedBy = "categories")
    private Collection<Member> member = new ArrayList<>();

    // ===== Getter & Setter (Fluent API) =====

    public String getCategoryName() {
        return categoryName;
    }

    public Category setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Collection<Member> getMember() {
        return member;
    }

    public Category setMember(Collection<Member> member) {
        this.member = member;
        return this;
    }
}
