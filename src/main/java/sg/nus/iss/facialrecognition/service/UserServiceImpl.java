package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sg.nus.iss.facialrecognition.model.User;
import sg.nus.iss.facialrecognition.model.UserDTO;
import sg.nus.iss.facialrecognition.repository.FamilyMemberRepository;
import sg.nus.iss.facialrecognition.repository.SurveyScoreRepository;
import sg.nus.iss.facialrecognition.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SurveyScoreRepository surveyScoreRepository;
    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUserAccounts() {
        return userRepo.findAll();
    }

    public List<User> getUserAccountsByConditions(String displayType){
        List<User> results;

        switch (displayType) {
            case "all":
                results = userRepo.findAll();
                break;
            case "3years":
                LocalDate threeYearsBefore = LocalDate.now().minusYears(3);
                results = userRepo.findByLastAccountAccessBefore(threeYearsBefore);
                break;
            case "active":
                results = userRepo.findByIsActiveTrue();
                break;
            case "inactive":
                results = userRepo.findByIsActiveFalse();
                break;
            default:
                results = userRepo.findAll();
        }

        return results;
    }


    @Override
    public User getUserByUserName(String userName) {
        return userRepo.findOneByUserName(userName);
    }

    @Override
    public Boolean updateUser(User user) {
        String userName = user.getUserName();
        User oldUser = userRepo.findOneByUserName(userName);
        oldUser.setFullName(user.getFullName());
        oldUser.setPassword(oldUser.getPassword());
        oldUser.setParentName(oldUser.getParentName());
        userRepo.save(oldUser);
        return true;
    }
    @Override
    public Boolean updateUser(User oldUser, User newUser) {
            oldUser.setFullName(newUser.getFullName());
            oldUser.setPassword(oldUser.getPassword());
            oldUser.setParentName(oldUser.getParentName());
            if (!newUser.getEmail().equals(oldUser.getEmail())){
                oldUser.setToken(newUser.getToken());
            }
            oldUser.setEmail(newUser.getEmail());
            userRepo.save(oldUser);
            return true;

    }
    @Override
    public void deleteChild(User child){
        familyMemberRepository.deleteById(child.getUserName());
        userRepo.deleteById(child.getUserName());
    }

    public boolean updateUserStatus(String userName, boolean isActive){
        User foundUser = getUserByUserName(userName);
        if(foundUser == null){
            return false;
        }
        foundUser.setActive(isActive);
        userRepo.save(foundUser);
        return true;
    }
    
    public User register(User user) {
        if (userRepo.findOneByUserName(user.getUserName()) == null) {
            String activation = createActivationToken(user, false);
            user.setToken(activation);
            userRepo.save(user);
            return user;
        }

        return null;
    }

    public String createActivationToken(User user, Boolean save) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String activationToken = passwordEncoder.encode(user.getUserName());
        activationToken = activationToken.replaceAll("\\s*\\p{Punct}+\\s*$", "");
        if (save) {
            user.setToken(activationToken);
            userRepo.save(user);
        }
        return activationToken;
    }

    public User activate(String activation) {
        if (activation.equals("1") || activation.length() < 5) {
            return null;
        }
        User u = userRepo.findOneByToken(activation);
        if (u != null) {
            u.setToken("1");
            u.setActive(true);
            userRepo.save(u);
            return u;
        }
        return null;
    }

    public String createResetPasswordToken(User user, Boolean save) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String resetToken = passwordEncoder.encode(user.getEmail());
        if (save) {
            user.setToken(resetToken);
            userRepo.save(user);
        }
        return resetToken;
    }

    public User resetActivation(String username) {
        User u = userRepo.findOneByUserName(username);
        if (u != null) {
            createActivationToken(u, true);
            return u;
        }
        return null;
    }

    public void resetPassword(User user, UserDTO userDTO) {
        user.setPassword(userDTO.getNewPassword());
        userRepo.save(user);
    }

    @Override
    public List<User>findChildrenByParentName(String parentName){
        return userRepo.findByParentName(parentName);
    }

    @Override
    public boolean updateLastLoginDate(String username){
        User user = userRepo.findOneByUserName(username);
        user.setLastAccountAccess(LocalDate.now());
        userRepo.save(user);
        return true;
    }

}
