package acct.service;

import acct.domain.Account;
import acct.domain.User;
import acct.dto.CreateAccountDTO;
import acct.dto.CreateUserDTO;
import acct.repository.UserRepository;
import core.dto.acct.dto.UserInfo;
import acct.repository.AccountRepository;
import core.protocol.Protocol;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcctService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenCacheService tokenCacheService;

    public Account findUserByLoginName(String loginName){
        Account oneByName = accountRepository.findOneByLoginName(loginName);
        if(oneByName==null|| Protocol.Status.YES == oneByName.getIsDeleted())
            return null;
        return oneByName;

    }
    @Transactional
    public void createAccount(CreateAccountDTO createAccountDTO) throws Exception{
        Account oneByLoginName = accountRepository.findOneByLoginName(createAccountDTO.getLoginName());
        if(oneByLoginName!=null)
            throw  new Exception("用户名已存在");
        Account account = new Account();
        account.setEmail(createAccountDTO.getEmail());
        account.setPassword(createAccountDTO.getPassword());
        account.setLoginName(createAccountDTO.getLoginName());
        accountRepository.save(account);
    }

    @Transactional
    public Long createUser(CreateUserDTO createUserDTO, Long accountId) throws Exception {
        User oneByNickNameAndArea = userRepository.findOneByNickNameAndArea(createUserDTO.getNickName(), createUserDTO.getArea());
        if(oneByNickNameAndArea!=null)
            throw  new Exception("角色名已存在");
        User usertmp = userRepository.findOneByAccountIdAndArea(accountId, createUserDTO.getArea());
        if(usertmp!=null){
            throw  new Exception("角色已存在");
        }
        //create
        User user = new User();
        user.setAccountId(accountId);
        user.setArea(createUserDTO.getArea());
        user.setNickName(createUserDTO.getNickName());
        userRepository.save(user);
        tokenCacheService.createUser(accountId,createUserDTO.getArea(),user.getId());
        return user.getId();
    }


    //大區判定
    public boolean checkHasArea(Long area){
        if(area==1)
            return true;
        return false;
    }


    public Account findAccountById(Long id){
        Account one = accountRepository.findOne(id);
        if(one==null|| Protocol.Status.YES == one.getIsDeleted()){
            return null;
        }
        return one;
    }


    public UserInfo getUserInfoByUserId(Long userId) {
        User one = userRepository.findOne(userId);
        if(one!=null){
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(one,userId);
            return userInfo;
        }
        return null;
    }
}
