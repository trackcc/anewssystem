<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%
    DiskFileUpload fu = new DiskFileUpload();
    // 设置允许用户上传文件大小,单位:字节
    fu.setSizeMax(10000000);
    // 设置最多只允许在内存中存储的数据,单位:字节
    fu.setSizeThreshold(4096);
    // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
    fu.setRepositoryPath("D:\\");
    //开始读取上传信息
    List fileItems = fu.parseRequest(request);
    // 依次处理每个上传的文件
    Iterator iter = fileItems.iterator();
    while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        //忽略其他不是文件域的所有表单信息
        if (!item.isFormField()) {
            String name = item.getName();
            System.out.println(name);
			try {
	            item.write(new File("D:\\" + name));
			} catch(Exception ex) {
				ex.printStackTrace();
			}
        }
    }
%>
{success:true}
