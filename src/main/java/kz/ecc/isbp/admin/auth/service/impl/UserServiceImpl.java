package kz.ecc.isbp.admin.auth.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.auth.repository.RoleModuleDictRepository;
import kz.ecc.isbp.admin.auth.repository.RoleModuleRepository;
import kz.ecc.isbp.admin.auth.repository.RoleRepository;
import kz.ecc.isbp.admin.auth.repository.UserRepository;
import kz.ecc.isbp.admin.auth.service.UserService;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.RepositoryNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityService;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.repository.DictRepository;
import kz.ecc.isbp.admin.fnd.repository.ModuleRepository;

@Stateless
public class UserServiceImpl extends AbstractEntityService<User> implements UserService {

	@Inject
	public UserServiceImpl(Repository<User> repository) {
		super(repository);
		this.userRepository = (UserRepository) repository;
	}


	@Override
	public User findByName(String entityName) {
		throw new UnsupportedOperationException();
	}


	public User disable(Long userId) {
		if (userRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null) 
			throw new InvalidArgumentException();		
		
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);

		
		if (!user.getIsDisabled()) {
			user.setIsDisabled(true);
			user = userRepository.update(user);
		}
		
		return user;
	}

	
	public User enable(Long userId) {
		if (userRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null) 
			throw new InvalidArgumentException();		
		
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);
		
		if (user.getIsDisabled()) {
			user.setIsDisabled(false);		
			user = userRepository.update(user);
		}
		
		return user;
	}

	
	public User toArchive(Long userId) {
		if (userRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null) 
			throw new InvalidArgumentException();		
		
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);
		
		if (!user.getIsArchive()) {
			user.setIsArchive(true);
			user = userRepository.update(user);
		}
		
		return user;
	}
	

	public User fromArchive(Long userId) {
		if (userRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null) 
			throw new InvalidArgumentException();		
		
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);
		
		if (user.getIsArchive()) {
			user.setIsArchive(false);
			user = userRepository.update(user);
		}
		
		return user;
	}
	
			
	public User addModules(Long userId, Set<Module> modules) {
		if (userRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null || modules==null) 
			throw new InvalidArgumentException();				
				
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);
		
		Set<Module> removedModules = user.getModules().stream()
				.filter(it -> !modules.contains(it))
				.collect(Collectors.toSet());		
		
		Set<Module> addedModules = modules.stream()
				.filter(it -> !user.getModules().contains(it))
				.collect(Collectors.toSet());		
		
		if (!removedModules.isEmpty() || !addedModules.isEmpty()) {
			removedModules.forEach(user.getModules()::remove);
			addedModules.forEach(user.getModules()::add);
			userRepository.update(user);
		}
		
		return user;
	}
	
	
	public User addRoles(Long userId, Long moduleId, Set<RoleModule> roleModules) {
		if (userRepository==null || moduleRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null || moduleId==null || roleModules==null) 
			throw new InvalidArgumentException();				
				
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);

		Module module = moduleRepository.selectById(moduleId);
		if (module==null) 
			throw new EntityNotFoundException(moduleId);		
		
		
		Set<RoleModule> removedRoleModules = user.getRoleModules().stream()
				.filter(it -> 
					it.getModule().equals(module) && 
					!roleModules.contains(it)
				)
				.collect(Collectors.toSet());		
		
		Set<RoleModule> addedRoleModules = roleModules.stream()
				.filter(it -> 
					it.getModule().equals(module) && 
					!user.getRoleModules().contains(it)
				)
				.collect(Collectors.toSet());		
		
		if (!removedRoleModules.isEmpty() || !addedRoleModules.isEmpty()) {
			removedRoleModules.forEach(user.getRoleModules()::remove);
			
			addedRoleModules.stream()
					.map(this::getOrCreateRoleModule)
					.filter(it -> it!=null && it.getId()!=null)
					.forEach(user.getRoleModules()::add);			
			
			userRepository.update(user);
		}
		
		return user;
	}
	
	private RoleModule getOrCreateRoleModule(RoleModule roleModule) {
		if (!isValidRoleModule(roleModule)) 
			return null;

		RoleModule existingRoleModule = ((RoleModuleRepository)roleModuleRepository).selectByIds(
			roleModule.getModule().getId(), 
			roleModule.getRole().getId(), 
			roleModule.getLevelId()
		);		
		
		return existingRoleModule==null ? roleModuleRepository.insert(roleModule) : existingRoleModule;
	}

	private boolean isValidRoleModule(RoleModule roleModule) {
		return !(
			roleModule.getModule()==null ||
			roleModule.getRole()==null ||
			roleModule.getLevelId()==null ||
			roleModule.getModule().getId()==null ||
			roleModule.getRole().getId()==null				
		);
	}
	
	
	public User addDicts(Long userId, Long moduleId, Long roleId, Long levelId, Set<RoleModuleDict> roleModuleDicts) {
		if (userRepository==null || moduleRepository==null)
			throw new RepositoryNotFoundException();

		if (userId==null || moduleId==null || roleId==null || levelId==null || roleModuleDicts==null) 
			throw new InvalidArgumentException();				
				
		User user = findById(userId);
		if (user==null)
			throw new EntityNotFoundException(userId);

		Module module = moduleRepository.selectById(moduleId);
		if (module==null) 
			throw new EntityNotFoundException(moduleId);		
		
		Role role = roleRepository.selectById(roleId);
		if (role==null) 
			throw new EntityNotFoundException(roleId);	
		
		
		Set<RoleModuleDict> removedRoleModuleDicts = user.getRoleModuleDicts().stream()
				.filter(it -> 
					it.getModule().equals(module) && 
					it.getRole().equals(role) && 
					Objects.equals(it.getLevelId(), levelId) && 
					!roleModuleDicts.contains(it)
				)
				.collect(Collectors.toSet());		
		
		Set<RoleModuleDict> addedRoleModuleDicts = roleModuleDicts.stream()
				.filter(it -> 
					it.getModule().equals(module) && 
					it.getRole().equals(role) && 
					Objects.equals(it.getLevelId(), levelId) && 
					!user.getRoleModuleDicts().contains(it)
				)
				.collect(Collectors.toSet());		
		
		
		if (!removedRoleModuleDicts.isEmpty() || !addedRoleModuleDicts.isEmpty()) {
			removedRoleModuleDicts.forEach(user.getRoleModuleDicts()::remove);
			
			addedRoleModuleDicts.stream()
						.map(this::getOrCreateRoleModuleDict)
						.filter(it -> it!=null && it.getId()!=null)
						.forEach(user.getRoleModuleDicts()::add);			
			
			userRepository.update(user);
		}
		
		return user;
	}
		
	private RoleModuleDict getOrCreateRoleModuleDict(RoleModuleDict roleModuleDict) {						
		if (!isValidRoleModuleDict(roleModuleDict)) 
			return null;
		
		RoleModuleDict existingRoleModuleDict = ((RoleModuleDictRepository)roleModuleDictRepository).selectByIds(
			roleModuleDict.getModule().getId(), 
			roleModuleDict.getRole().getId(), 
			roleModuleDict.getLevelId(), 
			roleModuleDict.getDict().getId(), 
			roleModuleDict.getAccessType()
		);
		
		return existingRoleModuleDict==null ? roleModuleDictRepository.insert(roleModuleDict) : existingRoleModuleDict;
	}
	
	private boolean isValidRoleModuleDict(RoleModuleDict roleModuleDict) {
		return !( 
			roleModuleDict.getModule()==null || 
			roleModuleDict.getRole()==null || 
			roleModuleDict.getLevelId()==null || 
			roleModuleDict.getDict()==null || 
			roleModuleDict.getAccessType()==null ||
			roleModuleDict.getModule().getId()==null ||
			roleModuleDict.getRole().getId()==null ||
			roleModuleDict.getDict().getId()==null
		);
	}
	
	
	public List<Module> getModules(Long userId) {
		if (userId==null) 
			return null;
		
		User user = userRepository.selectById(userId);
		if (user==null) 
			return null;
		
		List<Module> list = new ArrayList<>();
		moduleRepository.selectAll().forEach(module -> {
			module.setIsGranted(user.getModules().stream()
									.filter( it -> it.getId().longValue() == module.getId().longValue() )
									.findFirst().isPresent());	
			list.add(module);
		});
		
		return list;
	}
	
	
	public List<RoleModule> getRoles(Long userId, Long moduleId) {
		if (userId==null || moduleId==null)
			return null;

		User user = userRepository.selectById(userId);
		if (user==null) 
			return null;
		
		Module module = moduleRepository.selectById(moduleId);
		if (module==null) 
			return null;
		
		List<RoleModule> list = new ArrayList<>();			
		roleRepository.selectAll().forEach(role -> {
			Arrays.asList(1L, 2L, 3L).forEach(levelId -> {
				RoleModule roleModule = new RoleModule(module, role, levelId);
				
				roleModule.setIsGranted(user.getRoleModules().stream()
											.filter(it -> 
												it.getRole().getId().longValue() == roleModule.getRole().getId().longValue() && 
												it.getModule().getId().longValue() == roleModule.getModule().getId().longValue() && 
												it.getLevelId().longValue() == roleModule.getLevelId().longValue() 
											)
											.findFirst().isPresent());																		
				list.add(roleModule);
			});			
		});

		return list;
	}	
	
	
	public List<RoleModuleDict> getDicts(Long userId, Long moduleId, Long roleId, Long levelId) {
		if (userId==null || moduleId==null || roleId==null || levelId==null)
			return null;
		
		User user = userRepository.selectById(userId);
		if (user==null) 
			return null;
		
		Module module = moduleRepository.selectById(moduleId);
		if (module==null) 
			return null;		
	
		Role role = roleRepository.selectById(roleId);
		if (role==null) 
			return null;	
		
		List<RoleModuleDict> list = new ArrayList<>();
		dictRepository.selectAll().forEach(dict -> {
			RoleModuleDict roleModuleDict = new RoleModuleDict(module, role, levelId, dict);
			
			roleModuleDict.setIsViewGranted(user.getRoleModuleDicts().stream()
				.filter(it -> 
					it.getRole().getId().longValue() == roleModuleDict.getRole().getId().longValue() && 
					it.getModule().getId().longValue() == roleModuleDict.getModule().getId().longValue() && 
					it.getLevelId().longValue() == roleModuleDict.getLevelId().longValue() && 
					it.getDict().getId().longValue() == roleModuleDict.getDict().getId().longValue() && (
						it.getAccessType().longValue()==1L || 
						it.getAccessType().longValue()==3L
					)
				)
				.findFirst().isPresent());	
			
			roleModuleDict.setIsEditGranted(user.getRoleModuleDicts().stream()
				.filter(it -> 
					it.getRole().getId().longValue() == roleModuleDict.getRole().getId().longValue() && 
					it.getModule().getId().longValue() == roleModuleDict.getModule().getId().longValue() && 
					it.getLevelId().longValue() == roleModuleDict.getLevelId().longValue() && 
					it.getDict().getId().longValue() == roleModuleDict.getDict().getId().longValue() && (
						it.getAccessType().longValue()==2L || 
						it.getAccessType().longValue()==3L
					)
				)
				.findFirst().isPresent());
			
			list.add(roleModuleDict);
		});
		
		return list;
	}


	public void setModuleRepository(ModuleRepository moduleRepository) { this.moduleRepository = moduleRepository; }
	public void setRoleRepository(RoleRepository roleRepository) { this.roleRepository = roleRepository; }
	public void setDictRepository(DictRepository dictRepository) { this.dictRepository = dictRepository; }
	public void setRoleModuleRepository(RoleModuleRepository roleModuleRepository) { this.roleModuleRepository = roleModuleRepository; }
	public void setRoleModuleDictRepository(RoleModuleDictRepository roleModuleDictRepository) { this.roleModuleDictRepository = roleModuleDictRepository; }


	private UserRepository userRepository;

	@Inject private Repository<Module> moduleRepository;
	@Inject private Repository<Role> roleRepository;
	@Inject private Repository<Dict> dictRepository;
	@Inject private Repository<RoleModule> roleModuleRepository;
	@Inject private Repository<RoleModuleDict> roleModuleDictRepository;
}
