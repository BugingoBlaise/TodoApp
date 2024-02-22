package org.blaiseSolutions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.blaiseSolutions.model.enums.EUserRole;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue
    private Integer userId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EUserRole userRole;
    @ManyToMany
    @JoinTable(
            name = "users_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Tasks>assignedTasks;

}
