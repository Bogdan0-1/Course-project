import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    private int basePrice;

    public int getPrice(Session session,Seat seat){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


        LocalDate date = LocalDate.parse(session.getDate(), dateFormatter);
        LocalTime time = LocalTime.parse(session.getTime(), timeFormatter);

        int basePrice = 100;

        // Вихідні дні
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            basePrice += 30;
        }

        // Вечірній час (після 18:00)
        if (time.isAfter(LocalTime.of(18, 0))) {
            basePrice += 20;
        }

        // Ціна залежно від місця
        if (seat.getNumber() >= 1 && seat.getNumber() <= 5) {
            basePrice += 50;
        }
        this.basePrice = basePrice;
        return basePrice;

    }
    public int makePayment(int pay){
        return pay-basePrice;
    }
}
