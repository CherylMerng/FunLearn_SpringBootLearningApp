package sg.nus.iss.facialrecognition.service;

import sg.nus.iss.facialrecognition.model.User;
import sg.nus.iss.facialrecognition.model.UserDTO;

import java.util.List;

public interface UserService {
    public User saveUser(User user);

    public User getUserByUserName(String userName);

    Boolean updateUser(User user);

    Boolean updateUser(User oldUser, User newUser);

    void deleteChild(User child);

    public User register(User user) ;
    public String createActivationToken(User user, Boolean save);
    public User activate(String activation);
   // public String createResetPasswordToken(User user, Boolean save);
    public User resetActivation(String email);
    public void resetPassword(User user, UserDTO userDTO);
   // List<User>findChildrenByName(List<FamilyMember> childName);
   List<User> findChildrenByParentName(String parentName);
   public boolean updateLastLoginDate(String username);

}
