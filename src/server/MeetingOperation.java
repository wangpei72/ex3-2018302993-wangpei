package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import bean.Meeting;
import bean.User;
import rface.MeetingInterface;

/**
 * 扩展UnicastRemoteObject类，并实现远程接口HelloInterface
 *
 * @author wben
 */
public class MeetingOperation extends UnicastRemoteObject implements MeetingInterface {

    //用户
    private Vector<User> users = new Vector<User>();

    //会议
    private Vector<Meeting> meetings = new Vector<Meeting>();

    public Vector<User> getUsers() throws RemoteException {
        return users;
    }

    public Vector<Meeting> getMeetings() throws RemoteException {
        return meetings;
    }

    //用于时间转换格式
    public SimpleDateFormat myFmt = new SimpleDateFormat("yyyy/MM/dd/HH:mm");

    /**
     * <p>
     * /**
     * 必须定义构造方法，即使是默认构造方法，也必须把它明确地写出来，因为它必须抛出出RemoteException异常
     */
    public MeetingOperation() throws RemoteException {

    }

    //注册
    public boolean register(String userName, String userPass) throws RemoteException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(userName)) {
                return false;
            }
        }
        //加入其中
        users.add(new User(userName, userPass));
        return true;
    }

    //注册
    public boolean login(String userName, String userPass) throws RemoteException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(userName)
                    && users.get(i).getPassword().equals(userPass)) {
                return true;
            }
        }
        return false;
    }

    //增加会议
    public String addMeeting(String Sponsor, String Participant, String start, String end, String title) throws RemoteException, ParseException {
        //同一个用户情况
        if (Sponsor.equals(Participant)) {
            return "Incorrect participant information";
        }
        boolean flag = false;
        //找到符合条件的参与者
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(Participant)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return "Incorrect participant information";
        }
        Date startTime = myFmt.parse(start);
        Date endTime = myFmt.parse(start);
        //判断时间是否重叠
        for (int i = 0; i < meetings.size(); i++) {
            if (startTime.after(meetings.get(i).getStart()) && startTime.before(meetings.get(i).getEnd())
                    || endTime.after(meetings.get(i).getStart()) && endTime.before(meetings.get(i).getEnd())) {
                return "Failed to add meeting, overlap with existing meeting time";
            }
        }
        meetings.add(new Meeting(Sponsor, Participant, startTime, endTime, title));
        return "add meeting successfully";
    }

    public String deleteMeeting(String id) throws RemoteException {
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getId().equals(id)) {
                meetings.remove(i);
                return "delete successful";
            }
        }
        return "No corresponding meeting was found, deletion failed";
    }

    public String clearMeeting() throws RemoteException {
        meetings.clear();
        return "Clear all meetings";
    }

    public Vector<Meeting> queryMeeting(String start, String end) throws RemoteException, ParseException {
        Vector<Meeting> returnMeetings = new Vector<Meeting>();
        Date startTime = myFmt.parse(start);
        Date endTime = myFmt.parse(end);
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getStart().after(startTime) && meetings.get(i).getEnd().before(endTime)) {
                returnMeetings.add(meetings.get(i));
            }
        }
        return returnMeetings;
    }
}
