package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Meeting implements Serializable {

    //主办方 参与者 开始时间 截至时间 话题 单独id
    private String Sponsor;
    private String Participant;
    private Date start;
    private Date end;
    private String title;
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSponsor() {
        return Sponsor;
    }

    public void setSponsor(String sponsor) {
        this.Sponsor = sponsor;
    }

    public String getParticipant() {
        return Participant;
    }

    public void setParticipant(String participant) {
        this.Participant = participant;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Meeting(String sponsor, String participant, Date start, Date end, String title) {
        Sponsor = sponsor;
        Participant = participant;
        this.start = start;
        this.end = end;
        this.title = title;
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public Meeting(String sponsor, String participant, Date start, Date end, String title, String id) {
        Sponsor = sponsor;
        Participant = participant;
        this.start = start;
        this.end = end;
        this.title = title;
        UUID uuid = UUID.randomUUID();
        this.id = id;
    }

    public String toString() {
        return "[Sponsor name: " + Sponsor + ",Participant name:" + Participant +
                ",start time:" + start.toString() + ",end time:" + end.toString()
                + ",title:" + title + ",id:" + id
                + "]";
    }

}
