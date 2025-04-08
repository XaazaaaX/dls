package de.dlsa.api.responses;

import java.util.Collection;

public class CategoryMemberResponse {
    private Long id;

    private String categoryName;

    private Collection<MemberResponse> member;

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
