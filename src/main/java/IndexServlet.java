/**
 * Created by djmckee on 04/11/2016.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().print(
                "<html><body><h3>Enter username:</h3><form action=\"index\" and method=\"post\">\n" +
                        "  Username:<br>\n" +
                        "  <input type=\"text\" name=\"username\"><br>\n"
                        + "<input type=\"checkbox\" name=\"catgifs\" value=\"catgifs\"> Cat gifs?<br>"
                        + "   <input type=\"submit\" value=\"Submit\">\n" +
                        "</form></body></html>"
        );

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Perform form POST request...
        String username = request.getParameter("username");
        String catGifsString = request.getParameter("catgifs");

        boolean showCatGifs = false;

        if (catGifsString != null) {
            if (catGifsString.length() > 0) {
                showCatGifs = true;
            }
        }


        String catGifsHtml = "";

        String[] gifUrls = {
                "https://media.giphy.com/media/GvMSpPx44XlFm/giphy.gif",
                "https://media.giphy.com/media/JHCcEc9vLvHZS/giphy.gif",
                "https://media.giphy.com/media/idwespBy17WMw/giphy.gif",
                "https://media.giphy.com/media/o0vwzuFwCGAFO/giphy.gif"
        };

        int randomNumber = (int) (Math.random() * gifUrls.length);

        String gifUrl = gifUrls[randomNumber];

        if (showCatGifs) {
            catGifsHtml = "<img alt=\"Awesome cat gif\" src=\"" + gifUrl + "\" />";
        }

        response.getWriter().print("<html><body>Your username is: " + username + "<br/><br/>" + catGifsHtml + "<br/></body></html>");
    }
}