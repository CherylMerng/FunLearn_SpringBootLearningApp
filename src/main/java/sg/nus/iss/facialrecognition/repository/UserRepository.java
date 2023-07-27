package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.iss.facialrecognition.model.User;

import java.util.List;
import java.time.LocalDate;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findOneByUserName(String userName);
    User findOneByEmail(String email);
    User findOneByUserNameOrEmail(String username, String email);
    @Modifying
    @Transactional
    @Query("update User u set u.email = :email, u.fullName = :fullName "
            + "where u.userName = :userName")
    int updateUser(
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("fullName") String fullName);
    User findOneByToken(String token);
//    List<FamilyMember> findChildNameByUserName(String userName);

   // User findChildByChildName(List<FamilyMember> childName);
   List<User> findByParentName(String parentName);
   List<User> findByIsActiveTrue();
    List<User> findByIsActiveFalse();
    List<User> findByLastAccountAccessBefore(LocalDate date);
}
