package kz.ecc.isbp.admin.auth.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.auth.dto.RoleDto;
import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.auth.service.RoleService;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;

@RequestScoped
@Path("/roles")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class RoleResourceImpl {
	
	@GET
	public Response getAll() {		
		List<RoleDto> listDto = roleService.findAll().stream()
            .map(toRoleDto)
            .collect(Collectors.toList());
		
		return Response.ok()
            .entity(new GenericEntity<Collection<RoleDto>>(listDto){})
            .build();
	}
	
	
	@GET
	@Path("{roleId : \\d+}")
	public Response get(@PathParam("roleId") Long roleId) {
        Optional<Role> role = Optional.ofNullable(roleService.findById(roleId));
        return Response.ok()
            .entity( role.map(toRoleDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
            .build();
	}
	
	
	@GET
	@Path("{roleName}")
	public Response getByName(@PathParam("roleName") String roleName) {
        Optional<Role> role = Optional.ofNullable(roleService.findByName(roleName));
        return Response.ok()
            .entity( role.map(toRoleDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
            .build();
	}

	@Inject private RoleService roleService; 
}
