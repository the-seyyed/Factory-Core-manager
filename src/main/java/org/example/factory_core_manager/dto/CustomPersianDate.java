package org.example.factory_core_manager.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPersianDate {

    private int year;
    private int month;
    private int day;


    @Override
    public String toString() {
        return "CustomPersianDate [year=" + year + ", month=" + month + ", day=";
    }
}
