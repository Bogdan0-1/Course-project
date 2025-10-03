import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

public class Order {
    private Schedule sc = new Schedule();
    private Hall hl = new Hall();
    private Calc cl = new Calc();
    private Session session;
    private int seat;
    private String fullname;
    private String email;

    public List<Session> loadSessions(){
        return sc.loadSessionList();
    }

    public List<Session> isFilm(String Film){
        return sc.refreshF(Film);
    }
    public List<Session> isDate(String date, String time){
        return sc.refreshD(date,time);
    }

    public List<Seat> isSeat(Session session){
        return  hl.refreshS(session);

    }
    public void setCinemagoer(String fullname,String email){
        try(Connection connection = DB_Connection.getConnection();
            CallableStatement cstmt = connection.prepareCall("{call INSERT_CINEMAGOER(?,?)}");
            ){
                cstmt.setString(1, fullname);
                cstmt.setString(2, email);

                cstmt.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    public int getPrice(Session session,Seat seat){
        return cl.getPrice(session,seat);
    }
    public int makePayment(int pay){
        return cl.makePayment(pay);
    }

}
