package com.sessiontracking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SessionTracker")
public class SessionTrackingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Create or get session
        HttpSession session = request.getSession(true);

        // Set session timeout = 1 minute
        session.setMaxInactiveInterval(60);

        // Session details
        String sessionId = session.getId();
        Date creationTime = new Date(session.getCreationTime());
        Date lastAccessTime = new Date(session.getLastAccessedTime());

        // Visit count
        Integer visitCount = (Integer) session.getAttribute("visitCount");

        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }

        session.setAttribute("visitCount", visitCount);

        // Output HTML
        out.println("<html>");
        out.println("<head><title>Session Tracking</title></head>");
        out.println("<body>");

        out.println("<h2>Session Tracking Information</h2>");
        out.println("<p><b>Session ID:</b> " + sessionId + "</p>");
        out.println("<p><b>Session Creation Time:</b> " + creationTime + "</p>");
        out.println("<p><b>Last Access Time:</b> " + lastAccessTime + "</p>");
        out.println("<p><b>Visit Count:</b> " + visitCount + "</p>");
        out.println("<p><b>Session Expiry:</b> 60 seconds</p>");

        out.println("<br><a href='SessionTracker'>Refresh</a>");

        out.println("</body></html>");
    }
}