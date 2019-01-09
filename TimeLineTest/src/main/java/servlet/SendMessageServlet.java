package servlet;

import bean.Message;
import dao.MessageDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendMessageServlet extends HttpServlet {

    private String image = "";
    private String information = "";
    MessageDao messageDao = new MessageDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("method").equals("addImage")) {
                this.addImage(request, response);
            } else if (request.getParameter("method").equals("addInformation")) {
                this.addInformation(request, response);
            }
        } catch (Throwable var4) {
            var4.printStackTrace();
        }

    }


    public void addImage(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        if(image != ""){
            File defile = new File("D:/软件/IDEA/Workplace/TimeLineTest/image/" + image);
            defile.delete();
        }
        if(ServletFileUpload.isMultipartContent(request)){
            try{
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload sfu = new ServletFileUpload(factory);
                sfu.setSizeMax(10 * 1024 * 1024);
                sfu.setHeaderEncoding("utf-8");
                @SuppressWarnings("unchecked")
                List<FileItem> fileItemList = sfu.parseRequest(request);
                Iterator<FileItem> fileItems = fileItemList.iterator();
                while(fileItems.hasNext()){
                    FileItem fileItem = fileItems.next();
                    if(!fileItem.isFormField()){
                        String fileName = fileItem.getName();// 文件名称
                        System.out.println("原文件名：" + fileName);// Koala.jpg

                        String suffix = fileName.substring(fileName.lastIndexOf('.'));
                        System.out.println("扩展名：" + suffix);// .jpg

                        // 新文件名（唯一）
                        String newFileName = System.currentTimeMillis() + suffix;
                        System.out.println("新文件名：" + newFileName);//
                        File file = new File("D:/软件/IDEA/Workplace/TimeLineTest/image/" + newFileName);
                        System.out.println(file.getAbsolutePath());
                        fileItem.write(file);

                        // 6. 调用FileItem的delete()方法，删除临时文件
                        fileItem.delete();
                        this.image = newFileName;
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("status","已选择图片");
        request.setAttribute("flag", "3");
        request.getRequestDispatcher("Publish.jsp").forward(request, response);
    }

    public void addInformation(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        HttpSession session = request.getSession();
        String uerName = (String)session.getAttribute("user_id");
        System.out.println(uerName);
        this.information = new String(request.getParameter("mes_information").getBytes("ISO-8859-1"), "utf-8");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format( now );
        System.out.println(time);
        if (uerName.equals("")) {
            image = "";
            request.setAttribute("flag", "0");
            request.getRequestDispatcher("Publish.jsp").forward(request, response);
            return;
        }
        else if(information.equals("")){
            request.setAttribute("status","已选择图片");
            request.setAttribute("flag", "1");
            request.getRequestDispatcher("Publish.jsp").forward(request, response);
            return;
        }
        Message message = new Message(information,image,time,uerName);
        messageDao.addMessage(message);
        image = "";
        request.setAttribute("flag", "2");
        request.getRequestDispatcher("Publish.jsp").forward(request, response);
    }
}

