package com.stayeasy.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Service
public class EmailValidationService {

    public boolean isEmailValid(String email) {
        return isEmailFormatValid(email) && hasMXRecord(getDomain(email));
    }

    private boolean isEmailFormatValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private String getDomain(String email) {
        int atIndex = email.lastIndexOf("@");
        return (atIndex > 0) ? email.substring(atIndex + 1) : "";
    }

    private boolean hasMXRecord(String domain) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[] { "MX" });
            return attrs.get("MX") != null;
        } catch (Exception e) {
            return false;
        }
    }
}
