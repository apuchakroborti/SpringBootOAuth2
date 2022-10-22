package com.apu.springmvc.springsecuritymvc.specifications;

import com.apu.springmvc.springsecuritymvc.entity.CustomUser;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.data.jpa.domain.Specification;

public class UserSearchSpecifications {
    public static Specification<CustomUser> withId(Long id){
        return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : cb.conjunction();
    }
    public static Specification<CustomUser> withFirstName(String firstName){
        return (root, query, cb) -> !Utils.isNullOrEmpty(firstName) ?
                cb.like(root.get("firstName"), "%" + firstName + "%") :
                cb.conjunction();
    }
    public static Specification<CustomUser> withLastName(String lastName){
        return (root, query, cb) -> !Utils.isNullOrEmpty(lastName) ?
                cb.like(root.get("lastName"), "%" + lastName + "%") :
                cb.conjunction();
    }
    public static Specification<CustomUser> withPhone(String phone){
        return (root, query, cb) -> !Utils.isNullOrEmpty(phone) ?
                cb.equal(root.get("phone"), phone) :
                cb.conjunction();
    }
    public static Specification<CustomUser> withEmail(String email){
        return (root, query, cb) -> !Utils.isNullOrEmpty(email) ?
                cb.equal(root.get("email"), email) :
                cb.conjunction();
    }
    public static Specification<CustomUser> withStatus(Boolean status){
        return (root, query, cb) -> status!=null ?
                cb.equal(root.get("status"), status) :
                cb.conjunction();
    }
}
