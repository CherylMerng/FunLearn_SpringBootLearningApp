package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.facialrecognition.model.FamilyMember;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, String> {

}
