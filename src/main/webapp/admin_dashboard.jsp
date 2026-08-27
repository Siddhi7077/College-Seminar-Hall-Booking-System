<%@ page import="java.util.*, java.sql.*,com.project.Teacher" %>
<%
//    ArrayList<Teacher> teacherList = (ArrayList<Teacher>) request.getAttribute("teacherList");
   ArrayList<Teacher> teacherList = (ArrayList<Teacher>) request.getAttribute("teacherList");
%>

<h2>Teacher List</h2>
<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Password</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
    <%try {           if(teacherList!=null) {%>
    <%System.out.println("Teacher list size "+teacherList.size()); %>
        <% for (Teacher teacher : teacherList) { %>
        <tr>
            <td><%= teacher.getId() %></td>
            <td><%= teacher.getUsername() %></td>
            <td><%= teacher.getPassword() %></td>
            <td>
                <a href="EditTeacherServelet?id=<%= teacher.getId() %>">Edit</a></td>
               <td> <a href="javascript:void(0);" onclick="confirmDelete(<%= teacher.getId() %>)" style="color:red;">Delete</a>
            </td>
        </tr>
        <% } }
    else{
    	System.out.println("No data fetched");
    }
    } catch(Exception e){System.out.println(e);} %>
    </tbody>
</table>

<script>
    function confirmDelete(id) {
        if (confirm("Do you really want to delete this record?")) {
            window.location.href = "DeleteTeacherServlet?id=" + id;
        }
    }
</script>
