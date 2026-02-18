package org.example.factory_core_manager.enumeration;

import com.github.mfathi91.time.PersianDate;

public enum PersianMonth {
    FARVARDIN(1, 31),
    ORDIBEHESHT(2, 31),
    KHORDAD(3, 31),
    TIR(4, 31),
    MORDAD(5, 31),
    SHAHRIVAR(6, 31),
    MEHR(7, 30),
    ABAN(8, 30),
    AZAR(9, 30),
    DEY(10, 30),
    BAHMAN(11, 30),
    ESFAND(12, 29); // ممکنه ۳۰ روزه بشه اگر کبیسه بود

    private final int monthNumber;
    private final int days;

    PersianMonth(int monthNumber, int days) {
        this.monthNumber = monthNumber;
        this.days = days;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getDays(int year) {
        if (this == ESFAND && PersianDate.isLeapYear(year)) {
            return 30;
        }
        return days;
    }

    public static PersianMonth getPersianMonth(int monthNumber) {
        return PersianMonth.values()[monthNumber - 1];
    }
}
