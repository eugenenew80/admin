package kz.ecc.isbp.admin.fnd.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.fnd.dto.ModuleDto;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.service.ModuleService;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;

@RequestScoped
@Path("/modules")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class ModuleResourceImpl {
	
	@GET
	public Response getAll() {		
		List<ModuleDto> moduleListDto = moduleService.findAll().stream()
			.map( toModuleDto )
			.collect(Collectors.toList());
		
		return Response.ok()
				.entity(new GenericEntity<Collection<ModuleDto>>(moduleListDto){})
				.build();
	}
	
	
	@GET
	@Path("{moduleId : \\d+}")
	public Response get(@PathParam("moduleId") Long moduleId) {
		Optional<Module> module = Optional.ofNullable(moduleService.findById(moduleId));
		return Response.ok()
			.entity( module.map(toModuleDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	 
	
	@GET
	@Path("{moduleName}")
	public Response getByName(@PathParam("moduleName") String moduleName) {
		Optional<Module> module = Optional.ofNullable(moduleService.findByName(moduleName));
		return Response.ok()
			.entity( module.map(toModuleDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}

	@Inject private ModuleService moduleService;
}
