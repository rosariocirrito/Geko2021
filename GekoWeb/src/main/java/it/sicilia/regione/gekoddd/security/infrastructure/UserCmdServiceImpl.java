package it.sicilia.regione.gekoddd.security.infrastructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;
import it.sicilia.regione.gekoddd.security.domain.entity.UserSecur;
import it.sicilia.regione.gekoddd.security.domain.repository.UserRepository;
import it.sicilia.regione.gekoddd.security.domain.services.UserCmdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service("userCmdService")
@Repository
@Transactional
public class UserCmdServiceImpl implements UserCmdService {
	
	private Log log = LogFactory.getLog(UserCmdServiceImpl.class);

	

	
	

	
	@Transactional
	public void changePwd(UserGeko user, String password) {
        //System.out.println("UserServiceImpl.changePwd() username= "+userName+" pwd= "+password);  
        String hash = "";
        MessageDigest sha256 = null;
        try {
            //sha256 = MessageDigest.getInstance("SHA-256");
            sha256 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserCmdServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] bytes = sha256.digest(password.getBytes());
        for (byte b:bytes)
        hash += String.format("%02x", b);
        System.out.println(hash);
        
        //user.setPassword(hash); // sha???
        //this.save(user); // usare microservizi
    }

	public void resetPwd (UserGeko user){
		this.changePwd(user, user.getUsername().toLowerCase().trim());
	}
	
	
} // ------------------------------------------------------
