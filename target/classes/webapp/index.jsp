<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>


<html>
<head>
    <title>The Pupper Palace</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

    <script
            src="https://code.jquery.com/jquery-2.2.4.min.js"
            integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
            crossorigin="anonymous"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1><i class="fa fa-paw" aria-hidden="true"></i> The Pupper Palace</h1>
    <h4>Welcome to The Pupper Palace:</h4>

    <p><i class="fa fa-clock-o" aria-hidden="true"></i> Current time at The Pupper Palace: <strong><%= new Date() %>
    </strong></p>

    <h3><i class="fa fa-calendar-check-o" aria-hidden="true"></i> Booking status</h3>

    <p><% request.getAttribute("vacancyStatus"); %></p>

    <table class="table table-striped" style="max-width: 600px;">
        <tr>
            <th>Available pens</th>
        </tr>

        <% for (int i = 0; i < ((List<String>) request.getAttribute("availablePenNames")).size(); i++) { %>
        <tr>
            <td><%=((List<String>) request.getAttribute("availablePenNames")).get(i)%>
            </td>
        </tr>
        <% } %>


    </table>


    <div class="panel panel-default">
        <div class="panel-heading"><h3><i class="fa fa-id-badge" aria-hidden="true"></i> Pen booking</h3></div>
        <div class="panel-body">
            <form action="#" method="POST" style="max-width: 320px;">
                <div class="form-group">

                    <label for="name">Dog Name:</label>
                    <input type="text" class="form-control" id="name" name="name" required
                           placeholder="Your pupper's name!">
                </div>

                <div class="form-group">
                    <label for="size">Dog size:</label>

                    <div class="radio">
                        <label><input type="radio" id="size" name="size" value="small" checked="checked" required>Small</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="size" value="medium">Medium</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="size" value="giant">Giant!</label>
                    </div>
                </div>


                <div class="form-group">
                    <label for="status">Action:</label>

                    <div class="radio">
                        <label><input type="radio" id="status" name="status" value="checkin" checked="checked" required>Check
                            in</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="status" value="checkout">Check out</label>
                    </div>
                </div>

                <div class="form-group">

                    <label for="password">Pupper password:</label>
                    <input type="password" class="form-control" id="password" name="password" required
                           placeholder="Your pupper's top secret password!">
                </div>

                <button type="submit" class="btn-success btn"><i class="fa fa-paw" aria-hidden="true"></i> Submit
                </button>
                <br>
            </form>
        </div>q
    </div>


    <br/>
    <br/>

    <div class="well">
        <a href="/index" class="btn btn-primary"><i class="fa fa-refresh" aria-hidden="true"></i> Refresh</a>
    </div>

    <footer style="margin-bottom: 36px;">
        &copy; Copyright <%= new SimpleDateFormat("yyyy").format(new Date()) %> The Pupper Palace, all pups reserved.
    </footer>
</div>
</body>
</html>