<%@ page import="java.util.*, java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Your Events</title>
<style>
        /* Your existing CSS styles */
        body {
            margin: 0;
            padding: 0;
        }

        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0;
            background-color: #f8f9fa;
        }

        header>div>img {
            width: 55px;
            float: left;
            margin: 2px 15px;
        }

        .box {
            display: flow-root;
            justify-content: space-between;
            align-items: center;
        }

        header>div>h1 {
            margin-top: 7px;
            margin-bottom: 0px;
            color: #00457c;
            font-weight: bold;
            font-size: 35px
        }

        header>div>h3 {
            margin-top: 1px;
            font-weight: bold;
            color: #333;
            font-size: 20px;
            margin-bottom: 10px;
        }

        header>div>ul {
            margin: 0;
            padding: 0;
            display: flex;
            list-style: none;
            margin-top: 0px;
            margin-right: 0;
        }

        .imgSocial {
            display: flex;
            width: 45px;
            height: 45px;
            size: 4px;
            float: right;
            margin: 0;
            padding: 0;
            margin-top: 0;
        }

        .Redbox {
            border: 1px solid rgb(122, 1, 1);
            height: 25px;
            background-color: rgb(122, 1, 1);
            margin-left: 0;
        }

        .Bluebox {
            border: 1px solid #124d83;
            height: 40px;
            background-color: #124d83;
            margin-left: 0;
            position: sticky;
            top: 0;
            z-index: 100;
        }

        .main {
            display: flex;
            justify-self: center;
            margin-top: 40px;
            width: 1215px;
            height: 650px;
            box-shadow: 1px 3px 6px rgb(189, 189, 189);
            margin: 40px auto;
            margin: -750px auto;
            margin-left: 275px;
            margin-top: -1185px;
        }



        .mainmini {
            display: flex;
            background-color: rgb(247, 247, 247);
            height: 40px;
            width: 1215px;
            font-size: larger;
            box-shadow: 1px 1px 1px #e0e0e0;
        }

        #nav {
            padding: 7px;
            margin: 4px;
        }

       footer {
    display: flex;
    width: 1500px;
    justify-self: center;
    background-color: rgb(211, 211, 211);
    margin: 751px auto;
    padding: 10px;
}
        footer>div>div>div>h4>div>ul {
            list-style: none;
            justify-content: space-between;
        }

        .infocolomn {
            list-style: none;
            justify-content: space-between;
        }

        #nav1 {
            display: flex;
            list-style: none;
            color: white;
            gap: 15px;
            text-decoration: none;
            align-items: end;
            align-self: flex-end;
            justify-content: right;
            margin-right: 10px;
            margin-bottom: 2px;
        }

        /* Popup CSS styles */
        .popup-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .popup-content {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            position: relative;
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: transparent;
            border: none;
            font-size: 20px;
            cursor: pointer;
        }

        .popup-btn {
            margin-top: 10px;
        }

        .popup-form input {
            margin: 10px 0;
        }

        .adminsidebar {
            width: 250px;
            background: #2c3e50;
            color: white;
            height: 145vh;
            padding-top: 20px;
            /* position: relative; */
            overflow-y: auto;
            top: 105px;
            padding: 0;
        }

        .adminsidebar a {
            display: block;
            padding: 15px;
            color: white;
            text-decoration: none;
            transition: 0.3s;
        }

        .adminsidebar a:hover {
            background-color: #34495e;
        }

        /* Form Popup Styling */
        .form-container {
            display: none;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }

        .form-container input {
            margin: 10px;
            padding: 10px;
            width: 100%;
            box-sizing: border-box;
        }

        .form-container button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #124d83;
            color: white;
            border: none;
            cursor: pointer;
        }

        .form-container button:hover {
            background-color: #0a3c66;
        }

        .section {
            width: 1057px;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-left: -1215px;
            margin-top: 42px;
            position: relative;
        }
        
          #profilePopup {
            display: none;
            position: absolute;
             bottom: 528px;
             right: 58px;
            background: white;
            padding: 10px;
            border: 1px solid #ccc;
        }
        #logoutButton {
            margin-top: 5px;
            cursor: pointer;
            background: red;
            color: white;
            border: none;
            padding: 5px;
        }
        
        
        
    </style>
<script>
    // Fetch auditorium names from the servlet
    fetch('GetAuditoriumServlet')
        .then(response => response.text())
        .then(data => {
            document.getElementById('auditorium').innerHTML += data;
        })
        .catch(error => console.error('Error loading auditoriums:', error));
    
    document.getElementById("loadEventsBtn").addEventListener("click", function () {
        fetch('ViewEventsServlet') // Calls the servlet
            .then(response => response.text()) // Get the response as text
            .then(data => {
                document.getElementById("eventList").innerHTML = data; // Add table to div
            })
            .catch(error => {
                console.error("Error fetching events:", error);
                document.getElementById("eventList").innerHTML = "<p>Failed to load events.</p>";
            });
    });

    document.getElementById("viewEventBtn").addEventListener("click", function () {
        document.getElementById("viewEvent").style.display = "block"; // Ensure it shows up
    });
    
    
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
    

  
        function confirmDelete(event_id) {
            if (confirm("Do you really want to delete this event?")) {
                window.location.href = "DeleteEventServlet?event_id=" + event_id;
            }
        }
    </script>
