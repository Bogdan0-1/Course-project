import java.util.List;

public interface Request_to_System {
    List<Session> List_Session_Hall(int hall);
    List<String> Search_film_like(String film_name);
    List<String> Session_by_time_between(String start_time, String end_time);
    int  All_money();
    StringBuilder  How_many_tickets_sold_movie();
    String Most_Popular_Film();
    StringBuilder For_every_janr_popular_movie();
    StringBuilder All_film_dont_sale();
    StringBuilder Popular_and_not_sales();


}
