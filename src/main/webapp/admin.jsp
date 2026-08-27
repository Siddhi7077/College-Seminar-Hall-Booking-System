<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.project.DBConnection" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="dashboard-container">
        <header class="dashboard-header">
            <h1>Admin Dashboard</h1>
        </header>

        <div class="dashboard-content">
            <!-- Add Auditorium Form -->
            <div class="form-section">
                <h2>Add New Auditorium</h2>
                <form action="AddAuditoriumServlet" method="post" enctype="multipart/form-data">
                    <label for="auditorium-name">Auditorium Name:</label>
                    <input type="text" name="auditoriumName" required>
                    
                    <label for="seat-capacity">Seat Capacity:</label>
                    <input type="number" name="seatCapacity" required>
                    
                    <label for="auditorium-image">Upload Image:</label>
                    <input type="file" name="auditoriumImage" accept="image/*">
                    
                    <button type="submit">Add Auditorium</button>
                </form>
            </div>

            <!-- Add Teacher Form -->
            <div class="form-section">
                <h2>Add Teacher</h2>
                <form action="AddTeacherServlet" method="post">
                    <label for="teacher-username">Teacher Username:</label>
                    <input type="text" name="teacherUsername" required>

                    <label for="teacher-password">Teacher Password:</label>
                    <input type="password" name="teacherPassword" required>
                    
                    <button type="submit">Add Teacher</button>
                </form>
            </div>

            <!-- Display Auditoriums -->
            <div class="auditorium-list">
                <h2>Manage Auditoriums</h2>
                <ul>
                    <%
                     Connection conn = null;
                       // Statement stmt = null;
                       ResultSet rs = null;
                        try {
                        	conn = DBConnection.getConnection();
                          //  if (conn == null) {
                              //  out.println("<li>Error: Database Connection Failed.</li>");
                           // } else {
                             //   stmt = conn.createStatement();
                               // rs = stmt.executeQuery("SELECT * FROM auditorium");
                                while(rs.next()) {
                    %>
                                    <li><%= rs.getString("name") %> - Seats: <%= rs.getInt("capacity") %></li>
                    <%
                                }
                       // }
                        } catch (Exception e) {
                          //  out.println("<li>Error fetching auditorium list.</li>");
                            e.printStackTrace();
                        } //finally {
                            //try {
                              //  if (rs != null) rs.close();
                                //if (stmt != null) stmt.close();
                                //if (conn != null) conn.close();
                           // } catch (SQLException ex) {
                             //   ex.printStackTrace();
                            //}
                        //}
                    %>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