</head>
<body>
 <header>
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
                <li><a href="Teacher1.html" style="color: white;">Home</a></li>
                <li><button id="loginButton"
                        style="color: white; background: none; border: none; cursor: pointer;">About Us</button></li>
                <li>  <button id="profileButton" style="color: white; background: none; border: none; cursor: pointer;">
                        Profile
                    </button></li>
            </ul>
        </nav>
    </div>
    <div class="adminsidebar">
        <a href="Teacher1.html" id="addEventBtn" data-target="addEvent">Add Event</a>
        <a href="#" id="viewEventBtn" data-target="viewEvent">View Event</a>
       <!--  <a href="#" id="addAuditoriumBtn" data-target="addAuditorium">Add Auditorium</a>
        <a href="#" id="viewAuditoriumsBtn" data-target="viewAuditoriums">View Auditoriums</a> -->
    </div>
    <main>
        <div class="main">
          <h2 id="welcomeMessage">Welcome, User</h2> <!-- Will be updated dynamically -->

    <!-- Profile Sidebar -->
    <div id="profilePopup">
        <p>User: <span id="profileName"></span></p>
        <button id="logoutButton">Logout</button>
    </div>
            <div class="mainmini">
                <nav id="nav">Home/Notice</nav>
            </div>
            <div class="mainbox" id="mainbox">
            <div class="section">
    <h2>Your Events</h2>
    
    <table border="1">
        <thead>
            <tr>
                <th>Event ID</th>
                <th>Event Name</th>
                <th>Date</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Department</th>
                <th>Hosted By</th>
                <th>Description</th>
                <th>Poster</th>
                <th>Status</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Get teacher ID from session
                Integer loggedInTeacherId = (Integer) session.getAttribute("t_id");
                if (loggedInTeacherId == null) {
                    out.println("<tr><td colspan='12' style='color: red;'>Error: Please log in.</td></tr>");
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

                    // Only fetch events created by the logged-in teacher
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM event WHERE t_id=?");
                    ps.setInt(1, loggedInTeacherId);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        int event_id = rs.getInt("event_id");
                        String event_name = rs.getString("event_name");
                        String event_date = rs.getString("event_date");
                        String start_time = rs.getString("start_time");
                        String end_time = rs.getString("end_time");
                        String department = rs.getString("department");
                        String hosted_by = rs.getString("hosted_by");
                        String description = rs.getString("description");
                        String event_poster = rs.getString("event_poster");
                        String status = rs.getString("status");

                        // Handle missing images
                        String posterTag = (event_poster != null && !event_poster.isEmpty()) ? 
                                           "<img src='" + event_poster + "' width='50' height='50'>" : "No Image";
            %>
            <tr>
                <td><%= event_id %></td>
                <td><%= event_name %></td>
                <td><%= event_date %></td>
                <td><%= start_time %></td>
                <td><%= end_time %></td>
                <td><%= department %></td>
                <td><%= hosted_by %></td>
                <td><%= description %></td>
                <td><%= posterTag %></td>
                <td><%= status %></td>
                <td>
                     <a href="EditEventTeasher.jsp?event_id=<%= event_id %>">Edit</a>
             
                </td>
                <td>
                    <a href="javascript:void(0);" onclick="confirmDelete(<%= event_id %>)" style="color:red;">Delete</a>
                </td>
            </tr>
            <%
                    }
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
            %>
            <tr><td colspan="12" style="color: red;">Error loading events.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>
  </div>
  
  </div>
            </div>
            
       <!--   </div>-->
    </main>
    <footer>
        <div class="info">
            <div class="row">
                <div class="inforow">
                    <h4>Quick Links</h4>
                    <div class="items">
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
                        <p>St. Mira's College for Girls <br> 6, Koregaon Road, <br> Pune - 411001 </p>
                        <h3>Contact Detials</h3>
                        <br> 020 2612 4846 <h3>Email Id</h3>
                        <p>mira_college@yahoo.co.in <br>info@stmirascollegepune.edu.in </p>
                    </div>
                </div>
            </div>
        </div>

        
    </footer>

    <!-- Form Popups -->
    <!-- <div class="form-container" id="addTeacherForm">
    <h2>Add Teacher</h2>
    <form>
      <label for="teacherUsername">Username:</label>
      <input type="text" id="teacherUsername" name="teacherUsername">
      <label for="teacherPassword">Password:</label>
      <input type="password" id="teacherPassword" name="teacherPassword">
      <button type="submit">Add Teacher</button>
    </form>
  </div>

  <div class="form-container" id="addAuditoriumForm">
    <h2>Add Auditorium</h2>
    <form>
      <label for="auditoriumName">Auditorium Name:</label>
      <input type="text" id="auditoriumName" name="auditoriumName">
      <label for="seatCapacity">Seat Capacity:</label>
      <input type="number" id="seatCapacity" name="seatCapacity">
      <label for="auditoriumImage">Auditorium Image:</label>
      <input type="file" id="auditoriumImage" name="auditoriumImage">
      <button type="submit">Add Auditorium</button>
    </form>
  </div>-->
   <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Hide all sections by default
            document.querySelectorAll(".section").forEach(section => {
                section.style.display = "none";
            });

            // Get all sidebar buttons and add event listeners
            document.querySelectorAll(".adminsidebar a").forEach(item => {
                item.addEventListener("click", function (event) {
                    event.preventDefault();

                    // Hide all sections first
                    document.querySelectorAll(".section").forEach(section => {
                        section.style.display = "";
                    });

                    // Get the target section based on clicked button
                    let targetId = this.getAttribute("data-target");
                    let targetSection = document.getElementById(targetId);

                    // Show the clicked section
                    if (targetSection) {
                        targetSection.style.display = "block";
                    }
                });
            });
        });

    </script>

</body>

</html>
