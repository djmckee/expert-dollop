/**
 * Created by djmckee on 04/11/2016.
 */

import uk.ac.ncl.csc3422.kennelbooking.DogSize;
import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.Pen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum Action {
    Checkin,
    Checkout
}

public class IndexServlet extends HttpServlet {

    private static final String HEADER_CONSTANT = "<head>\n" +
            "    <title>D-Dawg Kennels</title>\n" +
            "\n" +
            "    <!-- Latest compiled and minified CSS -->\n" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"\n" +
            "          integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
            "\n" +
            "    <!-- Optional theme -->\n" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\"\n" +
            "          integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">\n" +
            "    <link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\" rel=\"stylesheet\"\n" +
            "          integrity=\"sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN\" crossorigin=\"anonymous\">\n" +
            "   <script\n" +
            "  src=\"https://code.jquery.com/jquery-2.2.4.min.js\"\n" +
            "  integrity=\"sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=\"\n" +
            "  crossorigin=\"anonymous\"></script> <!-- Latest compiled and minified JavaScript -->\n" +
            "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"\n" +
            "            integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\"\n" +
            "            crossorigin=\"anonymous\"></script>\n" +
            "</head>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        KennelManager kennelManager = KennelManager.getInstance();

        Kennel kennel = kennelManager.getKennel();

        String vacancyString = "No vacancies, sorry!";

        if (kennel.getVacancies()) {
            // Vacancies available...
            vacancyString = "Vacancies available. Book using the form below!";
        }


        List<Pen> pens = kennel.getPens();
        List<String> vacantPenInfo = new ArrayList<String>();
        List<String> availablePenNames = new ArrayList<String>();
        List<String> allPenNumbers = new ArrayList<String>();

        for (Pen pen : pens) {
            allPenNumbers.add(String.format("%d", pen.getPenNumber()));
            if (pen.isVacant()) {
                availablePenNames.add(pen.toString());
                vacantPenInfo.add(pen.toString());
            }
        }

        request.setAttribute("allPenNumbers", allPenNumbers);
        request.setAttribute("availablePenNames", availablePenNames);
        request.setAttribute("availablePens", vacantPenInfo);
        request.setAttribute("vacancyStatus", vacancyString);
        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Perform form POST request...
        String dogName = request.getParameter("name");


        String actionName;

        if (dogName == null || dogName.length() == 0) {
            // Name required.
            handleError(response, "Your pupper's name is required!");
            return;
        }

        Action action;
        String actionString = request.getParameter("status");

        if (actionString.equals("checkin")) {
            action = Action.Checkin;
            actionName = "checked in";
        } else if (actionString.equals("checkout")) {
            action = Action.Checkout;
            actionName = "checked out";
        } else {
            // Unknown action - bad request error...
            handleError(response, "Not a valid action! You cheeky pup.");
            return;
        }

        DogSize dogSize = null;
        String dogSizeString = request.getParameter("size");
        if (dogSizeString.equals("small")) {
            dogSize = DogSize.SMALL;
        } else if (dogSizeString.equals("medium")) {
            dogSize = DogSize.MEDIUM;
        } else if (dogSizeString.equals("giant")) {
            dogSize = DogSize.GIANT;
        } else {
            // Unknown action - bad request error...
            handleError(response, "Not a valid dog size! Please check your dog size selection and try again. You cheeky pup.");
            return;
        }

        KennelManager kennelManager = KennelManager.getInstance();

        Kennel kennel = kennelManager.getKennel();


        if (action == Action.Checkin) {
            // Attempt a checkin...

            Pen bookedPen = kennel.bookPen(dogSize, dogName);

            if (bookedPen != null) {
                // Success
                actionName = "checked in to pen " + bookedPen.getPenNumber();
                renderSuccess(response, dogName, actionName);
                return;

            } else {
                // Invalid pen, raise an error
                handleError(response, "Check-in failed! You cheeky pup. Please check availability for your dog size and try again.");
                return;
            }
        } else if (action == Action.Checkout) {
            // Attempt check out...

            if (kennel.checkout(dogName)) {
                renderSuccess(response, dogName, actionName);
                return;

            } else {
                // Invalid pen, raise an error
                handleError(response, "Checkout failed! You cheeky pup. Please check your dog name and try again.");
                return;
            }
        }


    }

    private void handleError(HttpServletResponse response, String errorText) throws IOException {
        // It's a bad request that we're handling...
        response.setStatus(400);

        // Error GIF: https://media.giphy.com/media/14bzmmBo252Zzi/giphy.gif
        response.getWriter().print("<html>" + HEADER_CONSTANT + "<body class=\"container\"><h2><i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i>\n Something went wrong!</h2><p>" + errorText + "</p><br/><br/><a href=\"/index\"  class=\"btn btn-success\">Back to booking list</a><br/><br/><br/>  <img height=\"120\" alt=\"Awesome doge gif\" src=\"https://media.giphy.com/media/14bzmmBo252Zzi/giphy.gif\" /> </body></html>");


    }

    private void renderSuccess(HttpServletResponse response, String dogName, String actionText) throws IOException {
        String[] gifUrls = {
                "https://media.giphy.com/media/fpXxIjftmkk9y/giphy.gif",
                "https://media.giphy.com/media/9fuvOqZ8tbZOU/giphy.gif",
                "https://media.giphy.com/media/Le7CoR9daihzi/giphy.gif",
                "https://media.giphy.com/media/Pk20jMIe44bHa/giphy.gif",
                "https://media.giphy.com/media/3o7TKvLix7esxtgXg4/giphy.gif",
                "https://media.giphy.com/media/8dNZXw6LOlgnm/giphy.gif",
                "https://media.giphy.com/media/l0COJ0jTGnPHh8Ua4/giphy.gif"
        };

        int randomNumber = (int) (Math.random() * gifUrls.length);

        String gifUrl = gifUrls[randomNumber];

        response.getWriter().print("<html>" + HEADER_CONSTANT + "<body class=\"container\"><h2><i class=\"fa fa-paw\" aria-hidden=\"true\"></i> Congratulations!</h2><p>" + dogName + " has been " + actionText + " to D-Dawg's kennels successfully. Have a pupper-filled day!</p><br/><a href=\"/index\"  class=\"btn btn-success\">Back to booking list</a><br/><br/><br/> <img height=\"200\" alt=\"Awesome doge gif\" src=\"" + gifUrl + "\" /> </body></html>");

    }


}