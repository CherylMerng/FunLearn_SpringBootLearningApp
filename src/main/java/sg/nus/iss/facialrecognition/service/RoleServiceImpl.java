package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.iss.facialrecognition.model.Role;
import sg.nus.iss.facialrecognition.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepo;

    @Override
    public Role saveRole(Role role){
        return roleRepo.save(role);
    }
    
    public Role getRole(String roleName){
        return roleRepo.findByRoleName(roleName);
    }
}
