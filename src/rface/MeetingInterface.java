package rface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Vector;

import bean.Meeting;
import bean.User;

/**
 * 远程接口必须扩展接口java.rmi.Remote
 *
 * @author wben
 */
public interface MeetingInterface extends Remote {

    /**
     * 远程接口方法必须抛出 java.rmi.RemoteException
     */
    //获取user
    public Vector<User> getUsers() throws RemoteException;

    //获取会议
    public Vector<Meeting> getMeetings() throws RemoteException;

    //注册
    public boolean register(String userName, String userPass) throws RemoteException;

    //登陆
    public boolean login(String userName, String userPass) throws RemoteException;

    //增加会议
    public String addMeeting(String Sponsor, String Participant, String start, String end, String title) throws RemoteException, ParseException;

    //删除会议
    public String deleteMeeting(String id) throws RemoteException;

    //清空会议
    public String clearMeeting() throws RemoteException;

    //查询会议
    public Vector<Meeting> queryMeeting(String start, String end) throws RemoteException, ParseException;
}
