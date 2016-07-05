package org.darkgem.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 启动器
 */
public class Launcher {
    public static void main(String args[]) throws Exception {
        new Launcher().start();
    }

    void start() throws Exception {
        // 服务器的监听端口
        Server server = new Server(80);
        // 关联一个已经存在的上下文
        WebAppContext context = new WebAppContext();
        // 设置描述符位置
        String path = Launcher.class.getResource("/").getPath() + "../../";
        context.setDescriptor(path + "src/main/webapp/WEB-INF/web.xml");
        // 设置Web内容上下文路径
        context.setResourceBase(path + "src/main/webapp/");
        // 设置上下文路径
        context.setContextPath("/");
        //优先使用WebApp的ClassLoader
        context.setParentLoaderPriority(false);
        //开启HTML，CSS，JS热部署
        context.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        server.setHandler(context);
        // 启动
        server.start();
        server.join();
    }
}
