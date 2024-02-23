package org.blaiseSolutions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.blaiseSolutions.model.enums.ETaskStatus;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@NamedQueries({
//        @NamedQuery(name = "Tasks.searchTasks", query = "select t from Tasks t where t.assignedTo like :kw"),
//        @NamedQuery(name = "Person.searchPersonInPage", query = "select p from Person p where lower(p.username) like lower(concat('%',:kw,'%'))")
//})
public class Tasks {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    @Enumerated(EnumType.STRING)
    private ETaskStatus status;
    @ManyToMany(mappedBy = "assignedTasks")
    private List<User> usersList;
    private String assignedTo;
}
