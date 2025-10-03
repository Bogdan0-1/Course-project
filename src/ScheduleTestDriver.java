import java.util.List;

public class ScheduleTestDriver {
    public static void main(String[] args) {
        Schedule schedule = new ScheduleStub();

        System.out.println("=== Тест refreshF(\"початок\") ===");
        List<Session> resultF = schedule.refreshF("початок");
        for (Session s : resultF) {System.out.println(s);}

        System.out.println("\n=== Тест refreshF(\"\") — всі сеанси ===");
        List<Session> allF = schedule.refreshF("");
        System.out.println("Кількість: " + allF.size());

        System.out.println("\n=== Тест refreshF(\"Шрек\") — очікується 0 ===");
        List<Session> noneF = schedule.refreshF("Шрек");
        System.out.println("Кількість: " + noneF.size());

        System.out.println("\n=== Тест refreshD(\"2025-06-10\", \"14:00:00\") ===");
        List<Session> resultD1 = schedule.refreshD("2025-06-10", "14:00:00");
        for (Session s : resultD1) {System.out.println(s);}

        System.out.println("\n=== Тест refreshD(\"2025-06-06\", null) ===");
        List<Session> resultD2 = schedule.refreshD("2025-06-06", null);
        for (Session s : resultD2) {System.out.println(s);}

        System.out.println("\n=== Тест refreshD(null, null) — всі сеанси ===");
        List<Session> resultD3 = schedule.refreshD(null, null);
        System.out.println("Кількість: " + resultD3.size());

        System.out.println("\n=== Тест refreshD(\"2025-06-08\", \"14:00:00\") — очікується 0 ===");
        List<Session> resultD4 = schedule.refreshD("2025-06-08", "14:00:00");
        System.out.println("Кількість: " + resultD4.size());
    }
}
