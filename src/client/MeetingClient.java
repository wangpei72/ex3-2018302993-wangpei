package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Vector;

import bean.Meeting;
import bean.User;
import rface.MeetingInterface;

/**
 * meeting客户端编写继承展现层 以及登录和注册的处理
 *
 * @author wangpei
 */
public class MeetingClient {
    public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static MeetingInterface meetingOperation;

    //客户端处理代码
    public static void clientServer(String userName) {
        try {
            //输出菜单
            System.out.println("RMI Menu:\n\t" +
                    "1. userList\n\t\targuments: no args\n\t" +
                    "2. meetingList\n\t\targuments: no args\n\t" +
                    "3. add\n\t\targuments:<username> <start> <end> <title>\n\t" +
                    "4. delete\n\t\targument:<meetingid>\n\t" +
                    "5. clear\n\t\targuments: no args\n\t" +
                    "6 query\n\t\targuments:<start> <end>\n\t" +
                    "7. help\n\t\targuments: no args\n\t" +
                    "8. quit\n\t\targuments: no args\n\n" +
                    "info:all time info should in format like:2020/10/21/15:39\n" +
                    "Input an operation:");
            String cmd = bufferedReader.readLine();
            String[] cmds = cmd.split(" ");
            switch (cmds[0]) {
                //输出user
                case "userList":
                    Vector<User> users = meetingOperation.getUsers();
                    System.out.println("user count is " + users.size());
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(users.get(i).toStringLow());
                    }
                    break;
                //输出meeting
                case "meetingList":
                    Vector<Meeting> meetings = meetingOperation.getMeetings();
                    System.out.println("meeting count is " + meetings.size());
                    for (int i = 0; i < meetings.size(); i++) {
                        System.out.println(meetings.get(i).toString());
                    }
                    break;
                //增加会议
                case "add":
                    System.out.println(meetingOperation.addMeeting(userName, cmds[1], cmds[2], cmds[3], cmds[4]));
                    break;
                //删除会议
                case "delete":
                    System.out.println(meetingOperation.deleteMeeting(cmds[1]));
                    break;
                //清空会议
                case "clear":
                    System.out.println(meetingOperation.clearMeeting());
                    break;
                //查询
                case "query":
                    Vector<Meeting> meetings1 = meetingOperation.queryMeeting(cmds[1], cmds[2]);
                    if (meetings1.size() == 0) {
                        System.out.println("No qualifying meeting");
                    } else {
                        System.out.println("qualifying meeting count is " + meetings1.size());
                        for (int i = 0; i < meetings1.size(); i++) {
                            System.out.println(meetings1.get(i).toString());
                        }
                    }
                    break;
                case "help":
                    System.out.println("help: usage <option> <args>");
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Does not meet the requirements, please re-enter");
            }
            clientServer(userName);
        } catch (Exception e) {
            System.out.println("Throw an exception,please re-enter");
            clientServer(userName);
//            e.printStackTrace();
        }

    }

    //注册或登录
    public static void registerOrLogin() throws IOException, NotBoundException {
        System.out.println("java [clientname] [servername] [portnumber] register or login [username] [password]");
        String cmd = bufferedReader.readLine();
        String[] cmds = cmd.split(" ");
        while (cmds.length != 7) {
            System.out.println("Command format is wrong, please re-enter\n");
            cmd = bufferedReader.readLine();
            cmds = cmd.split(" ");
        }
        //注册
        meetingOperation = (MeetingInterface) Naming.lookup("//" + cmds[2] + ":" + cmds[3] + "/" + "MeetingInterface");
        if (cmds[4].equals("register")) {
            //注册情况
            if (meetingOperation.register(cmds[5], cmds[6])) {
                System.out.println("register successful");
                clientServer(cmds[5]);
            } else {
                System.out.println("register fail,the account has been registered\n");
                registerOrLogin();
            }
        } else if (cmds[4].equals("login")) {
            if (meetingOperation.login(cmds[5], cmds[6])) {
                System.out.println("login successful\n");
                clientServer(cmds[5]);
            }
            //登陆失败
            System.out.println("login fail,No account found\n");
            registerOrLogin();
        } else {
            System.out.println("Command format is wrong\n");
            registerOrLogin();
        }
    }

    public static void main(String[] argv) {
        try {
            registerOrLogin();
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e);
        }
    }
}
