<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>


<html>
<head>
    <title>D-Dawg Kennels</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1><i class="fa fa-paw" aria-hidden="true"></i> D-Dawg Kennels</h1>
    <h4>Welcome to D-Dawg Kennels:</h4>

    <p><i class="fa fa-clock-o" aria-hidden="true"></i> Current time at D-Dawg kennels: <strong><%= new Date() %>
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


    <h3><i class="fa fa-id-badge" aria-hidden="true"></i> Pen booking</h3>

    <form action="#" method="POST" style="max-width: 320px;">
        <div class="form-group">

            <label for="name">Dog Name:</label>
            <input type="text" class="form-control" id="name" name="name" required placeholder="Your pupper's name!">
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
                <label><input type="radio" id="status" name="status" value="checkin" checked="checked" required>Check in</label>
            </div>
            <div class="radio">
                <label><input type="radio" name="status" value="checkout">Check out</label>
            </div>
        </div>

        <button type="submit" class="btn-success btn"><i class="fa fa-paw" aria-hidden="true"></i> Submit</button>
        <br>
    </form>


    <br/>
    <br/>
    <a href="/index" class="btn btn-default">Refresh</a>
</div>
</body>
</html>