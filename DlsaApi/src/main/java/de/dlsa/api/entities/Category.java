package de.dlsa.api.entities;

import de.dlsa.api.responses.CategoryResponse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "Sparten")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "spartenname", unique = true)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Collection<Member> member = new ArrayList<Member>();

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

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
