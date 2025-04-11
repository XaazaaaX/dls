package de.dlsa.api.dtos;

import de.dlsa.api.responses.MemberResponse;

public class MemberDto {
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public MemberDto setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }
}
