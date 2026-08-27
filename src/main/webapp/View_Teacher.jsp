<%@ page import="java.util.*, java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Teacher List</title>
     <link rel="stylesheet" type="text/css" href="styles.css">
    <script>
    
    
    document.addEventListener("DOMContentLoaded", function () {
        // Fetch username from SessionServlet
        fetch("SessionServlet")
            .then(response => response.text())
            .then(username => {
                if (username !== "null") {
                    document.getElementById("welcomeMessage").innerText = "Welcome, " + username;
                    document.getElementById("profileName").innerText = username;
                }
            });

        // Show profile popup
        document.getElementById("profileButton").addEventListener("click", function () {
            let profilePopup = document.getElementById("profilePopup");
            profilePopup.style.display = (profilePopup.style.display === "block") ? "none" : "block";
        });

        // Logout function
        document.getElementById("logoutButton").addEventListener("click", function () {
            window.location.href = "LogoutServlet"; // Redirects to logout servlet
        });
    });
    
        function confirmDelete(t_id) {
            if (confirm("Do you really want to delete this teacher?")) {
                window.location.href = "DeleteTeacherServlet?t_id=" + t_id;
            }
        }
    </script>
</head>
<body> <header>
        <div class="box">
            <img src="logo1.png" alt="">
            <h1>St. Mira's College For Girls, Pune</h1>
            <br>
            <h3>Autonomous - Affiliated to Savitribai Phule Pune University * <small
                    style="font-weight: 100;font-size:smaller;">Undertaking</small></h3>
        </div>
        <div class="social">
            <ul>
                <li><img class="imgSocial" src="64-641423_blue-phone-logo-png-transparent-blue-phone-icon.png" alt="">
                </li>
                <li><img class="imgSocial" src="Logo_of_Twitter.svg.png" alt=""></li>
                <li><img class="imgSocial" src="instagram-logo-instagram-icon-transparent-free-png.webp" alt=""></li>
                <li><img class="imgSocial" src="aaw6si4mt.webp" alt=""></li>
                <li><img class="imgSocial"
                        src="red-circle-bordered-youtube-logo-with-long-shadow-on-transparent-background-free-png.webp"
                        alt=""></li>
            </ul>
        </div>
         
    </header>
    <div class="Redbox"></div>
    <div class="Bluebox">
    
        
     <nav>
    <ul id="nav1">
        <li><a href="Admin1.html" style="color: white;">Home</a></li>
       
        <li>
            <button id="profileButton" style="color: white; background: none; border: none; cursor: pointer;">
                Profile
            </button>
        </li>
        

        <li><a href="#" style="color: white;">About Us</a></li>
    </ul>
</nav>

      
    </div>
    <main>
         <h2 id="welcomeMessage">Welcome, User</h2><!--  Will be updated dynamically -->

    <!-- Profile Sidebar -->
    <div id="profilePopup">
        <p>User: <span id="profileName"></span></p>
        <button id="logoutButton">Logout</button>
    </div>
            <div class="main">
                <div class="mainmini">
                    <nav id="nav">Seminar Hall Booking System</nav>
                </div>
                <div id=mainbox>
    <h2>Teacher List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Password</th>
                <th>Auditorium ID</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <%
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM teacher");
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
            %>
            <tr>
                <td><%= rs.getInt("t_id") %></td>
                <td><%= rs.getString("user_name") %></td>
                <td><%= rs.getString("password") %></td>
                <td><%= rs.getInt("aid") %></td>
                <td>
                    <a href="Edit_Teacher.jsp?t_id=<%= rs.getInt("t_id") %>">Edit</a>
                </td>
                <td>
                    <a href="javascript:void(0);" onclick="confirmDelete(<%= rs.getInt("t_id") %>)" style="color:red;">Delete</a>
                </td>
            </tr>
            <%
                    }
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
        </tbody>
    </table>
    </div>
    </div>
    </main>
 <footer>
        <div class="info">
            <div class="row">
                <div class="inforow1">
                   
                    <div class="items">
                     <h4>Quick Links</h4>
                        <ul>
                            <li>Home</li>
                            <li>About Us</li>
                            <li>Courses</li>
                            <li>Admission</li>
                            <li>Examination</li>
                            <li>College In News</li>
                            <li>Latest Update</li>
                        </ul>
                    </div>
                    <div class="colomn">
                        <div class="infocolomn">
                        <br>
                        <br>
                            <ul>
                                <li>Faculty</li>
                                <li>College Activities</li>
                                <li>Alumini</li>
                                <li>Photo Gallery</li>
                                <li>Video Galeery</li>
                                <li>Contact Us</li>
                                <li>Upcoming Events</li>
                            </ul>
                        </div>
                    </div>
                    <div class="add">
                        <h3>Address</h3>
                        <p>St. Mira's College for Girls
                            <br>6, Koregaon Road,
                            <br> Pune - 411001
                        </p>
                        <h3>Contact Detials</h3>
                        020 2612 4846
                        <h3>Email Id</h3>
                        <p>mira_college@yahoo.co.in
                            info@stmirascollegepune.edu.in
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <!-- Popup HTML -->
    <div class="popup-overlay">
        <div class="popup-content">
            <button class="close-btn">&times;</button>
            <h2>Login</h2>
            <form class="popup-form" action="LoginServlet" method="post">
                <input type="text" placeholder="Username" name="username" required>
                <br>
                <input type="password" placeholder="Password" name="password" required>
                <br>
                <button class="popup-btn" type="submit" value="login" name="login">Login</button>
                <button class="popup-btn" type="submit" value="sign-up" name="signup">Sign Up</button>
            </form>
        </div>
    </div>
    

    
</body>

</html>
