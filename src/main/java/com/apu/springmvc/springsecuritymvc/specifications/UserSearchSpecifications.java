package com.apu.springmvc.springsecuritymvc.specifications;

import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.data.jpa.domain.Specification;

public class UserSearchSpecifications {
    public static Specification<User> withId(Long id){
        return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : cb.conjunction();
    }
    public static Specification<User> withFirstName(String firstName){
        return (root, query, cb) -> !Utils.isNullOrEmpty(firstName) ?
                cb.like(root.get("firstName"), "%" + firstName + "%") :
                cb.conjunction();
    }
    public static Specification<User> withLastName(String lastName){
        return (root, query, cb) -> !Utils.isNullOrEmpty(lastName) ?
                cb.like(root.get("lastName"), "%" + lastName + "%") :
                cb.conjunction();
    }
    public static Specification<User> withUsername(String username){
        return (root, query, cb) -> !Utils.isNullOrEmpty(username) ?
                cb.equal(root.get("username"), username) :
                cb.conjunction();
    }
    public static Specification<User> withPhone(String phone){
        return (root, query, cb) -> !Utils.isNullOrEmpty(phone) ?
                cb.equal(root.get("phone"), phone) :
                cb.conjunction();
    }
    public static Specification<User> withEmail(String email){
        return (root, query, cb) -> !Utils.isNullOrEmpty(email) ?
                cb.equal(root.get("email"), email) :
                cb.conjunction();
    }
}
