import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Register implements Request_to_System {
    private Order o;

    public void newOrder(){
        o = new Order();
    }
    public List<Session> loadSessions(){
        return o.loadSessions();
    }
    public List<Session> isFilm(String filmName){
        return o.isFilm(filmName);
    }
    public List<Session> isDate(String date,String time){
        return  o.isDate(date,time);
    }
    public List<Seat> isSeat(Session Session){
        return o.isSeat(Session);
    }
    public void setCinemagoer(String fullname,String email){
        o.setCinemagoer(fullname,email);
    }
    public int getPrice(Session session,Seat seat){
        return o.getPrice(session,seat);
    }
    public int makePayment(int pay){
        return o.makePayment(pay);
    }
    public void save(Seat seat, Session session, int price) {
        try (Connection connection = DB_Connection.getConnection();
             CallableStatement cstmt = connection.prepareCall("{call SAVE(?, ?, ?)}")) {

            cstmt.setInt(1, seat.getId());
            cstmt.setInt(2, session.getID_Session());
            cstmt.setInt(3, price);

            cstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Session> List_Session_Hall(int hall) {
        List<Session> Session_Hall = new ArrayList<>();
        try  (Connection con = DB_Connection.getConnection();
        CallableStatement stmt = con.prepareCall("{call 1_GET_SESSION_HALL(?)}");)
        {
            stmt.setInt(1, hall);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            int id = rs.getInt("Сеанс_ID");
            String n = rs.getString("Назва");
            int num = rs.getInt("Кінозал_ID");
            String d = rs.getString("Дата");
            String t = rs.getString("Час_початку");
            Session_Hall.add(new Session(id,n,num,d,t));
        }
            rs.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Session_Hall;
    }

    @Override
    public List<String> Search_film_like(String film_name) {
        List<String> Films = new ArrayList<>();
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 2_SEARCH_FILM_LIKE(?)}");)
        {
            stmt.setString(1, film_name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Films.add(rs.getString("Назва"));
            }
            rs.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Films;
    }

    @Override
    public List<String> Session_by_time_between(String start_time, String end_time) {
        List<String> Sessions = new ArrayList<>();
        try (Connection con = DB_Connection.getConnection();
             CallableStatement stmt = con.prepareCall("{call 3_BETWEEN(?, ?)}")) {

            stmt.setTime(1, Time.valueOf(start_time));
            stmt.setTime(2, Time.valueOf(end_time));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sessions.add(rs.getString("Назва_фільму"));
                Sessions.add(rs.getString("Дата_сеансу"));
                Sessions.add(rs.getString("Час_початку_сеансу"));
                Sessions.add(rs.getString("Номер_кінозалу"));


            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Sessions;

    }

    @Override
    public int All_money() {
        int suma = 0;
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 4_ALL_MONEY}");
              ResultSet rs = stmt.executeQuery();)
        {
            if (rs.next()) {
                suma = rs.getInt("Money");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return suma;
    }

    @Override
    public StringBuilder How_many_tickets_sold_movie() {
        StringBuilder Tickets_movie  = new StringBuilder();
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 5_EVERY_TICKET_EVERY_FILM}");
              ResultSet rs = stmt.executeQuery();)
        {
            while (rs.next()) {
                Tickets_movie.append(rs.getString("Назва_фільму")+": ");
                Tickets_movie.append(rs.getString("Кількість_проданих_квитків")+"\n");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Tickets_movie;
    }

    @Override
    public String Most_Popular_Film() {
        String film = "";
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 6_MOST_POPULAR}");
              ResultSet rs = stmt.executeQuery();)
        {
            if (rs.next()) {
                film = rs.getString("Назва_фільму");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return film;
    }

    @Override
    public StringBuilder For_every_janr_popular_movie() {
        StringBuilder Janr_movie  = new StringBuilder();
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 7_FOR_EVERY_JANR_POPULAR_FILM}");
              ResultSet rs = stmt.executeQuery();)
        {
            while (rs.next()) {
                Janr_movie.append("["+rs.getString("Жанр")+"] ");
                Janr_movie.append("["+rs.getString("Назва")+"] ");
                Janr_movie.append("["+rs.getString("Найбільша_кількість_квитків_в_жанрі")+"]\n");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Janr_movie;
    }

    @Override
    public StringBuilder All_film_dont_sale() {
        StringBuilder movie  = new StringBuilder();
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 8_FILM_NOT_SELL_TICKET}");
              ResultSet rs = stmt.executeQuery();)
        {
            while (rs.next()) {
                movie.append(rs.getString("Назва")+"\n");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return movie;    }

    @Override
    public StringBuilder Popular_and_not_sales() {
        StringBuilder movie  = new StringBuilder();
        try  (Connection con = DB_Connection.getConnection();
              CallableStatement stmt = con.prepareCall("{call 9_POPULAR_AND_NOT_SALES}");
              ResultSet rs = stmt.executeQuery();)
        {
            while (rs.next()) {
                movie.append(rs.getString("Назва")+"  ");
                movie.append("["+rs.getString("Статус")+"]\n");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return movie;
    }
}

