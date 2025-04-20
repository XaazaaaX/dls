package de.dlsa.api.shared;

import de.dlsa.api.entities.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseOfYearWorker {
/*
    private Settings settings;
    private Year year;
    private Member member;
    private List<Booking> bookings;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate lastCOYDueDate;
    private Double achievedDls;
    private int monthCount;

    private final DateTimeFormatter dateStringFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CourseOfYearWorker(Year year, Settings settings) {
        this.settings = settings;
        this.year = year;
        calculateDates();
    }

    public CourseOfYearWorker(Year year, Settings settings, Date lastCOYDueDate) {
        this.settings = settings;
        this.year = year;
        this.setLastCOYDueDate(convertToLocalDate(lastCOYDueDate));
        calculateDates();
    }

    private void calculateDates() {
        if (year != null && settings != null && settings.getDueDate() != null) {
            toDate = LocalDate.parse(settings.getDueDate() + "." + year.getYear(), dateStringFormat);
            fromDate = toDate.minusYears(1).plusDays(1);

            if (lastCOYDueDate != null && fromDate.isBefore(lastCOYDueDate)) {
                fromDate = lastCOYDueDate.plusDays(1);
            }
        }
    }

    public Boolean isMemberLiberated() {
        return getMemberLiberated(member);
    }

    private Boolean getMemberLiberated(Member m) {
        if (m.getActive() != null && !m.getActive()) {
            return true;
        }

        if (m.getAge(toDate) != -1 && m.getAge(toDate) < settings.getAgeFrom()) {
            return true;
        }

        if (m.getAge(fromDate) != -1 && m.getAge(fromDate) >= settings.getAgeTo()) {
            return true;
        }

        if (m.getEntryDate() != null && convertToLocalDate(m.getEntryDate()).isAfter(toDate)) {
            return true;
        }

        if (m.getLeavingDate() != null && convertToLocalDate(m.getLeavingDate()).isBefore(fromDate)) {
            return true;
        }

        for (Group g : m.getGroups()) {
            if (g.getLiberated()) {
                return true;
            }
        }

        return false;
    }

    public double getMemberDebit(List<Booking> bookings, int fullMonth) {
        double sum = 0;

        for (Booking b : bookings) {
            LocalDate dDate = convertToLocalDate(b.getDoneDate());

            if ((fromDate.isBefore(dDate) || fromDate.isEqual(dDate)) &&
                    (toDate.isAfter(dDate) || toDate.isEqual(dDate))) {
                sum += b.getCountDls();
            }
        }

        achievedDls = sum;
        sum = getRequiredDls(fullMonth) - sum;

        if (sum < 0) {
            sum = 0;
        }

        return sum * settings.getCostDls();
    }

    public double getRequiredDls(int fullMonth) {
        return (((double) Math.round(settings.getCountDls() / 12 * fullMonth * 10)) / 10);
    }

    public int getFullDlsMonth() {
        monthCount = 0;
        LocalDate month = getStartMonthDate(fromDate);
        List<LocalDate> months = new ArrayList<>();

        do {
            months.add(month);
            month = getNextMonth(month);
            monthCount++;
        } while (month.isBefore(toDate) || month.isEqual(toDate));

        MemberInfo mi = new MemberInfo(member);
        int dlsLibMonth = 0;

        for (LocalDate dt : months) {
            Member m = mi.getMemberStateFromDate(
                    HibernateUtil.getBasicMember(member.getId()),
                    getLastDayOfMonth(dt).plusDays(1)
            );
            m.setBirthdate(member.getBirthdate());
            if (getMemberLiberated(m)) {
                dlsLibMonth++;
            }
        }

        return monthCount - dlsLibMonth;
    }

    private LocalDate getNextMonth(LocalDate dt) {
        return dt.withDayOfMonth(1).plusMonths(1);
    }

    private LocalDate getLastDayOfMonth(LocalDate dt) {
        return dt.withDayOfMonth(1).plusMonths(1).minusDays(1);
    }

    private LocalDate getStartMonthDate(LocalDate dt) {
        if (dt.getDayOfMonth() == 1) {
            return dt;
        } else {
            return dt.withDayOfMonth(1).plusMonths(1);
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    // Getter & Setter

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public LocalDate getLastCOYDueDate() {
        return lastCOYDueDate;
    }

    public void setLastCOYDueDate(LocalDate lastCOYDueDate) {
        this.lastCOYDueDate = lastCOYDueDate;
    }

    public Double getAchievedDls() {
        return achievedDls;
    }

    public void setAchievedDls(Double achievedDls) {
        this.achievedDls = achievedDls;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

 */
}

