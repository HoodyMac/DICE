package pl.zed.dice.security.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")}
    )
    private List<Authority> authorities;

    @OneToOne
    private UserProfile profile;

    public UserAccount() {
    }

    public UserAccount(String email, String password, Boolean enabled, Date lastPasswordResetDate) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public void editUserEmail(UserDTO userDTO){
        this.email = userDTO.getEmail();
    }

    public void editUserPassword(UserDTO userDTO){
        this.password = new BCryptPasswordEncoder().encode(userDTO.getPassword());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }
}
