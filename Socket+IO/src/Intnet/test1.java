package Intnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class test1 extends JFrame {
    static Socket socket = null;
    String name = null;
    BufferedReader in;
    PrintWriter out;


    JTextArea jtextarea_north = new JTextArea(14,40);    // 创建聊天区
    JTextArea jtextarea_south = new JTextArea(5,40);   //  创建输入的文本区
    JScrollPane jsp_north = new JScrollPane(jtextarea_north);    // 创建滚动的聊天区   将聊天区叫到滚动区里面
    JScrollPane jsp_south = new JScrollPane(jtextarea_south);

    JButton jbutton_send = new JButton("发送");        // 创建发送的按钮
    JButton jbutton_close = new JButton("关闭");

    JPanel pan_north = new JPanel();      //  创建面板
    JPanel pan_south = new JPanel();
    JPanel pan_picture = new JPanel();
    JPanel pan_button = new JPanel();


    JFrame jframe1 = new JFrame("登录界面");
    JLabel[] array = {new JLabel("用户名"),new JLabel("密码")};
    JTextField jname = new JTextField();
    JPasswordField jpassword = new JPasswordField();
    JButton enter = new JButton("登录");
    JButton cancel = new JButton("取消");

    Thread receive = new Thread(new receiveThread());


    public void enter()
    {

        jframe1.setLayout(null);
        array[0].setBounds(20,20,50,25);
        jname.setBounds(70,20,170,25);
        array[1].setBounds(20,60,50,25);
        jpassword.setBounds(70,60,170,25);
        jframe1.setBounds(700,500,280,220);
        enter.setBounds(50, 100, 70, 30);
        cancel.setBounds(150, 100, 70, 30);
        jframe1.add(enter);
        jframe1.add(cancel);
        jframe1.add(jname);
        jframe1.add(jpassword);
        jframe1.add(array[0]);
        jframe1.add(array[1]);
        jframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe1.setVisible(true);
        jframe1.setResizable(false);
    }

    public void test(){
        JFrame jframe = new JFrame(name);
        jframe.setSize(700,500);   // 设置框架的大小
        jframe.setLocation(500, 300);
        jframe.setLayout(null);    //  取消框架的布局管理

        pan_north.setBounds(30,10,500,300);                //  设置聊天区的位置
        pan_north.add(jsp_north);
        pan_north.setBorder(new TitledBorder("111"));
        jframe.add(pan_north);


        pan_south.setBounds(30,300,500,120);               // 设置输入区的位置
        pan_south.setBorder(new TitledBorder("222"));
        pan_south.add(jsp_south);
        jframe.add(pan_south);

        pan_button.setBounds(330,400,200,60);
        pan_button.setBorder(new TitledBorder("444"));       // 设置按钮的位置
        pan_button.add(jbutton_close);
        pan_button.add(jbutton_send);
        jframe.add(pan_button);


        pan_picture.setBounds(520,10,180,430);              // 设置图像区的位置
        pan_picture.setBorder(new TitledBorder("333"));
        jframe.add(pan_picture);


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setResizable(false);       // 设置jframe不可以随意拉动
        jtextarea_north.setEditable(false);
        jtextarea_north.setLineWrap(true);     //  设置超出字符自动换行
        jtextarea_south.setLineWrap(true);

    }
    public void enter_Listener()
    {

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if(jname.getText().equals("Lin")&&String.valueOf(jpassword.getPassword()).equals("123"))
                {

                    try {
                        socket = new Socket("127.0.0.1", 5678);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    System.out.println("连上服务端");
                    receive.start();
                    name = "Lin";
                    jframe1.setVisible(false);
                    out.println(name);      //  传入登录名字
                    out.flush();
                    test();      //  执行客户端框架
                    //  添加该用户的聊天图像
                    ImageIcon ib = new ImageIcon("C:\\Users\\asus\\Desktop\\timg.jpg");
                    ib.setImage(ib.getImage().getScaledInstance(300, 500, Image.SCALE_DEFAULT));
                    JLabel bg = new JLabel(ib);
                    pan_picture.add(bg);
                    bg.setOpaque(true);

                    setListener();     // 响应事件
                }
                else {
                    JOptionPane.showMessageDialog(jframe1, "用户名或者密码错误");
                }
            }

        });
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                System.exit(0);
            }

        });
    }



    public void setListener()
    {
        jtextarea_south.addKeyListener(new KeyAdapter()    // 设置键盘的监听
        {

            public void keyPressed(KeyEvent e)
            {

                if((e.getKeyCode()==KeyEvent.VK_ENTER));
                {
                    String s = jtextarea_south.getText();      // 获取文本的输入消息
                    String str = s.replaceAll("\r|\n", "");   //消去空格回车行
                    jtextarea_south.setText("");               // 清空已经发送出去的文本
                    jtextarea_north.append("你说:"+str+"\n");
                    int height =10000;
                    Point p = new Point();
                    p.setLocation(0, jtextarea_north.getLineCount()*height);
                    jsp_north.getViewport().setViewPosition(p);
                    try {
                        out.println(str);
                        out.flush();
                        if(str.equals("end")) {
                            socket.close();
                        }
                    }catch(Exception e1) {
                        JOptionPane.showMessageDialog(test1.this, "出错了发送不成功");
                        e1.printStackTrace();
                    }
                }
            }






        });


        jbutton_send.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String s = jtextarea_south.getText();
                String str = s.replaceAll("\r|\n", "");
                jtextarea_north.append("你说:"+jtextarea_south.getText()+"\n");
                jtextarea_south.setText("");
                int height =10000;
                Point p = new Point();
                p.setLocation(0, jtextarea_north.getLineCount()*height);
                jsp_north.getViewport().setViewPosition(p);
                try {
                    out.println(str);
                    out.flush();
                    if(str.equals("end")) {
                        socket.close();
                    }
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(test1.this, "出错了发送不成功");
                    e1.printStackTrace();
                }
            }

        });
        jbutton_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);	    // 清空面板
            }
        });


    }

    class receiveThread implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                String str1 = in.readLine();
                String message1 = str1.split("-\\|1\\|2\\|-")[0];
                String name1 = str1.split("-\\|1\\|2\\|-")[1];
                jtextarea_north.append(name1+"已经上线"+"\n");
                jtextarea_north.append(name1+"说:"+message1+"\n");

                while(socket!=null)
                {
                    int height =10000;
                    Point p = new Point();
                    p.setLocation(0, jtextarea_north.getLineCount()*height);
                    jsp_north.getViewport().setViewPosition(p);
                    String str = in.readLine();
                    System.out.println(str);
                    String message = str.split("-\\|1\\|2\\|-")[0];
                    String name = str.split("-\\|1\\|2\\|-")[1];
                    jtextarea_north.append(name+"说:"+message+"\n");

                }
            }catch(Exception e) {

            }
        }

    }
    public test1() throws UnknownHostException, IOException
    {

    }
    public static void main(String[] args) throws Exception
    {
        test1 t = new test1();
        t.enter();
        t.enter_Listener();
    }

}