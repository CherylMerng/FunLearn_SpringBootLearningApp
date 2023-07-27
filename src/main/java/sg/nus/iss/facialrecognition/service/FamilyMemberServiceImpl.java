package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.iss.facialrecognition.model.FamilyMember;
import sg.nus.iss.facialrecognition.model.User;
import sg.nus.iss.facialrecognition.repository.FamilyMemberRepository;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepo;

    @Override
    public FamilyMember saveFamilyMember(FamilyMember member){
        return familyMemberRepo.save(member);
    }

    public void registerFamilyMember(String childName, User parent){
        FamilyMember newMember =  new FamilyMember();
		newMember.setUserName(childName);
		newMember.setUser(parent);
        saveFamilyMember(newMember);
    }
}
