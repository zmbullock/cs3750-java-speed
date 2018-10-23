package cs3750;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloWorld extends HttpServlet
{
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    // Set the response message's MIME type
    response.setContentType("text/html;charset=UTF-8");

    // Allocate an output writer to write teh response message into the network socket
    PrintWriter out = response.getWriter();

    // Write the response message as an HTML page
    try 
    {
      out.printf(""
 + "<!DOCTYPE html>"
 + "<html>"
 + "<head>"
 + "<title>Hello, World</title>"
 + "</head>"
 + "<body>"
 + "<h1>Hello World!</h1>"
 + "<p>Request URI: %s</p>"
 + "<p>Protocol: %s</p>"
 + "<p>PathInfo: %s</p>"
 + "<p>Remote Address: %s</p>"
 + "<p>A Random Number: <strong>%d</strong></p>"
 + "</body>"
 + "</html>"
, request.getRequestURI(), request.getProtocol(), request.getPathInfo(), request.getRemoteAddr(), Math.random());
    } finally
    {
      out.close();
    }
  }

}

