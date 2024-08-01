package springbootmicroservices.mediasocial.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import springbootmicroservices.mediasocial.user.UserModel;

@Entity(name = "posts")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Description is required")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private UserModel user;

    public PostModel() {}

    public PostModel(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
