package ru.ylab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    public enum PlaceType {
        WORK,
        CONF_HALL;

        private String getCapitalizedName() {
            return this.name().substring(0, 1).toUpperCase() + this.name().substring(1);
        }

    }

    private long id;
    private PlaceType type;
    private LocalDateTime reservationDate;

    @Override
    public String toString() {
        return "[" + id + "] " + type.getCapitalizedName() + " " + (reservationDate == null ? "Свободно" : "Зарезервировано на " + reservationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }

}