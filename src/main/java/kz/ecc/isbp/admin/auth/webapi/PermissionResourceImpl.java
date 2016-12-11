package kz.ecc.isbp.admin.auth.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.auth.dto.PermissionDto;
import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.auth.service.PermissionService;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;


@RequestScoped
@Path("/permissions")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class PermissionResourceImpl {
	
	@GET
	public Response getAll() {		
		List<PermissionDto> permissionListDto = permissionService.findAll().stream()
			.map(toPermissionDto)
			.collect(Collectors.toList());
		
		return Response.ok()
			.entity(new GenericEntity<Collection<PermissionDto>>(permissionListDto){})
			.build();
	}
	
	
	@GET
	@Path("{permissionId : \\d+}")
	public Response get(@PathParam("permissionId") Long permissionId) {
		Optional<Permission> permission = Optional.ofNullable(permissionService.findById(permissionId));
		return Response.ok()
			.entity( permission.map(toPermissionDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}


	@GET
	@Path("{permissionName}")
	public Response getByName(@PathParam("permissionName") String permissionName) {
		Optional<Permission> permission = Optional.ofNullable(permissionService.findByName(permissionName));
		return Response.ok()
			.entity( permission.map(toPermissionDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@Inject private PermissionService permissionService; 
}
