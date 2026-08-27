<%@ page import="java.util.*, java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
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
            margin-top: -1268px;
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
    right: 161px;
    background: white;
    padding: 10px;
    border: 1px solid #ccc;
    z-index: 10;
        }
        
        #logoutButton {
            margin-top: 5px;
            cursor: pointer;
            background: red;
            color: white;
            border: none;
            padding: 5px;
        }
         .add{
   align-items:center;
   margin-left: 11px;
   margin-right:-42;
   }
        
        
    </style>
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

document.getElementById("viewAuditoriumsBtn").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default action
    console.log("Fetching auditorium data...");

    //fetch("View_Auditorium.jsp") // Ensure this JSP file generates the required HTML output
       fetch("ViewAuditoriumServlet1")
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP Error: ${response.status}`);
        }
        return response.text(); // Convert response to text
    })
    .then(data => {
        console.log("Data received:", data);
        
        // Ensure section is visible
        document.querySelectorAll(".section").forEach(section => section.style.display = "none");
        document.getElementById("viewAuditoriums").style.display = "block";

        // Insert fetched content into the div
        document.getElementById("auditoriumList").innerHTML = data; 
    })
    .catch(error => {
        console.error("Error fetching auditorium data:", error);
        document.getElementById("auditoriumList").innerHTML = `<p style='color:red;'>Failed to load auditorium list: ${error.message}</p>`;
    });
});
</script>

<!--  <script>
    document.getElementById("loadTeachersBtn").addEventListener("click", function () {
        fetch("ViewTeacherServlet1") // Call your servlet
            .then(response => response.text()) // Assuming response is plain HTML or JSON
            .then(data => {
                document.getElementById("teacherList").innerHTML = data; // Insert data
            })
            .catch(error => console.error("Error fetching teachers:", error));
    });

    </script> -->
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
    <div class="adminsidebar">
        <a href="AddTeacherServlet" id="addTeacherBtn" data-target="addTeacher">Add Teacher</a>
        <a href="View_Teacher.jsp" id="viewTeachersBtn" data-target="viewTeachers">View Teachers</a>
        <a href="#" id="addAuditoriumBtn" data-target="addAuditorium">Add Auditorium</a>
        <a href="#" id="viewAuditoriumsBtn" data-target="viewAuditoriums">View Auditoriums</a>
      
        <a href="#" id="viewEventBtn" data-target="viewEvent">View Event</a>
    </div>
    <main>
     <h2 class="welcome">Welcome, User</h2><!--  Will be updated dynamically -->

    <!-- Profile Sidebar -->
    <div id="profilePopup">
        <p>User: <span id="profileName"></span></p>
        <button id="logoutButton">Logout</button>
    </div>
        <div class="main">
            <div class="mainmini">
                <nav id="nav">Home/Notice</nav>
            </div>
            <div class="mainbox" id="mainbox">
            <div class="section">

<%
    int event_id = Integer.parseInt(request.getParameter("event_id"));
    String event_name = "", event_date = "", start_time = "", end_time = "", department = "", hosted_by = "", description = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
        PreparedStatement ps = con.prepareStatement("SELECT * FROM event WHERE event_id=?");
        ps.setInt(1, event_id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            event_name = rs.getString("event_name");
            event_date = rs.getString("event_date");
            start_time = rs.getString("start_time");
            end_time = rs.getString("end_time");
            department = rs.getString("department");
            hosted_by = rs.getString("hosted_by");
            description = rs.getString("description");
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<h2>Edit Event</h2>
<form action="EditEventServlet" method="post">
    <input type="hidden" name="event_id" value="<%= event_id %>">
    <label>Event Name: <input type="text" name="event_name" value="<%= event_name %>"></label><br>
    <label>Date: <input type="date" name="event_date" value="<%= event_date %>"></label><br>
    <label>Start Time: <input type="time" name="start_time" value="<%= start_time %>"></label><br>
    <label>End Time: <input type="time" name="end_time" value="<%= end_time %>"></label><br>
    <label>Department: <input type="text" name="department" value="<%= department %>"></label><br>
    <label>Hosted By: <input type="text" name="hosted_by" value="<%= hosted_by %>"></label><br>
    <label>Description: <textarea name="description"><%= description %></textarea></label><br>
    <button type="submit">Save Changes</button>
</form>

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

        </div>
    </footer>

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
  
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Hide all sections by default
            document.querySelectorAll(".section").forEach(section => {
                section.style.display = "";
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
