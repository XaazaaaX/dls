package de.dlsa.api.shared;

import de.dlsa.api.entities.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MemberInfo {
/*
    private Member member;
    private final DateTimeFormatter dateStringFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public MemberInfo(Member member) {
        this.setMember(member);
    }

    public void printHistory() {
        System.out.println("--------- Changes for Member " + member.getFullName() + " ---------");

        Collection<MemberChanges> changes = HibernateUtil.getMemberChanges(member.getId());

        for (MemberChanges change : changes) {
            System.out.printf("Spalte '%s': '%s' -> '%s'%n",
                    change.getColumn(), change.getOldValue(), change.getNewValue());
        }

        System.out.println("--------- End of changes ---------");
    }

    public Boolean compareMembers(BasicMember bm) {
        Member m = getMemberStateFromDate(bm, LocalDate.now().plusDays(1));

        return m.getActive().equals(member.getActive())
                && convertToLocalDate(m.getEntryDate()).equals(convertToLocalDate(member.getEntryDate()))
                && convertToLocalDate(m.getLeavingDate()).equals(convertToLocalDate(member.getLeavingDate()));
    }

    public Member getMemberStateFromDate(BasicMember bm, LocalDate dt) {
        Member m = new Member();
        m.setActive(bm.getActive());
        m.setEntryDate(bm.getEntryDate());
        m.setLeavingDate(bm.getLeavingDate());

        Collection<MemberChanges> changes = HibernateUtil.getMemberChanges(member.getId(), convertToDate(dt));

        for (MemberChanges change : changes) {
            switch (change.getColumn()) {
                case "ACTIVE":
                    m.setActive(Boolean.parseBoolean(change.getNewValue()));
                    break;
                case "ENTRYDATE":
                    m.setEntryDate(parseDate(change.getNewValue()));
                    break;
                case "LEAVINGDATE":
                    m.setLeavingDate(parseDate(change.getNewValue()));
                    break;
                case "GROUP":
                    Collection<Group> groups = new ArrayList<>();
                    String[] ids = change.getNewValue().trim().split(" ");
                    for (String id : ids) {
                        if (!id.isBlank()) {
                            Group currentGroup = (Group) HibernateUtil.getUnique(Group.class, "id = " + id);
                            GroupInfo gi = new GroupInfo(currentGroup);
                            groups.add(gi.getGroupStateFromDate(
                                    HibernateUtil.getBasicGroup(currentGroup.getId()), convertToDate(dt)));
                        }
                    }
                    m.setGroups(groups);
                    break;
            }
        }

        return m;
    }

    public void updateMember(Member m) {
        m.removeAllPropertyChangeListeners();
        Collection<MemberChanges> changes = HibernateUtil.getMemberChanges(member.getId());

        for (MemberChanges change : changes) {
            switch (change.getColumn()) {
                case "ACTIVE":
                    m.setActive(Boolean.parseBoolean(change.getNewValue()));
                    break;
                case "ENTRYDATE":
                    m.setEntryDate(parseDate(change.getNewValue()));
                    break;
                case "LEAVINGDATE":
                    m.setLeavingDate(parseDate(change.getNewValue()));
                    break;
                case "GROUP":
                    Collection<Group> groups = new ArrayList<>();
                    String[] ids = change.getNewValue().trim().split(" ");
                    for (String id : ids) {
                        if (!id.isBlank()) {
                            Group currentGroup = (Group) HibernateUtil.getUnique(Group.class, "id = " + id);
                            GroupInfo gi = new GroupInfo(currentGroup);
                            groups.add(gi.getGroupStateFromDate(
                                    HibernateUtil.getBasicGroup(currentGroup.getId()), new Date()));
                        }
                    }
                    m.setGroups(groups);
                    break;
            }
        }

        HibernateUtil.save(m);
    }

    // ------------------ Hilfsmethoden ------------------

    private Date parseDate(String str) {
        try {
            LocalDate localDate = LocalDate.parse(str, dateStringFormat);
            return convertToDate(localDate);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private Date convertToDate(LocalDate date) {
        if (date == null) return null;
        return java.sql.Date.valueOf(date);
    }

    // ------------------ Getter & Setter ------------------

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    */
}

