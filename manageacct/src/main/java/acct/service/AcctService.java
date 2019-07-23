package acct.service;

import acct.domain.Account;
import acct.dto.UserInfo;
import acct.repository.AccountRepository;
import core.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AcctService {

    @Autowired
    private AccountRepository accountRepository;


    public Account findUserByLoginName(String loginName){
        Account oneByName = accountRepository.findOneByLoginName(loginName);
        if(oneByName==null|| Protocol.Status.YES == oneByName.getIsDeleted())
            return null;
        return oneByName;

    }

//    public void createUser(CreateUaaUserDTO createUaaUserDTO) {
//        UaaUser uaaUser = new UaaUser();
//        uaaUser.setId(UUIDGenerator.getUUID());
//        uaaUser.setStatus(Constants.USER_STATUS_SAVE);
//        uaaUser.setName(createUaaUserDTO.getLoginName());
//        uaaUser.setNickName(createUaaUserDTO.getNickName());
//        uaaUser.setPassword(createUaaUserDTO.getPassword());
//        uaaUser.setVerifyCode(createUaaUserDTO.getPassword());
//        uaaUser.setProjectType(createUaaUserDTO.getProjectType());
//        uaaUser.setTenantCode(createUaaUserDTO.getTenantCode());
//        uaaUserRepository.save(uaaUser);
//    }

    public Account findAccountById(Long id){
        Account one = accountRepository.findOne(id);
        if(one==null|| Protocol.Status.YES == one.getIsDeleted()){
            return null;
        }
        return one;
    }

}
