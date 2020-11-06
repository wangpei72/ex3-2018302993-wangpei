package server;

import java.io.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Date;

import bean.Meeting;
import bean.User;
import rface.MeetingInterface;

/**
 * RMI Hello Server
 *
 * @author wben
 */
public class MeetingServer {

    public static MeetingInterface meetingInterface;

    public static void readData() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/server/user.data")));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(" ");
            String userName = data[0];
            String password = data[1];
            User user = new User(userName, password);
            meetingInterface.getUsers().add(user);
        }
        //序列化message
        bufferedReader = new BufferedReader(new FileReader(new File("src/server/meeting.data")));
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(" ");
            String Sponsor = data[0];
            String Participant = data[1];
            Date start = new Date(Long.parseLong(data[2]));
            Date end = new Date(Long.parseLong(data[3]));
            String title = data[4];
            String id = data[5];
            Meeting meeting = new Meeting(Sponsor, Participant, start, end, title, id);
            meetingInterface.getMeetings().add(meeting);
        }
    }

    /**
     * 启动 RMI 注册服务并进行对象注册
     */
    public static void main(String[] args) {
        try {
            // 启动RMI注册服务，指定端口为1099　（1099为默认端口）
            // 也可以通过命令 ＄java_home/bin/rmiregistry 1099启动
            // 这里用这种方式避免了再打开一个DOS窗口
            // 而且用命令rmiregistry启动注册服务还必须事先用RMIC生成一个stub类为它所用
            LocateRegistry.createRegistry(1099);

            // 创建远程对象的一个或多个实例，下面是hello对象
            // 可以用不同名字注册不同的实例
            meetingInterface = new MeetingOperation();
            readData();

            // 把hello注册到RMI注册服务器上，命名为Hello
            Naming.rebind("MeetingInterface", meetingInterface);

            // 如果要把hello实例注册到另一台启动了RMI注册服务的机器上
            // Naming.rebind("//192.168.1.105:1099/Hello",hello);
            System.out.println("Meeting Server is ready.");
        } catch (Exception e) {
            System.out.println("Meeting Server failed: " + e);
        }
    }

}
