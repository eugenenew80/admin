package kz.ecc.isbp.admin.auth.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.net.URI;
import java.util.*;
import static java.util.stream.Collectors.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.auth.dto.*;
import kz.ecc.isbp.admin.auth.entity.*;
import kz.ecc.isbp.admin.auth.service.UserService;
import kz.ecc.isbp.admin.common.repository.*;
import kz.ecc.isbp.admin.common.repository.QueryBuilder;
import kz.ecc.isbp.admin.fnd.dto.ModuleDto;
import kz.ecc.isbp.admin.fnd.entity.Module;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;

@RequestScoped
@Path("users")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class UserResourceImpl {
	
	@GET
	public Response findAll(@QueryParam("departmentId") Long departmentId, @QueryParam("surname") String surname, @QueryParam("name") String name, @QueryParam("iin") String iin) {
		Query query = queryBuilder
			.setParameter("orgStruct.id", departmentId)
			.setParameter("surname", surname)
			.setParameter("name", name)
			.setParameter("iin", iin)
			.build();
		
		List<UserDto> userListDto = userService.find(query).stream()
			.map(toUserDto)
			.collect(toList());
		
		return Response.ok()
			.entity(new GenericEntity<Collection<UserDto>>(userListDto){})
			.build();
	}
	
	
	@GET
	@Path("{userId : \\d+}")
	public Response findById(@PathParam("userId") Long userId) {
		Optional<User> user = Optional.ofNullable(userService.findById(userId));
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@PUT
	@Path("{userId : \\d+}") 
	public Response update(@PathParam("userId") Long userId, UserDto userDto) {
		if (userDto==null || userDto.getId()==null)
			throw new WebApplicationException(SC_BAD_REQUEST);
		
		User entity = fromUserDto.apply(userDto);
		User currentEntity = userService.findById(entity.getId());
		entity.setModules(currentEntity.getModules());
		entity.setRoleModules(currentEntity.getRoleModules());
		entity.setRoleModuleDicts(currentEntity.getRoleModuleDicts());
		
		Optional<User> updatedUser = Optional.ofNullable(userService.update(entity)); 
		return Response.ok()
			.entity( updatedUser.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@POST
	public Response create(UserDto userDto, @Context UriInfo uriInfo) {
		if (userDto==null || userDto.getId()!=null)
			throw new WebApplicationException(SC_BAD_REQUEST);
		
		Optional<User> newUser = Optional.ofNullable( userService.create(fromUserDto.apply(userDto)) );	
		URI uri = uriInfo.getAbsolutePathBuilder()
			.path( newUser.map(User::getId).map(id -> id.toString()).orElse("") )
			.build();
		
		return Response.created(uri)
			.entity( newUser.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@DELETE 
	@Path("{userId : \\d+}") 
	public Response delete(@PathParam("userId") Long userId) {
		if (!userService.delete(userId))
			throw new WebApplicationException(SC_NOT_FOUND);

		return Response.noContent().build();
	}	
	

	@PUT 
	@Path("{userId : \\d+}/enable") 
	public Response enable(@PathParam("userId") Long userId) {
		Optional<User> user = Optional.ofNullable(userService.enable(userId));		
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@PUT 
	@Path("{userId : \\d+}/disable") 
	public Response disable(@PathParam("userId") Long userId) {
		Optional<User> user = Optional.ofNullable(userService.disable(userId));		
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}

	
	@PUT 
	@Path("{userId : \\d+}/toArchive") 
	public Response toArchive(@PathParam("userId") Long userId) {
		Optional<User> user = Optional.ofNullable(userService.toArchive(userId));		
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}	
	
	
	@PUT 
	@Path("{userId : \\d+}/fromArchive") 
	public Response fromArchive(@PathParam("userId") Long userId) {
		Optional<User> user = Optional.ofNullable(userService.fromArchive(userId));
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	

	@PUT
	@Path("{userId : \\d+}/modules")
	public Response addModules(@PathParam("userId") Long userId, ListDataDto moduleIds) {
		if (userId==null || moduleIds==null || moduleIds.modules==null)
			throw new WebApplicationException(SC_BAD_REQUEST);
		
		Set<Module> modules = moduleIds.modules.stream()
			.map(fromModuleDto)
			.collect(toSet());		
		
		Optional<User> user = Optional.ofNullable(userService.addModules(userId, modules));
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@PUT
	@Path("{userId : \\d+}/modules/{moduleId : \\d+}/roles")
	public Response addRoles(@PathParam("userId") Long userId, @PathParam("moduleId") Long moduleId, ListDataDto roleIds) {		
		if (userId==null || moduleId==null || roleIds==null || roleIds.roles==null)
			throw new WebApplicationException(SC_BAD_REQUEST);
		
		Set<RoleModule> roleModules = roleIds.roles.stream()
			.map(fromRoleModuleDto)
			.collect(toSet());
		
		Optional<User> user = Optional.ofNullable(userService.addRoles(userId, moduleId, roleModules));
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}


	@PUT
	@Path("{userId : \\d+}/modules/{moduleId : \\d+}/roles/{roleId : \\d+}/levels/{levelId : \\d+}/dicts")
	public Response addDicts(@PathParam("userId") Long userId, @PathParam("moduleId") Long moduleId, @PathParam("roleId") Long roleId, @PathParam("levelId") Long levelId, ListDataDto dictIds) {		
		if (userId==null || moduleId==null || roleId==null || levelId==null || dictIds==null || dictIds.dicts==null)
			throw new WebApplicationException(SC_BAD_REQUEST);		
		
		Set<RoleModuleDict> roleModuleDicts = dictIds.dicts.stream()
			.map(fromRoleModuleDictDto)
			.collect(toSet());
		
		Optional<User> user = Optional.ofNullable(userService.addDicts(userId, moduleId, roleId, levelId, roleModuleDicts));
		return Response.ok()
			.entity( user.map(toUserDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@GET
	@Path("{userId : \\d+}/modules")
	public Response getModules(@PathParam("userId") Long userId) {
		if (userId==null)
			throw new WebApplicationException(SC_BAD_REQUEST);
		
		List<Module> modules = userService.getModules(userId);
		if (modules==null)
			throw new WebApplicationException(SC_NOT_FOUND);
		
		List<ModuleDto> listDto = modules.stream()
			.map(toModuleDto)
			.collect(toList());
		
		return Response.ok()
			.entity(new GenericEntity<Collection<ModuleDto>>(listDto){})
			.build();
	}
	
	
	@GET
	@Path("{userId : \\d+}/modules/{moduleId : \\d+}/roles")
	public Response getRoles(@PathParam("userId") Long userId, @PathParam("moduleId") Long moduleId) {		
		if (userId==null || moduleId==null)
			throw new WebApplicationException(SC_BAD_REQUEST);

		List<RoleModule> roles = userService.getRoles(userId, moduleId);
		if (roles==null)
			throw new WebApplicationException(SC_NOT_FOUND);
		
		List<RoleModuleDto> listDto = roles.stream()
			.map(toRoleModuleDto)
			.collect(toList());
		
		return Response.ok()
			.entity(new GenericEntity<Collection<RoleModuleDto>>(listDto){})
			.build();		
	}
	
	
	@GET
	@Path("{userId : \\d+}/modules/{moduleId : \\d+}/roles/{roleId : \\d+}/levels/{levelId : \\d+}/dicts")
	public Response getDicts(@PathParam("userId") Long userId, @PathParam("moduleId") Long moduleId, @PathParam("roleId") Long roleId, @PathParam("levelId") Long levelId) {
		if (userId==null || moduleId==null || roleId==null || levelId==null)
			throw new WebApplicationException(SC_BAD_REQUEST);

		List<RoleModuleDict> dicts = userService.getDicts(userId, moduleId, roleId, levelId);
		if (dicts==null)
			throw new WebApplicationException(SC_NOT_FOUND);

		List<RoleModuleDictDto> listDto = dicts.stream()
			.map(toRoleModuleDictDto)
			.collect(toList());		
		
		return Response.ok()
			.entity(new GenericEntity<Collection<RoleModuleDictDto>>(listDto){})
			.build();
	}
	
		
	@GET
	@Path("{userId : \\d+}/permissions")
	public Response getPermissions(@PathParam("userId") Long userId) {
		User user = userService.findById(userId);
		if (user==null) 
			throw new WebApplicationException(SC_NOT_FOUND);		
		
		List<UserPermissionDto> permissionListDto = new ArrayList<>();		
		user.getModules().forEach(module -> {
			user.getRoleModules().stream()
				.filter(it-> it.getModule().equals(module))
				.forEach(roleModule -> {
					roleModule.getRole().getPermissions()
						.forEach(permission -> permissionListDto.add(new UserPermissionDto(user, module, roleModule, permission)) );
					
					if (module.getNameRu().equals("Нормативно-справочная информация")) 
						user.getRoleModuleDicts().stream()
							.filter(it-> 
								it.getModule().equals(roleModule.getModule()) &&
								it.getRole().equals(roleModule.getRole()) &&
								it.getLevelId().equals(roleModule.getLevelId())
							)							
							.forEach(roleModuleDict -> permissionListDto.add(new UserPermissionDto(user, module, roleModule, roleModuleDict)) );
				});;
		});
		
		return Response.ok()
			.entity(new GenericEntity<Collection<UserPermissionDto>>(permissionListDto){})
			.build();
	}
	
	
	@Inject private UserService userService;
	@Inject private QueryBuilder queryBuilder;
}
