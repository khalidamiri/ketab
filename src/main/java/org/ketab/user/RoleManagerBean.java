package org.ketab.user;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Session Bean implementation class RoleManagerBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RoleManagerBean implements RoleManager {
	
	@PersistenceContext(name="RoleManagerBeanLocal", unitName="ketab", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;

    public RoleManagerBean() {
    }

	@Override
	public void addRole(Role role) {
		em.persist(role);
	}

	@Override
	public void delRole(Long roleId) {
		em.remove(em.find(Role.class, roleId));
	}

	@Override
	public void updateRole(Role role) {
		em.merge(role);
	}

	@Override
	public List listRoles() {
		return null;
	}

	@Override
	public Role getRole(long roleId) {
		return em.find(Role.class, roleId);
	}
    
}
