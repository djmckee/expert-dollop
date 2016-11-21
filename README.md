# KennelWebServer README
This is a Java Jetty based web server for the CSC3422 module of Computer Science at Newcastle University.

## Extra features.
As well as the booking function, this implementation includes the following extra features:
- All inputs are validated server-side (as well as attempts to enforce them client-side too) - on invalid input, the user is given a helpful and specific error message and a spirit-raising dog GIF _(from Giphy)_.
- Random selection of cheerful dog GIFs _(from Giphy)_ upon successful check-in or check-out
- A password is required upon dog check-in, and check-out is only allowed if the password is correct for the dog the user is trying to check out.
- Passwords are stored in memory in a HashMap data structure, and hashed using SHA256 to provide a simple layer of security.
- Passwords are validated upon check-in and must be secure enough to meet system requirements _(i.e. longer than 6 chars and not 'password')_.
- The current date/time at the kennel is injected into the page using a JSP Scriptlet
- The current copyright year is injected into the page's footer using a JSP Scriptlet
- Bootstrap has been used to style the pages

### External libraries used
As well as the libraries specified in the assignment, I used the following 3rd pary libraries:
- Bootstrap
- jQuery