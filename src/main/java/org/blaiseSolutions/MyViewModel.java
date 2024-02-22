package org.blaiseSolutions;

import lombok.Getter;
import lombok.Setter;
import org.blaiseSolutions.dao.TasksDao;
import org.blaiseSolutions.dao.UsersDao;
import org.blaiseSolutions.model.Tasks;
import org.blaiseSolutions.model.User;
import org.blaiseSolutions.model.enums.ETaskStatus;
import org.blaiseSolutions.model.enums.EUserRole;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Component
public class MyViewModel {
    private String description;
    private String username;
    private User selectedUser;
    private Tasks selectedTask;
    private List<Tasks> tasksList = new ArrayList<>();
    private List<String> filteredTasks = new ArrayList<>();
    private List<User> usersList = new ArrayList<>();
    
    @Init
    public void init() {
        listTasks();
        listUsers();
        setFilteredTasks();
    }
    @Command
    @NotifyChange({"tasksList"})
    public void saveSelectedTask() {
        Session session = Sessions.getCurrent();
        User userInSession = (User) session.getAttribute("selectedUser");

        if (selectedTask != null && userInSession != null) {
            selectedTask.setAssignedTo(String.valueOf(userInSession));
            selectedTask.setStatus(ETaskStatus.PENDING);
            TasksDao dao = new TasksDao();
            dao.updateTask(selectedTask);

            // Refresh the task list after the update
            setFilteredTasks();
        }
    }
    @Command
    @NotifyChange({"tasksList"})
    public void deleteForm() {
        if (selectedTask != null) {
            TasksDao dao = new TasksDao();
            dao.deleteTask(selectedTask);
            listTasks();
        }
    }
    @Command
    @NotifyChange({"description", "tasksList"})
    public void addTask() {
        TasksDao tasksDao = new TasksDao();
        Tasks newTask = new Tasks();
        newTask.setDescription(description);
        newTask.setStatus(ETaskStatus.PENDING);
        tasksDao.saveTask(newTask);
        description = "";
        listTasks();
    }

    @Command("addUser")
    @NotifyChange({"username", "usersList"})
    public void addUser() {
        UsersDao usersDao = new UsersDao();
        User users = new User();
        users.setUsername(username);
        users.setUserRole(EUserRole.ADMIN);
        users.setPassword("ope123");
        usersDao.saveUser(users);
        username = "";
        listUsers();
    }

    @Command
    public void navigateToUserDetails(@BindingParam("selectedUser") User selectedUser) {
        if (selectedUser != null) {
            // Set selectedUser in the ViewModel
            Session session = Sessions.getCurrent();
            session.setAttribute("selectedUser", selectedUser);
            setSelectedUser(selectedUser);
            // Redirect to UserDetails page
            Executions.sendRedirect("userDetails.zul");
        }
    }

    private void listTasks() {
        TasksDao tasksDao = new TasksDao();
        tasksList = tasksDao.findAllTask();
    }

    public void listUsers() {
        UsersDao usersDao = new UsersDao();
        usersList = usersDao.findAllUsers();
    }

    @NotifyChange({"filteredTasks"})
    @Command
    public void setFilteredTasks() {

        Session session = Sessions.getCurrent();
        User userInSession = (User) session.getAttribute("selectedUser");
            this.selectedUser=userInSession;
        if (userInSession != null ) {
            System.out.println("user founddddd");
            UsersDao usersDao = new UsersDao();
            filteredTasks = usersDao.findTaskDescriptionByUser(userInSession.getUsername());
        } else {
            System.out.println("====================");
            System.out.println("Select User under Filter List Task is Null !!!");
            System.out.println("======================");
        }
    }
}
